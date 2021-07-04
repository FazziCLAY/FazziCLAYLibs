package ru.fazziclay.fazziclaylibs;

import java.util.Random;

public class NumberUtils {
    public static int getRandom(int minimum, int maximum) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(maximum - minimum + 1) + minimum;
    }
}
