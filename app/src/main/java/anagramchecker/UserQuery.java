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
        Scanner userInputScanner = new Scanner(System.in);

        try {
            System.out.print("Username: ");
            this.setUsername(userInputScanner.nextLine());
            System.out.print("First Word: ");
            this.setFirstWord(userInputScanner.nextLine());
            System.out.print("Second Word: ");
            this.setSecondWord(userInputScanner.nextLine());
            userInputScanner.close();
        } catch (NoSuchElementException error) {
            System.out.println("Entered String was empty");
        }
        return this;
    }
}