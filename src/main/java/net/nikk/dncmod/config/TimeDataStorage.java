package net.nikk.dncmod.config;

public class TimeDataStorage {
    public long dayDuration = 3000;
    public long nightDuration = 3000;

    public TimeDataStorage() {
    }

    public TimeDataStorage(long dayD, long nightD) {
        this.dayDuration = dayD;
        this.nightDuration = nightD;
    }
}