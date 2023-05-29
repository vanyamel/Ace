package boggle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Hints {

    BoggleStats gameStats;
    private String hintWord;

    public Hints(BoggleStats gameStats) {
        this.gameStats = gameStats;
    }

    // This method prints the first and last letter of a random word as a hint
    public boolean getHint(Map<String, ArrayList<Position>> allWords, boolean hintGet) {

        if (!hintGet) {
            Random r = new Random();
            Object[] hintWords = allWords.keySet().toArray();
            hintWord = (String) hintWords[r.nextInt(hintWords.length)];
        }

        return true;

    }

    public String getHintWord() {
        return this.hintWord;
    }


}
