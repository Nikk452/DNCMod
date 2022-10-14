package net.nikk.dncmod.mixin;

import net.minecraft.server.world.SleepManager;
import net.nikk.dncmod.DNCMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SleepManager.class)
public class SleepManagerMixin {
    @Inject(method = "canSkipNight", at =@At("HEAD"), cancellable = true)
    private void disableNightSkip(int percentage, CallbackInfoReturnable<Boolean> cir){
        if(DNCMod.CONFIG.syncWithSystemTime || DNCMod.CONFIG.enableNightSkipAcceleration){
            cir.setReturnValue(false);
        }
    }
}
