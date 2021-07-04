package ru.fazziclay.fazziclaylibs;

import java.util.List;

public class ArrayUtils {
    public static boolean removeByValue(final List list, Object value) {
        for (Object o : list) {
            if (o == value) {
                list.remove(o);
                return true;
            }
        }
        return false;
    }
}
