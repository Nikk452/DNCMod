package net.nikk.dncmod.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin
{
    @Inject(at = @At("HEAD"), method = "renderHealthBar", cancellable = true)
    private void renderNoHealthbar(CallbackInfo ci)
    {
        ci.cancel();
    }
    @Inject(at= @At("HEAD"), method="renderExperienceBar", cancellable = true)
    private void renderNoExperienceBar(CallbackInfo ci){
        ci.cancel();
    }
}
