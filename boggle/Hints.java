package boggle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Hints {

    BoggleStats gameStats;
    private String hintWord;

    public Hints(BoggleStats gameStats){
        this.gameStats = gameStats;
    }

    // This method prints the first and last letter of a random word as a hint
    public boolean getHint(Map<String, ArrayList<Position>> allWords, boolean hintGet){

        if (!hintGet){
            Random r = new Random();
            Object[] hintWords = allWords.keySet().toArray();
            hintWord = (String) hintWords[r.nextInt(hintWords.length)];
            System.out.println("starts with " + hintWord.toLowerCase().charAt(0) +
                    " and ends with " + hintWord.toLowerCase().charAt(hintWord.length()-1));
        }
        else{
            System.out.println("you already got a hint");
        }

        return true;

    }

    public void deductScore(boolean hintGet){
        if (!hintGet){
            float a = (float) (gameStats.getpScoreTotal()-1);
            gameStats.setpScoreTotal((int) a);
        }
    }
    public String getHintWord(){
        return this.hintWord;
    }


}
