package boggle;

public class TimeRush {
    private final long startTime;

    public TimeRush() {
        this.startTime = System.currentTimeMillis();
    }

    public float getTimeInSeconds() {
        return Math.round((System.currentTimeMillis() - startTime) / 1000f);
    }

    public String printTime(){
        int minutes = (int) ((this.getTimeInSeconds() % 3600) / 60);
        int seconds = (int) (this.getTimeInSeconds() % 60);
        return String.format(minutes +" minutes and "+seconds+ " seconds");
    }
}
