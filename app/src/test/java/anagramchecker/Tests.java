package anagramchecker;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Tests {

    AnagramChecker myAnagramChecker = new AnagramChecker();

    public ArrayList<UserQuery> beforeListReading() {
        String testResultsFilePathRead = System.getProperty("user.dir")
                + "\\src\\test\\resources\\testAnagramResultRead.txt";
        return myAnagramChecker.readInResults(testResultsFilePathRead);
    }

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

            myAnagramChecker.writeToResults(testFile.toString(), userQuery1);

            assertEquals(-1, Files.mismatch(groundTruthFile, testFile));

        } catch (IOException e) {
            System.out.println("File not found during read");
        }
    }

    @Test
    public void detectPresenceOfQueryInList() {
        UserQuery userQuery1 = new UserQuery("David", "qwerty", "qwreyt", true);
        ArrayList<UserQuery> pastResultsList = new ArrayList<UserQuery>();
        pastResultsList.add(userQuery1);
        assertEquals(true, myAnagramChecker.isCurrentTestInHistory(pastResultsList, userQuery1));
    }

    @Test
    public void detectLackOfQueryInList() {
        UserQuery userQuery1 = new UserQuery("David", "qwerty", "qwreyt", true);
        UserQuery userQuery2 = new UserQuery("Richard", "zxc", "cvb", false);
        ArrayList<UserQuery> pastResultsList = new ArrayList<UserQuery>();
        pastResultsList.add(userQuery1);
        assertEquals(false, myAnagramChecker.isCurrentTestInHistory(pastResultsList, userQuery2));
    }

    @Test
    public void correctlyFindsAnagram() {
        assertEquals(true, myAnagramChecker.areStringsAnagrams("angered", "enraged"));
    }

    @Test
    public void correctlyFindsNoAnagram() {
        assertEquals(false, myAnagramChecker.areStringsAnagrams("angeered", "enraged"));
    }

    @Test
    public void readUsernameFromResultsFile() {
        ArrayList<UserQuery> tempResultsList = beforeListReading();
        assertEquals("David", tempResultsList.get(0).getUsername());
    }

    @Test
    public void readFirstWordFromResultsFile() {
        ArrayList<UserQuery> tempResultsList = beforeListReading();
        assertEquals("qwerty", tempResultsList.get(0).getFirstWord());
    }

    @Test
    public void readSecondWordFromResultsFile() {
        ArrayList<UserQuery> tempResultsList = beforeListReading();
        assertEquals("qwreyt", tempResultsList.get(0).getSecondWord());
    }

    @Test
    public void readFromResultsFile() {
        ArrayList<UserQuery> tempResultsList = beforeListReading();
        assertEquals(true, tempResultsList.get(0).getIsQueryAnAnagram());
    }
}
