package boggle.surprise;

import java.util.Random;

public class Bets {
    private static final Random random = new Random();
    public static boolean chance(double chance) throws BadBetException {
        if (chance > 1.0 || chance < 0.0)
            throw new BadBetException("Invalid bet chance");

        return random.nextDouble() < chance;
    }

    public static int multiplier(int points) throws BadBetException {
        int value = random.nextInt(Multiplier.POINT_ONE.ordinal(), Multiplier.TEN.ordinal());

        Multiplier result = Multiplier.values()[value];

        switch (result) {
            case POINT_ONE: return (int) (points * 0.1);
            case POINT_TWO: return (int) (points * 0.2);
            case POINT_FIVE: return (int) (points * 0.5);
            case ONE: return points;
            case TWO: return points * 2;
            case TWO_POINT_FIVE: return (int) (points * 2.5);
            case TEN: return points * 10;

            default: break;
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
