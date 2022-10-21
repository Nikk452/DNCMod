package net.nikk.dncmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@SuppressWarnings("unused")
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", cancellable = true)
    private void viewOwnLabel(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        if (entity == MinecraftClient.getInstance().cameraEntity) ci.setReturnValue(MinecraftClient.isHudEnabled());
    }
}
