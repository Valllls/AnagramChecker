package anagramchecker;

import java.util.*;

public class UserQuery {

    String username;
    String firstWord;
    String secondWord;
    boolean isQueryAnAnagram;

    public boolean getIsQueryAnAnagram() {
        return isQueryAnAnagram;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public void setIsQueryAnAnagram(boolean isQueryAnAnagram) {
        this.isQueryAnAnagram = isQueryAnAnagram;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    public UserQuery() {
    }

    public UserQuery(String username, String firstWord, String secondWord, boolean isQueryAnAnagram) {
        this.username = username;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.isQueryAnAnagram = isQueryAnAnagram;
    }

    public UserQuery readQueryInfoFromUser() {
        System.out.println("--Reading in UserQuery--");
        Scanner userInputScanner = new Scanner(System.in);
        System.out.print("Username: ");
        this.setUsername(userInputScanner.nextLine());
        System.out.print("First Word: ");
        this.setFirstWord(userInputScanner.nextLine());
        System.out.print("Second Word: ");
        this.setSecondWord(userInputScanner.nextLine());
        System.out.println();
        userInputScanner.close();

        // Confirms that the UserQuery parameters are not empty
        if (this.getUsername().isEmpty() || this.getFirstWord().isEmpty() || this.getSecondWord().isEmpty()) {
            System.out.println("ERROR: One of the entered strings was empty");
            System.exit(0);
        }

        // The initial release of the software ignores all Strings that contain special
        // characters or spaces.
        // The if statement below will close the program if either the first or second
        // words contain anything but Alphabetic characters.
        if (!this.getFirstWord().matches("[a-zA-Z]+") || !this.getSecondWord().matches("[a-zA-Z]+")) {
            System.out.println("ERROR: First Word and Second Word contain special characters (i.e. non-alphabetic)");
            System.exit(0);
        }
        return this;
    }
}