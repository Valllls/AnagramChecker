package anagramchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import java.io.*;

public class AnagramChecker {

    public AnagramChecker() {
    }

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
        // If the strings do not match in length they cannot be anagrams therefore
        // return false
        if (firstString.length() != secondString.length())
            return false;
        else {
            // Create a hashmap to store the occurances of each character in the two strings
            HashMap<Character, Integer> countCharacterOccurances = new HashMap<Character, Integer>();

            for (int i = 0; i < firstString.length(); i++) {

                // For each instance of a char in 'firstString' add one to the value in the
                // hashmap 'countCharacterOccurances' with this char as its key.
                // For each instance of a char in 'secondString' remove one to the value in the
                // hashmap 'countCharacterOccurances' with this char as its key.
                countCharacterOccurances.put(Character.toLowerCase(firstString.charAt(i)),
                        countCharacterOccurances.getOrDefault(Character.toLowerCase(firstString.charAt(i)), 0) + 1);
                countCharacterOccurances.put(Character.toLowerCase(secondString.charAt(i)),
                        countCharacterOccurances.getOrDefault(Character.toLowerCase(secondString.charAt(i)), 0) - 1);
            }

            // If the strings are anagrams then the occurances of each char should have
            // canceled out, i.e. each char(key) in the Hashmap 'countCharacterOccurances'
            // will have a value of 0.
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
     * Each query from this results file is stored as a UserQuery object
     * within an ArrayList.
     * 
     * @param filePath The filepath to the results file.
     * @return An ArrayList of the UserQueries read in from the results file.
     */
    public ArrayList<UserQuery> readInResults(String filePath) {
        System.out.print("Reading in past results from the results file...");
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
                            "\nError with reading in the query results, some queries did not have to required four values (username, firstWord, secondWord, isQueryAnAnagram)");
                }
            }
            resultsFileReader.close();
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("Results file not found in result reading");
        }
        System.out.println(" Done");
        return pastResultsList;
    }

    /**
     * Given a UserQuery this method will write to the results file the information
     * within it. In the format:
     * \nusername, firstWord, secondWord, isQueryAnAnagram
     * 
     * @param filePath      The filepath to the results file.
     * @param thisUserQuery The UserQuery to be written to the results file
     */
    public void writeToResults(String filePath, UserQuery thisUserQuery) {
        System.out.print("Writing query result to the results file...");
        try {
            BufferedWriter resultsFileWrite = new BufferedWriter(
                    new FileWriter(filePath, true));
            resultsFileWrite
                    .write("\n" + thisUserQuery.getUsername() + "," + thisUserQuery.getFirstWord() + ","
                            + thisUserQuery.getSecondWord() + "," + thisUserQuery.getIsQueryAnAnagram());
            resultsFileWrite.close();
        } catch (IOException fileNotFound) {
            System.out.println("\nResults file not found in result writing");
        }
        System.out.println(" Done");
    }

    /**
     * This function will loop through the list of already ran queries read in from
     * the results file.
     * If the query is present in the ArrayList of past queries 'true' is returned
     * if not 'false' is returned.
     * If the query was previously ran then IsQueryAnAngram within the passed in
     * UserQuery object is set to the result from this previous query.
     * 
     * @param pastResultsList Stores a cache of previously run queries, these are
     *                        read in from the results file in the readInResults
     *                        function.
     * @param thisUserQuery   An instance of the object UserQuery, this object
     *                        stores all the information about the current query.
     * @return boolean true if currentQuery is within pastResultsList or boolean
     *         false if not.
     */
    public boolean isCurrentQueryInHistory(ArrayList<UserQuery> pastResultsList,
            UserQuery thisUserQuery) {
        System.out.print("Searching the results list for the current Query...");

        for (UserQuery result : pastResultsList) {
            String thisQuery = thisUserQuery.getFirstWord() + thisUserQuery.getSecondWord();
            // If the current 'result' in the pastResultsList has anything but for values it
            // is invalid and should be ignored
            if (thisQuery.equals(result.getFirstWord() + result.getSecondWord())
                    || thisQuery.equals(result.getFirstWord() + result.getSecondWord())) {
                thisUserQuery.setIsQueryAnAnagram(result.getIsQueryAnAnagram());
                System.out.println(" Done\n[Current query was found in the list of previous results]");
                return true;
            } else
                continue;
        }
        System.out.print(" Done\n[Current query was not found in the list of previous results]");
        return false;
    }

    public static void main(String[] args) {

        AnagramChecker myAnagramChecker = new AnagramChecker();

        // Currently this filepath is hardcoded to a set value however in future
        // versions this could be read in from user input much like the other query
        // information
        String storedResultsFilePath = System.getProperty("user.dir")
                + "\\src\\main\\resources\\anagramResults.txt";

        // Create a new instance of UserQuery and populate it with values using
        // the readQueryInfoFromUser() function within UserQuery.
        UserQuery thisUserQuery = new UserQuery();
        thisUserQuery.readQueryInfoFromUser();

        // Read in previous stored results from the result file
        // Store these results in an ArrayList of UserQuery
        ArrayList<UserQuery> pastResultsList = myAnagramChecker.readInResults(storedResultsFilePath);

        if (myAnagramChecker.isCurrentQueryInHistory(pastResultsList, thisUserQuery)) {
            myAnagramChecker.writeToResults(storedResultsFilePath, thisUserQuery);
        } else {
            thisUserQuery.setIsQueryAnAnagram(
                    myAnagramChecker.areStringsAnagrams(thisUserQuery.getFirstWord(),
                            thisUserQuery.getSecondWord()));
            myAnagramChecker.writeToResults(storedResultsFilePath, thisUserQuery);
        }

        System.out.println(
                "\n--Results--\nAre words '" + thisUserQuery.getFirstWord() + "' and '" + thisUserQuery.getSecondWord()
                        + "' anagrams -> " + (thisUserQuery.getIsQueryAnAnagram() ? "Yes" : "No"));
    }
}