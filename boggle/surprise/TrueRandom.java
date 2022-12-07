package boggle.surprise;

import java.util.Random;

public class TrueRandom {
    // make sure that our random is seeded with the time of the program launch, making it more or less truly random
    static private final Random random = new Random(System.currentTimeMillis());

    static public double nextDouble() {
        return random.nextDouble();
    }

    static public int nextInt(int min, int max) {
        return random.nextInt(min, max);
    }
}
