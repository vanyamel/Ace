package boggle.surprise;

public enum BetMode {
    MULTIPLIER,
    CHANCE;

    @Override
    public String toString() {
        return switch (this) {
            case MULTIPLIER -> "Multiplier";
            case CHANCE -> "Chance";
        };

    }

    public static BetMode fromString(String str) {
        return switch (str) {
            case "Multiplier" -> MULTIPLIER;
            case "Chance" -> CHANCE;
            default -> null;
        };

    }
}
