package net.nikk.dncmod.util;

import net.nikk.dncmod.tick.Ticker;

public interface ITimeOperations {
    Ticker getTimeTicker();
    void setTimeTicker(Ticker timeTicker);
    void setTimeOfDayDNC(long time);
    long getTimeDNC();
    long getTimeOfDayDNC();
    boolean isClient();
    void setSkipState(boolean bl);
    void setSpeed(int speed);
}