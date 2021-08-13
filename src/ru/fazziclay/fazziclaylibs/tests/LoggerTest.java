package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.logging.Logger;

public class LoggerTest {
    public static int i = 0;
    public static void main(String[] args) {
        main2();
    }

    public static void main2() {
        main3();
    }
    public static void main3() {
        main4();
    }
    public static void main4() {
        i++;
        if (i > 10550) main5(); else main4();
    }
    public static void main5() {
        TestLogger testLogger = new TestLogger();
        testLogger.log("log!!!");
        testLogger.info("info!!!");
        testLogger.returned();
        testLogger.returned(null);
        testLogger.error("error!!!");
        testLogger.errorDescription("error description!!!");
        testLogger.exception(new Exception("exception!!!"));
        testLogger.done();
    }

    public LoggerTest() {

    }

    @Override
    public String toString() {
        return "hello!!!!!!!!!";
    }

    public static class TestLogger extends Logger {
        @Override
        public int getNumberInheriting() {
            return 1;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean isCleared() {
            return false;
        }
    }
}