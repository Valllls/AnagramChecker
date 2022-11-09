package anagramchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import java.io.*;

public class AnagramChecker {
    /**
     * Given two strings this method will return true if there are anagrams of one
     * another and false if not.
     * e.g. 'testing' and 'setting' are anagrams as they contain the same number of
     * each letter.
     * 
     * @param firstString  one of the two strings compared against one another to
     *                     determine if they are indeed anagrams.
     * @param secondString one of the two strings compared against one another to
     *                     determine if they are indeed anagrams.
     * @return a boolean value indicating if the strings are anagrams.
     */
    public boolean areStringsAnagrams(String firstString, String secondString) {
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

    /**
     * Reads in the results stored in the results file
     * Each query from this results file is stored as a UserQuery within an
     * ArrayList
     * 
     * @param filePath The filepath to the anagramResults.txt file.
     * @return A list of all of the UserQueries within the results file.
     */
    public ArrayList<UserQuery> readInResults(String filePath) {
        ArrayList<UserQuery> pastResultsList = new ArrayList<UserQuery>();
        String[] temporaryQueryStorage;
        try {
            Scanner resultsFileReader = new Scanner(new File(filePath));
            while (resultsFileReader.hasNext()) {
                temporaryQueryStorage = resultsFileReader.nextLine().split(",");
                if (temporaryQueryStorage.length == 4)
                    pastResultsList.add(new UserQuery(temporaryQueryStorage[0], temporaryQueryStorage[1],
                            temporaryQueryStorage[2], Boolean.parseBoolean(temporaryQueryStorage[3])));
                else {
                    System.out.println(
                            "Error with reading in the query results, some queries did not have to required four values (username, firstWord, secondWord, isQueryAnAnagram)");
                }
            }
            resultsFileReader.close();
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("Results file not found in result reading");
        }
        return pastResultsList;
    }

    /**
     * Given a UserQuery this method will write to the results file the information
     * within it. In the format:
     * username, firstWord, secondWord, isQueryAnAnagram;
     * 
     * @param filePath      The filepath to the anagramResults.txt file.
     * @param thisUserQuery The UserQuery to be written to the results file
     */
    public void writeToResults(String filePath, UserQuery thisUserQuery) {
        try {
            BufferedWriter resultsFileWrite = new BufferedWriter(
                    new FileWriter(filePath, true));
            resultsFileWrite
                    .write("\n" + thisUserQuery.getUsername() + "," + thisUserQuery.getFirstWord() + ","
                            + thisUserQuery.getSecondWord() + "," + thisUserQuery.getIsQueryAnAnagram());
            resultsFileWrite.close();
        } catch (IOException fileNotFound) {
            System.out.println("Results file not found in result writing");
        }
    }

    /**
     * This function will loop through the list of already ran queries read in from
     * the results file.
     * If the query has been run before then 'true' is returned else 'false' is
     * returned.
     * If the current query has been run before the isAnagram variable in the class
     * UserSearch
     * is set to the value from this already ran query.
     * 
     * @param pastResultsList Stores an ArrayList of ArrayLists with each ArrayList
     *                        containing one search query. i.e. username, firstword,
     *                        secondword and isAnagram.
     * @param thisUserSearch  An instance of the object UserString, this object
     *                        stores all the information about the current search.
     * @return boolean true if currentQuery is within pastResultsList or boolean
     *         false if not.
     */
    public boolean isCurrentTestInHistory(ArrayList<UserQuery> pastResultsList,
            UserQuery thisUserQuery) {
        for (UserQuery result : pastResultsList) {
            String thisTest = thisUserQuery.getFirstWord() + thisUserQuery.getSecondWord();
            // If the current 'result' in the pastResultsList has anything but for values it
            // is invalid and should be ignored

            if (thisTest.equals(result.getFirstWord() + result.getSecondWord())
                    || thisTest.equals(result.getFirstWord() + result.getSecondWord())) {
                thisUserQuery.setIsQueryAnAnagram(result.getIsQueryAnAnagram());
                return true;
            } else
                continue;
        }
        return false;
    }

    public static void main(String[] args) {

        AnagramChecker myAnagramChecker = new AnagramChecker();

        String storedResultsFilePath = System.getProperty("user.dir")
                + "\\src\\main\\resources\\anagramResults.txt";

        // Create a new instance of UserSearch and populate it with values using
        // the readQueryInfoFromUser() within UserQuery
        UserQuery thisUserQuery = new UserQuery();
        thisUserQuery.readQueryInfoFromUser();

        // Read in previoused stored results from the result file
        // Store these results in an array of UserSearch
        ArrayList<UserQuery> pastResultsList = myAnagramChecker.readInResults(storedResultsFilePath);

        if (myAnagramChecker.isCurrentTestInHistory(pastResultsList, thisUserQuery)) {
            System.out.println("Duplicate Values Found: ");
            myAnagramChecker.writeToResults(storedResultsFilePath, thisUserQuery);
        } else {
            thisUserQuery
                    .setIsQueryAnAnagram(
                            myAnagramChecker.areStringsAnagrams(thisUserQuery.getFirstWord(),
                                    thisUserQuery.getSecondWord()));
            myAnagramChecker.writeToResults(storedResultsFilePath, thisUserQuery);
        }

        System.out.println("\nAre words '" + thisUserQuery.getFirstWord() + "' and '" + thisUserQuery.getSecondWord()
                + "' anagrams -> " + thisUserQuery.getIsQueryAnAnagram());
    }
}