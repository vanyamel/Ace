package boggle;
import java.util.*;

public class timeRush {
    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public float getTimeInSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000f;
    }

    public float scoreMultiplier(float time){
        // placeholder
        int wordScore = 1;

        if(time <= 15){
            wordScore*=2;
        }
        else if(time <= 30){
            wordScore*=1.5;
        }
        else if(time <= 45){
            wordScore*=1.3;
        }
        return wordScore;
    }
}
