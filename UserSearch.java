package CMEGroupAssessment;

public class UserSearch {

    String username;
    String firstWord;
    String secondWord;
    boolean isAnagram;

    public boolean getIsAnagram() {
        return isAnagram;
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

    public void setIsAnagram(boolean isAnagram) {
        this.isAnagram = isAnagram;
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

    public UserSearch() {
    }
}