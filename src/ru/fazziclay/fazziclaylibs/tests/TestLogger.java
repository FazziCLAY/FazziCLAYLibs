package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.logging.Logger;

public class TestLogger extends Logger {
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