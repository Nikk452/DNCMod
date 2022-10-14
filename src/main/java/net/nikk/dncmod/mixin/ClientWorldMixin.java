package net.nikk.dncmod.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.config.TimeDataStorage;
import net.nikk.dncmod.tick.DefaultTicker;
import net.nikk.dncmod.tick.SystemTimeTicker;
import net.nikk.dncmod.tick.Ticker;
import net.nikk.dncmod.tick.TimeTicker;
import net.nikk.dncmod.util.ITimeOperations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World implements ITimeOperations {


    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Shadow
    public abstract void setTimeOfDay(long l);


    protected Ticker timeTicker;

    private boolean skipState = false;
    private int speed = 0;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void attachTimeData(ClientPlayNetworkHandler networkHandler, ClientWorld.Properties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimensionTypeEntry, int loadDistance, int simulationDistance, Supplier<Profiler> profiler, WorldRenderer worldRenderer, boolean debugWorld, long seed, CallbackInfo ci){
        String worldId = this.getRegistryKey().toString();
        if(this.getRegistryKey().getRegistry()==OVERWORLD.getRegistry()){
            this.timeTicker = new TimeTicker(48000, 24000);
        } else this.timeTicker = new DefaultTicker();
    }

    @Redirect(method = "tickTime", at = @At(value = "INVOKE", target = "net/minecraft/client/world/ClientWorld.setTimeOfDay (J)V"))
    private void customTicker(ClientWorld clientWorld, long timeOfDay) {
        timeTicker.tick(this, skipState, speed);
    }

    @Override
    public Ticker getTimeTicker() {
        return this.timeTicker;
    }

    @Override
    public void setTimeTicker(Ticker timeTicker) {
        this.timeTicker = timeTicker;
    }

    @Override
    public void setTimeOfDayDNC(long time) {
        this.setTimeOfDay(time);
    }

    @Override
    public long getTimeDNC() {
        return this.getLevelProperties().getTime();
    }

    @Override
    public long getTimeOfDayDNC() {
        return this.getLevelProperties().getTimeOfDay();
    }

    public boolean isClient() {
        return this.isClient;
    }

    @Override
    public void setSkipState(boolean bl) {
        this.skipState = bl;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}