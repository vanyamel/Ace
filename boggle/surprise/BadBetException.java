package boggle.surprise;

public class BadBetException extends RuntimeException {
    public BadBetException(String reason) {
        super(reason);
    }
}
