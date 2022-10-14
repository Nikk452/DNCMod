package net.nikk.dncmod.tick;

import net.nikk.dncmod.util.ITimeOperations;

public interface Ticker {
    void tick(ITimeOperations world, boolean nskip, int acceleration);
}