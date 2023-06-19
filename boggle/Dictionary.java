package boggle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The Dictionary class for the first Assignment in CSC207, Fall 2022
 * The Dictionary will contain lists of words that are acceptable for Boggle
 */
public class Dictionary {

    /**
     * set of legal words for Boggle
     */
    private TreeSet<String> legalWords;

    /**
     * Class constructor
     *
     * @param filename the file containing a list of legal words.
     */
    public Dictionary(String filename) {
        String line = "";
        int wordcount = 0;
        this.legalWords = new TreeSet<String>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null)
            {
                if (line.strip().length() > 0) {
                    legalWords.add(line.strip());
                    wordcount++;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Initialized " + wordcount + " words in the Dictionary.");;
    }

    /*
     * Checks to see if a provided word is in the dictionary.
     *
     * @param word  The word to check
     * @return  A boolean indicating if the word has been found
     */
    public boolean containsWord(String word){
        for (String legalWord : legalWords) {
            word = word.toLowerCase();
            if (word.equals(legalWord)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks to see if a provided string is a prefix of any word in the dictionary.
     *
     * @param str  The string to check
     * @return  A boolean indicating if the string has been found as a prefix
     */
    public boolean isPrefix(String str){
        boolean prefix = true;
        for (String legalWord : legalWords) {
            str = str.toLowerCase();
            int i = 0;
            if(str.length() <= legalWord.length()) {
                while (i <= str.length() - 1 && i <= legalWord.length() - 1) {
                    if (str.charAt(i) == legalWord.charAt(i)) {
                        prefix = true;
                    } else if (str.charAt(i) != legalWord.charAt(i)) {
                        prefix = false;
                        break;
                    }
                    i++;
                }
                if (prefix) {
                    return true;
                }
            }
        }
        return false;
    }

}

