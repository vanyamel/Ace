package boggle.surprise;

public class Bets {
    public static boolean chance(double chance) {
        if (chance > 1.0 || chance < 0.0)
            throw new BadBetException("Invalid bet chance");

        return TrueRandom.nextDouble() < chance;
    }

    public static int multiplier(int points) {
        int value = TrueRandom.nextInt(Multiplier.POINT_ONE.ordinal(), Multiplier.TEN.ordinal());

        Multiplier result = Multiplier.values()[value];

        switch (result) {
            case POINT_ONE: return (int) (value * 0.1);
            case POINT_TWO: return (int) (value * 0.2);
            case POINT_FIVE: return (int) (value * 0.5);
            case ONE: return value;
            case TWO: return value * 2;
            case TWO_POINT_FIVE: return (int) (value * 2.5);
            case TEN: return value * 10;

            default:
                break;
        }
        
        throw new BadBetException("Invalid multiplier generated");
    }

    enum Multiplier {
        POINT_ONE,
        POINT_TWO,
        POINT_FIVE,
        ONE,
        TWO,
        TWO_POINT_FIVE,
        TEN
    }
}
