package ru.fazziclay.fazziclaylibs.logging;

import ru.fazziclay.fazziclaylibs.util.ExceptionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Logger {
    public String getTimeFormat() {
        return "dd.MM HH:mm:ss:SSS";
    }

    public String getTime(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }

    @Deprecated
    public String getDate(String format) {
        return getTime(format);
    }

    public String getLogFormat(String time, String logTag, String logMessage) {
        return String.format("[%s (%s:%s %s) -> (%s:%s %s) %s] %s",
                time,
                fromClass,
                fromLine,
                fromMethod,
                mainClass,
                mainLine,
                mainMethod,
                logTag,
                logMessage);
    }

    /**
     * Inheritance Number.
     * If you inherited from the Logger class in Logger2, then write 1.
     * If you inherited from the Logger2 class in Logger3, then write 2, and so on.
     *
     * This is necessary to correctly determine where the logger was created from,
     * since this is determined using StackTrace, and when inheriting it,
     * this is also specified in it
     * */
    public abstract int getNumberInheriting();

    protected String mainClass = null;
    protected String mainMethod = null;
    protected int mainLine = 0;
    protected String fromClass = null;
    protected String fromMethod = null;
    protected int fromLine = 0;

    public Logger() {
        init();
        function();
    }

    public Logger(String firstMessage) {
        init();
        function(firstMessage);
    }

    protected String getClassFromPath(String path) {
        String[] fromPath = path.split("\\.");
        return fromPath[fromPath.length-1];
    }

    public void init() {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();

        int posStackTraceCurrent = 2 + getNumberInheriting();
        int posStackTraceFrom = 3 + getNumberInheriting();

        if (stackTrace.length > posStackTraceCurrent) {
            mainClass = getClassFromPath(stackTrace[posStackTraceCurrent].getClassName());
            mainMethod = stackTrace[posStackTraceCurrent].getMethodName();
            mainLine = stackTrace[posStackTraceCurrent].getLineNumber();
        }
        if (stackTrace.length > posStackTraceFrom) {
            fromClass = getClassFromPath(stackTrace[posStackTraceFrom].getClassName());
            fromMethod = stackTrace[posStackTraceFrom].getMethodName();
            fromLine = stackTrace[posStackTraceFrom].getLineNumber();
        }
    }

    public void raw(String tag, String message) {
        printLog(getLogFormat(getDate(getTimeFormat()), tag, message));
    }

    public void printLog(String message) {
        System.out.println(message);
    }

    public void done() {
        raw("DONE", "Done!");
    }
    public abstract void clear();
    public abstract boolean isCleared();
    public void info(String message) {raw("INFO", message);}
    public void log(String message) {raw("LOG", message);}
    public void error(String message) {raw("ERROR", message);}
    public void error(Throwable throwable) {
        raw("ERROR", throwable.toString() + "'\n-------------- StackTrace --------------\n" + ExceptionUtils.getStackTrace(throwable) + "-------------- StackTrace end --------------");
    }
    public void errorDescription(String message) {raw("ERROR_DESCRIPTION", message); }
    @Deprecated
    public void exception(Throwable throwable) {
        error(throwable);
    }
    public void function() {raw("FUNCTION_CALLED", "Called!");}
    public void function(String firstMessage) {raw("FUNCTION_CALLED", firstMessage);}
    public void returned(Object obj) {
        String str = null;
        if (obj != null) str = obj.toString();
        raw("RETURNED", str);
    }
    public void returned() {
        raw("RETURNED", "");
    }
}
