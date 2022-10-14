package net.nikk.dncmod.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.config.TimeDataStorage;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.tick.DefaultTicker;
import net.nikk.dncmod.tick.SystemTimeTicker;
import net.nikk.dncmod.tick.Ticker;
import net.nikk.dncmod.tick.TimeTicker;
import net.nikk.dncmod.util.ITimeOperations;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements ITimeOperations {



    @Shadow @Final
    List<ServerPlayerEntity> players;

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }


    @Shadow public abstract void setTimeOfDay(long l);

    protected Ticker timeTicker;

    protected boolean enableNightSkipAcceleration = false;
    protected int accelerationSpeed = 0;

    private boolean shouldUpdateNSkip = true;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void attachTimeData(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime, CallbackInfo ci){
        String worldId = this.getRegistryKey().toString();
        if(this.getRegistryKey().getRegistry()==OVERWORLD.getRegistry()){
            this.timeTicker = new TimeTicker(48000 , 24000);
        } else this.timeTicker = new DefaultTicker();
    }

    @Inject(method = "updateSleepingPlayers", at =@At("HEAD"), cancellable = true)
    private void patchNightSkip(CallbackInfo ci){
        if(DNCMod.CONFIG.syncWithSystemTime){
            ci.cancel();
        } else if (DNCMod.CONFIG.enableNightSkipAcceleration){
            List<ServerPlayerEntity> totalPlayers = this.players.stream().filter(player -> !player.isSpectator() || !player.isCreative()).collect(Collectors.toList());
            if(totalPlayers.size() > 0) {
                int sleepingPlayers = (int) totalPlayers.stream().filter(ServerPlayerEntity::isSleeping).count();
                double factor = (double) sleepingPlayers / totalPlayers.size();
                int threshold = DNCMod.CONFIG.enableThreshold ? totalPlayers.size() / 100 * DNCMod.CONFIG.thresholdPercentage : 0;
                if (sleepingPlayers > threshold) {
                    enableNightSkipAcceleration = true;
                    this.accelerationSpeed = DNCMod.CONFIG.enableThreshold && DNCMod.CONFIG.flatAcceleration ?
                            DNCMod.CONFIG.accelerationSpeed :
                            (int) Math.ceil(DNCMod.CONFIG.accelerationSpeed * factor);
                } else enableNightSkipAcceleration = false;
            } else enableNightSkipAcceleration = false;
            if(this.shouldUpdateNSkip) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(enableNightSkipAcceleration);
                buf.writeInt(accelerationSpeed);
                this.players.forEach(player -> ServerPlayNetworking.send(player, Networking.NIGHT_SKIP_INFO, buf));
            }
            ci.cancel();
        }
    }

    @Inject(method = "addPlayer", at =@At("HEAD"))
    private void onPlayerJoin(ServerPlayerEntity player, CallbackInfo ci){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(enableNightSkipAcceleration);
        buf.writeInt(accelerationSpeed);
        ServerPlayNetworking.send(player, Networking.NIGHT_SKIP_INFO, buf);
    }

    @Inject(method = "wakeSleepingPlayers", at =@At("HEAD"))
    private void preventPacketsSpam(CallbackInfo ci){
        this.shouldUpdateNSkip = false;
    }

    @Inject(method = "wakeSleepingPlayers", at =@At("TAIL"))
    private void preventPacketsSpamEnd(CallbackInfo ci){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(enableNightSkipAcceleration);
        buf.writeInt(accelerationSpeed);
        this.players.forEach(player -> ServerPlayNetworking.send(player, Networking.NIGHT_SKIP_INFO, buf));
        this.shouldUpdateNSkip = true;
    }

    @Redirect(method = "tickTime", at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld.setTimeOfDay(J)V"))
    private void customTicker(ServerWorld world, long timeOfDay) {
        this.timeTicker.tick(this, enableNightSkipAcceleration, accelerationSpeed);
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
    public void setTimeOfDayDNC(long time) {this.setTimeOfDay(time);}

    @Override
    public long getTimeDNC() {
        return this.getLevelProperties().getTime();
    }

    @Override
    public long getTimeOfDayDNC() {
        return this.getLevelProperties().getTimeOfDay();
    }

    @Override
    public boolean isClient() {
        return this.isClient;
    }

    @Override
    public void setSkipState(boolean bl) {}

    @Override
    public void setSpeed(int speed) {}
}
