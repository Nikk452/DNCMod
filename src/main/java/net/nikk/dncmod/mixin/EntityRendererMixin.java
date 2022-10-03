package net.nikk.dncmod.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.LivingEntity;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.text.DecimalFormat;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    MinecraftClient minecraft = MinecraftClient.getInstance();

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    protected void renderLabelIfPresent(T entity, Text text, MatrixStack matrices,
                                        VertexConsumerProvider vertexConsumers, int light, CallbackInfo callbackInfo) {
        double d = minecraft.getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);
        boolean bl2 = entity instanceof PlayerEntity;
        DecimalFormat df = new DecimalFormat("#.#");
        Text infoRow1 = Text.literal("");
        Text infoRow2 = Text.literal("");
        if(entity instanceof LivingEntity) {
            infoRow1 = Text.literal(String.format("%s \u00A7a↔ %s", getHealth((LivingEntity) entity,df),
                    df.format(((LivingEntity) entity).distanceTo(minecraft.player))));
            infoRow2 = Text.literal(String.format("\u00A7d%s %s %s", df.format(((LivingEntity) entity).getPos().x),
                    df.format(((LivingEntity) entity).getPos().y), df.format(((LivingEntity) entity).getPos().z)));
        }
        if (!(d > 4096.0)) {
            boolean bl = !entity.isSneaky();
            float f = entity.getHeight() + 0.5F;
            int i = "deadmau5".equals(text.getString()) ? -10 : 0;
            matrices.push();
            matrices.translate(0.0, (double)f, 0.0);
            matrices.multiply(minecraft.getEntityRenderDispatcher().getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
            int j = (int)(g * 255.0F) << 24;
            TextRenderer textRenderer = ((EntityRenderer)(Object)this).getTextRenderer();
            Text name = text;
            if(entity instanceof PlayerEntity) name = Text.literal(((IEntityDataSaver)entity).getPersistentData().getString("first_name")+" "+((IEntityDataSaver)entity).getPersistentData().getString("last_name"));
            System.out.println(name);
            float infoRow1X = (float) (-textRenderer.getWidth((StringVisitable) infoRow1) / 2);
            float infoRow2X = (float) (-textRenderer.getWidth((StringVisitable) infoRow2) / 2);
            float textX = (float) (-textRenderer.getWidth((StringVisitable) name) / 2);
            int y = -10;
            float h = (float)(-textRenderer.getWidth(name) / 2);
            textRenderer.draw(name, h, (float)i, 553648127, false, matrix4f, vertexConsumers, bl, j, light);
            textRenderer.draw(infoRow1, infoRow1X, (float) y - 10, 553648127, false, matrix4f, vertexConsumers, bl, j, light);
            textRenderer.draw(infoRow2, infoRow2X, (float) y, 553648127, false, matrix4f, vertexConsumers, bl, j, light);
            if (bl) {
                textRenderer.draw(name, h, (float)i, -1, false, matrix4f, vertexConsumers, false, 0, light);
                textRenderer.draw(infoRow1, infoRow1X, (float) y - 10, -1, false, matrix4f, vertexConsumers, false, 0, light);
                textRenderer.draw(infoRow2, infoRow2X, (float) y, -1, false, matrix4f, vertexConsumers, false, 0, light);
            }
            matrices.pop();
        }
        callbackInfo.cancel();
    }

    private static String getHealth(LivingEntity entity, DecimalFormat df) {
        float Health = entity.getHealth() + entity.getAbsorptionAmount();
        if (Health > 20)
            return " \u00A76❤" + df.format(Health);
        else if (Health >= 15.0 && Health <= 20.9)
            return " \u00A7a❤" + df.format(Health);
        else if (Health >= 10.0 && Health <= 14.9)
            return " \u00A7e❤" + df.format(Health);
        else if (Health >= 5.0 && Health <= 9.9)
            return " \u00A7c❤" + df.format(Health);
        else if (Health >= 1.0 && Health <= 4.9)
            return " \u00A74❤" + df.format(Health);
        else
            return " \u00A70❤" + df.format(Health);
    }

}