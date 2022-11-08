package CMEGroupAssessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;

import java.io.*;

public class AnagramChecker {
    public static boolean areStringsAnagram(String firstString, String secondString) {
        // If the strings do not match in length they cannot be anagrams
        // Therefore return false
        if (firstString.length() != secondString.length())
            return false;
        else {
            // Create a hashmap to store the occurances of each character in the strings
            HashMap<Character, Integer> countCharacterOccurances = new HashMap<Character, Integer>();
            char firstStringCurrentChar = ' ';
            char secondStringCurrentChar = ' ';

            for (int i = 0; i < firstString.length(); i++) {

                if (!Character.isAlphabetic(firstString.charAt(i)) || !Character.isAlphabetic(secondString.charAt(i))) {
                    return false;
                }
                firstStringCurrentChar = Character.toLowerCase(firstString.charAt(i));
                secondStringCurrentChar = Character.toLowerCase(secondString.charAt(i));

                // For each instance of a char in 'firstString' add one to this chars value in
                // the hashmap 'countCharacterOccurances'.
                // For each instance of a char in 'secondString' remove one from this chars
                // value in the hashmap 'countCharacterOccurances'.
                countCharacterOccurances.put(firstStringCurrentChar,
                        countCharacterOccurances.getOrDefault(firstStringCurrentChar, 0) + 1);
                countCharacterOccurances.put(secondStringCurrentChar,
                        countCharacterOccurances.getOrDefault(secondStringCurrentChar, 0) - 1);
            }

            // If the strings are anagrams then the occurances of each char should have
            // canceled out, i.e. each char will have a value of 0.
            for (char key : countCharacterOccurances.keySet())
                if (countCharacterOccurances.get(key) == 0)
                    continue;
                else {
                    return false;
                }
            return true;
        }
    }

    public static ArrayList<ArrayList<String>> readInResults(String filePath) {
        ArrayList<ArrayList<String>> pastResultsList = new ArrayList<ArrayList<String>>();
        try {
            Scanner resultsFileReader = new Scanner(new File(filePath));
            resultsFileReader.useDelimiter(";");
            while (resultsFileReader.hasNext()) {
                ArrayList<String> thisResult = new ArrayList<String>(
                        Arrays.asList(resultsFileReader.nextLine().split(",")));
                pastResultsList.add(thisResult);
            }
            resultsFileReader.close();
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("Results file not found");
        }
        return pastResultsList;
    }

    public static void writeToResults(String filePath, UserSearch thisUserSearch) {
        try {
            BufferedWriter resultsFileWrite = new BufferedWriter(
                    new FileWriter(filePath, true));
            resultsFileWrite
                    .write("\n" + thisUserSearch.getUsername() + "," + thisUserSearch.getFirstWord() + ","
                            + thisUserSearch.getSecondWord() + "," + thisUserSearch.getIsAnagram());
            resultsFileWrite.close();
        } catch (IOException fileNotFound) {
            System.out.println("Results file not found");
        }
    }

    public static boolean isCurrentTestInTestHistory(ArrayList<ArrayList<String>> pastResultsList,
            UserSearch thisUserSearch) {
        for (ArrayList<String> result : pastResultsList) {
            String thisTest = thisUserSearch.getFirstWord() + thisUserSearch.getSecondWord();
            if (thisTest.equals(result.get(1) + result.get(2)) || thisTest.equals(result.get(2) + result.get(1))) {
                return true;
            } else
                continue;
        }
        return false;
    }

    public static void main(String[] args) {
        UserSearch thisUserSearch = new UserSearch();

        Scanner userInputScanner = new Scanner(System.in);
        System.out.print("Username: ");
        thisUserSearch.setUsername(userInputScanner.nextLine());
        System.out.print("First Word: ");
        thisUserSearch.setFirstWord(userInputScanner.nextLine());
        System.out.print("Second Word: ");
        thisUserSearch.setSecondWord(userInputScanner.nextLine());
        userInputScanner.close();

        ArrayList<ArrayList<String>> pastResultsList = readInResults(
                System.getProperty("user.dir") + "\\anagramResults.txt");

        System.out.println("\nOld Results: \n" + pastResultsList);

        if (isCurrentTestInTestHistory(pastResultsList, thisUserSearch))
            System.out.println("Duplicate Values");

        thisUserSearch.setIsAnagram(areStringsAnagram(thisUserSearch.getFirstWord(), thisUserSearch.getSecondWord()));

        writeToResults(System.getProperty("user.dir") + "\\anagramResults.txt", thisUserSearch);

        System.out.println("\nAre words '" + thisUserSearch.getFirstWord() + "' and '" + thisUserSearch.getSecondWord()
                + " anagrams: " + thisUserSearch.getIsAnagram());
    }
}