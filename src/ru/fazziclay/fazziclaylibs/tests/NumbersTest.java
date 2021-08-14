package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.ByteUtils;
import ru.fazziclay.fazziclaylibs.NumberUtils;

import java.util.Arrays;

public class NumbersTest {
    public static void main(String[] args) throws InterruptedException {
        if (false) {
            int i = 13412;
            int l = 100;

            System.out.println(NumberUtils.intToFixedLengthString(i, l));
        }

        if (false) {
            int b = String.valueOf(Integer.MAX_VALUE).length();

            int i = 0;
            while (i < 100) {
                int a = NumberUtils.getRandom(Integer.MIN_VALUE, Integer.MAX_VALUE);
                System.out.println(NumberUtils.intToFixedLengthString(a, b));
                i++;
            }
        }

        if (true) {
            int countOfFailed = 0;

            byte b1 = Byte.MIN_VALUE;
            byte b2 = Byte.MIN_VALUE;

            while (!(b1 == 0 && b2 == 0)) {
                if (b1 == Byte.MAX_VALUE) {
                    b1 = Byte.MIN_VALUE;
                    b2++;
                } else {
                    b1++;
                }

                short endShort = ByteUtils.getShort((byte) -128, (byte) -128);
                System.out.println(Arrays.toString(new byte[]{b1, b2}) + " > "+endShort);

                //System.out.println(i+" - "+start+" > " + Arrays.toString(endBytes) + " > " + endShort);
                //if (start != endShort) countOfFailed++;
            }

            //System.out.println("countOfFailed="+countOfFailed);
        }
    }
}
