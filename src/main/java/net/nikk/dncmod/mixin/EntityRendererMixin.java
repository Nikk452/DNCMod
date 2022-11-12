package net.nikk.dncmod.mixin;

import net.minecraft.nbt.NbtCompound;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
@SuppressWarnings("unused")
@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    MinecraftClient minecraft = MinecraftClient.getInstance();

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    protected void insightSkill(T entity, Text text, MatrixStack matrices,
                                        VertexConsumerProvider vertexConsumers, int light, CallbackInfo callbackInfo) {
        if(entity instanceof PlayerEntity){
            NbtCompound viewer = ((IEntityDataSaver)minecraft.player).getPersistentData();
            if(viewer.getBoolean("created")){
                if(viewer.getIntArray("skills")[14]>=0){
                    int p_invest = viewer.getIntArray("skills")[14]+viewer.getIntArray("stat_mod")[3]+8;
                    if(10>=p_invest) callbackInfo.cancel();
                } else if (viewer.getIntArray("skills")[20]>=0) {
                    int p_insight = viewer.getIntArray("skills")[20]+viewer.getIntArray("stat_mod")[4]+8;
                    if(10>=p_insight) callbackInfo.cancel();
                } else callbackInfo.cancel();
            }
        }
    }

}