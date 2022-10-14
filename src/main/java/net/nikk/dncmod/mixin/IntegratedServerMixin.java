package net.nikk.dncmod.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.DataFixer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ApiServices;
import net.minecraft.util.SystemDetails;
import net.minecraft.world.level.storage.LevelStorage;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.tick.SystemTimeTicker;
import net.nikk.dncmod.tick.Ticker;
import net.nikk.dncmod.util.ITimeOperations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.net.Proxy;
import java.util.function.BooleanSupplier;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin extends MinecraftServer {

    @Shadow
    private boolean paused;
    private boolean shouldUpdate = false;

    public IntegratedServerMixin(Thread serverThread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, ApiServices apiServices, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory) {
        super(serverThread, session, dataPackManager, saveLoader, proxy, dataFixer, apiServices, worldGenerationProgressListenerFactory);
    }


    @Inject(method = "tick", at =@At("TAIL"))
    private void recalculateTime(BooleanSupplier bl, CallbackInfo ci){
        if(DNCMod.CONFIG.syncWithSystemTime) {
            if (this.paused) {
                this.shouldUpdate = true;
            } else if (shouldUpdate) {
                shouldUpdate = false;
                this.getWorlds().forEach(serverWorld -> {
                    Ticker t = ((ITimeOperations) serverWorld).getTimeTicker();
                    if (t instanceof SystemTimeTicker) {
                        ((SystemTimeTicker) t).updateTime((ITimeOperations) serverWorld);
                    }
                });
                DNCMod.LOGGER.info("Game unpaused, time corrected");
            }
        }
    }
}
