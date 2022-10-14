package net.nikk.dncmod.tick;

import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.config.SystemTimeConfig;
import net.nikk.dncmod.util.ITimeOperations;

import java.util.function.LongSupplier;

public class SystemTimeTicker implements Ticker{

    protected long timeZoneOffset;
    protected final LongSupplier systemTime = () -> System.currentTimeMillis() + timeZoneOffset;

    protected int tickMod;
    protected int dayMod;
    protected int nightMod;
    protected int ticks = 0;

    long dayD;
    long nightD;
    int dayF;
    int nightF;
    int sunrise;
    int sunset;

    public SystemTimeTicker(ITimeOperations world, SystemTimeConfig config){
        this.sunrise = config.getSunriseMs();
        this.sunset = config.getSunsetMs();
        this.timeZoneOffset = config.getTimeOffset();
        if(sunrise < sunset){
            dayD = sunset - sunrise;
            nightD = 86_400_000 - dayD;
        } else {
            nightD = sunrise - sunset;
            dayD = 86_400_000 - nightD;
        }
        this.dayF = (int) (dayD / 12000);
        this.nightF = (int) (nightD / 12000);

        this.dayMod = dayF / 50;
        this.nightMod = nightF / 50;

        int startingTick = calculateCurrentTick();
        world.setTimeOfDayDNC(unwrapTime(startingTick, world.getTimeOfDayDNC()));
    }

    public void tick(ITimeOperations world, boolean nskip, int acceleration){
        ++ticks;
        long time = world.getTimeOfDayDNC();
        tickMod = time % 24000L < 12000 ? dayMod : nightMod;
        if(ticks % tickMod == 0){
            world.setTimeOfDayDNC(time + 1L);
        }
        if(!world.isClient() && ticks % 6000 == 0){
            DNCMod.LOGGER.info("Checking if time corrections is needed...");
            int targetTicks = calculateCurrentTick();
            int timeTicks = (int) (time % 24000L);
            if(targetTicks != timeTicks){
                world.setTimeOfDayDNC(unwrapTime(targetTicks, time));
                DNCMod.LOGGER.info("Time corrected");
            } else DNCMod.LOGGER.info("Skipping correction");
        }
    }

    public void updateTime(ITimeOperations world){
        world.setTimeOfDayDNC(unwrapTime(calculateCurrentTick(), world.getTimeOfDayDNC()));
    }

    private int calculateCurrentTick(){
        long time = calculateElapsedTimeWithShift();
        int returnVal;
        if(time < dayD){
            returnVal = (int) (time / dayF);
        } else returnVal = (int) (12000 + ((time - dayD) / nightF));
        return returnVal;
    }

    private long calculateElapsedTimeWithShift(){
        long currentTime = systemTime.getAsLong()  % 86_400_000;
        int val = (int) (currentTime - sunrise); /// currentTime - shift
        if(val < 0){
            val = (int) (86_400_000 - (sunrise - currentTime)); //shift - currentTime
        }
        return val;
    }

    private long unwrapTime(int targetTime, long timeOfDay){
        long zeroPoint = timeOfDay - timeOfDay % 24000L;
        return zeroPoint + targetTime;
    }

    public int getSunrise(){
        return this.sunrise;
    }
    public int getSunset(){
        return this.sunset;
    }
    public int getDayMod(){
        return this.dayMod;
    }
    public int getNightMod(){
        return this.nightMod;
    }
    public long getDayD(){
        return this.dayD;
    }
    public long getNightD(){
        return this.nightD;
    }
}