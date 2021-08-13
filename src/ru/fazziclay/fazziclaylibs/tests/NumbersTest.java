package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.NumberUtils;

public class NumbersTest {
    public static void main(String[] args) throws InterruptedException {
        if (false) {
            int i = 13412;
            int l = 100;

            System.out.println(NumberUtils.intToFixedLengthString(i, l));
        }

        if (true) {
            int b = String.valueOf(Integer.MAX_VALUE).length();

            int i = 0;
            while (i < 100) {
                int a = NumberUtils.getRandom(Integer.MIN_VALUE, Integer.MAX_VALUE);
                System.out.println(NumberUtils.intToFixedLengthString(a, b));
                i++;
            }
        }
    }
}
