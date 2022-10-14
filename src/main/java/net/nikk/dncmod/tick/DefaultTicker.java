package net.nikk.dncmod.tick;

import net.nikk.dncmod.util.ITimeOperations;

public class DefaultTicker implements Ticker{

    @Override
    public void tick(ITimeOperations world, boolean nskip, int acceleration) {
        if(nskip) world.setTimeOfDayDNC(world.getTimeOfDayDNC() + acceleration);
        else world.setTimeOfDayDNC(world.getTimeOfDayDNC() + 1L);
    }
}