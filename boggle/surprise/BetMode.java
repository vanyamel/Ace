package boggle.surprise;

public enum BetMode {
    MULTIPLIER,
    CHANCE;


    @Override
    public String toString() {
        switch(this) {
            case MULTIPLIER: return "Multiplier";
            case CHANCE: return "Chance";
        }

        return "?";
    }

    public static BetMode fromString(String str) {
        switch(str) {
            case "Multiplier": return MULTIPLIER;
            case "Chance": return CHANCE;
        }

        return null;
    }
}
