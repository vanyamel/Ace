package boggle;
import java.util.*;

public class timeRush {
    private long startTime;

    private BoggleStats gameStats;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public float getTimeInSeconds() {

        return (System.currentTimeMillis() - startTime) / 1000f;
    }

    public void scoreMultiplier(float time){
        System.out.println("points*2 if time spent is less than 2 minutes");
        System.out.println("points*1.5 if time spent is less than 3 minutes");
        System.out.println("points*1.2 if time spent is less than 5 minutes");
        if(time <= 120){
            gameStats.setPlayerScore(gameStats.getScore()*2);
        }
        else if(time <= 180){
            gameStats.setPlayerScore((int) (gameStats.getScore()*1.5));
        }
        else if(time <= 300){
            gameStats.setPlayerScore((int) (gameStats.getScore()*1.2));
        }
    }
}
