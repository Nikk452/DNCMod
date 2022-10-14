package net.nikk.dncmod.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.util.math.MathHelper.wrapDegrees;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    private ClientWorld world;
    private float deltaTime = 0;
    private float prevSkyAngle;
    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at =@At("HEAD"))
    private void captureDelta(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci){
        this.deltaTime = tickDelta;
    }

    @Redirect(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at =@At(value = "INVOKE", target = "net/minecraft/client/util/math/MatrixStack.multiply(Lnet/minecraft/util/math/Quaternion;)V"))
    private void lerpSky(MatrixStack matrices, Quaternion quaternion){
        float skyAngle = this.world.getTimeOfDay();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(rotLerp(this.deltaTime, this.prevSkyAngle * 360.0F,skyAngle * 360.0F)));
        this.prevSkyAngle = skyAngle;
    }
    private static float rotLerp(float f, float g, float h) {
        return g + f * wrapDegrees(h - g);
    }

}
