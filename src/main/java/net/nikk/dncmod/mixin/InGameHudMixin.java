package net.nikk.dncmod.mixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper
{
    @Inject(at = @At("HEAD"), method = "renderHealthBar", cancellable = true)
    private void renderNoHealthbar(CallbackInfo ci) {ci.cancel();}
    @Inject(at= @At("HEAD"), method="renderExperienceBar", cancellable = true)
    private void renderNoExperienceBar(CallbackInfo ci){
        ci.cancel();
    }
}
