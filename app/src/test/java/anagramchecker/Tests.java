package anagramchecker;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Tests {

    AnagramChecker myAnagramChecker = new AnagramChecker();

    // This function reads the results from the result list and returns them in the
    // form of an ArrayList of type UserQuery.
    public ArrayList<UserQuery> beforeListReading() {
        String testResultsFilePathRead = System.getProperty("user.dir")
                + "\\src\\test\\resources\\testAnagramResultRead.txt";
        return myAnagramChecker.readInResults(testResultsFilePathRead);
    }

    // This function writes a UserQuery to a results file, the modified text file
    // is then compared to an expected text file. By checking that these two files
    // are equal the test assures that the writing of results to a file is working
    // as intended.
    @Test
    public void writingToFile() {
        Path testFile = Paths.get(System.getProperty("user.dir")
                + "\\src\\test\\resources\\testResultsFile.txt");
        Path groundTruthFile = Paths.get(System.getProperty("user.dir")
                + "\\src\\test\\resources\\testResultsFileGroundTruth.txt");
        UserQuery userQuery1 = new UserQuery("David", "qwerty", "qwreyt", true);

        try {
            // Wipe file
            new PrintWriter(testFile.toString()).close();

            // Write results to file
            myAnagramChecker.writeToResults(testFile.toString(), userQuery1);

            // If the two files contain the same data then Files.mismatch(file1,file2)
            // will return -1.
            assertEquals(-1, Files.mismatch(groundTruthFile, testFile));

        } catch (IOException e) {
            System.out.println("File not found during read");
        }
    }

    // Tests the isCurrentQueryInHistory function to return true when the query does
    // exist in the ArrayList.
    // A UserQuery is added to an ArrayList of type UserQuery. This ArrayList is
    // then searched for this UserQuery with the expectation of it returning true.
    @Test
    public void detectPresenceOfQueryInList() {
        UserQuery userQuery1 = new UserQuery("David", "qwerty", "qwreyt", true);
        ArrayList<UserQuery> pastResultsList = new ArrayList<UserQuery>();
        pastResultsList.add(userQuery1);
        AnagramChecker myAnagramChecker = new AnagramChecker();
        assertEquals(true, myAnagramChecker.isCurrentQueryInHistory(pastResultsList, userQuery1));
    }

    // Tests the isCurrentQueryInHistory function to return false when the query
    // does not exist in the ArrayList.
    // A UserQuery is added to an ArrayList of type UserQuery. This ArrayList is
    // then searched for a different UserQuery with the expectation of it returning
    // false.
    @Test
    public void detectLackOfQueryInList() {
        UserQuery userQuery1 = new UserQuery("David", "qwerty", "qwreyt", true);
        UserQuery userQuery2 = new UserQuery("Richard", "zxc", "cvb", false);
        ArrayList<UserQuery> pastResultsList = new ArrayList<UserQuery>();
        pastResultsList.add(userQuery1);
        AnagramChecker myAnagramChecker = new AnagramChecker();
        assertEquals(false, myAnagramChecker.isCurrentQueryInHistory(pastResultsList, userQuery2));
    }

    // Tests the areStringsAnagrams function to return true if two strings are given
    // that are anagrams.
    @Test
    public void correctlyFindsAnagram() {
        assertEquals(true, myAnagramChecker.areStringsAnagrams("angered", "enraged"));
    }

    // Tests the areStringsAnagrams function to return false if two strings are
    // given that are not anagrams.
    @Test
    public void correctlyFindsNoAnagram() {
        assertEquals(false, myAnagramChecker.areStringsAnagrams("angeered", "enraged"));
    }

    // Tests the readInResults method to confirm that the username String is read in
    // correctly.
    @Test
    public void readUsernameFromResultsFile() {
        ArrayList<UserQuery> tempResultsList = beforeListReading();
        assertEquals("David", tempResultsList.get(0).getUsername());
    }

    // Tests the readInResults method to confirm that the firstWord String is read
    // in correctly.
    @Test
    public void readFirstWordFromResultsFile() {
        ArrayList<UserQuery> tempResultsList = beforeListReading();
        assertEquals("qwerty", tempResultsList.get(0).getFirstWord());
    }

    // Tests the readInResults method to confirm that the secondWord String is read
    // in correctly.
    @Test
    public void readSecondWordFromResultsFile() {
        ArrayList<UserQuery> tempResultsList = beforeListReading();
        assertEquals("qwreyt", tempResultsList.get(0).getSecondWord());
    }

    // Tests the readInResults method to confirm that the isQueryAnAnagram boolean
    // is read in correctly.
    @Test
    public void readFromResultsFile() {
        ArrayList<UserQuery> tempResultsList = beforeListReading();
        assertEquals(true, tempResultsList.get(0).getIsQueryAnAnagram());
    }
}
