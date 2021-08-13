package ru.fazziclay.fazziclaylibs.util;

public class ExceptionUtils {
    public static String getStackTrace(Throwable exception) {
        StringBuilder stackTrace = new StringBuilder();
        int i = 0;
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            stackTrace.append(" at-").append(i).append(" ").append(stackTraceElement.toString()).append("\n");
            i++;
        }

        return stackTrace.toString();
    }
}
