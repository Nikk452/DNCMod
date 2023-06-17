package net.nikk.dncmod.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }
    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    public void render(AbstractClientPlayerEntity clientPlayer, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        NbtCompound nbt = ((IEntityDataSaver)clientPlayer).getPersistentData();
        setVisible(nbt, clientPlayer);
    }
    protected void setVisible(NbtCompound nbt,AbstractClientPlayerEntity clientPlayer) {
        this.model.head.visible = true;
        this.model.hat.visible = true;
        this.model.body.visible = true;
        this.model.rightArm.visible = true;
        this.model.leftArm.visible = true;
        this.model.rightLeg.visible = true;
        this.model.leftLeg.visible = true;

        if (nbt.getBoolean("created")) if(nbt.getIntArray("skills")[5]>=0 && clientPlayer.isSneaking()) {
            this.model.head.visible = false;
            this.model.hat.visible = false;
            this.model.body.visible = false;
            this.model.rightArm.visible = false;
            this.model.leftArm.visible = false;
            this.model.rightLeg.visible = false;
            this.model.leftLeg.visible = false;
        }
    }
}
