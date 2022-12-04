package boggle;
import java.util.*;

public class timeRush {
    private long startTime;

    public timeRush() {
        this.startTime = System.currentTimeMillis();
    }

    public float getTimeInSeconds() {
        return Math.round((System.currentTimeMillis() - startTime) / 1000f);
    }
}
