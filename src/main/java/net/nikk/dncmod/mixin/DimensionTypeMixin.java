package net.nikk.dncmod.mixin;

import net.minecraft.world.dimension.DimensionType;
import net.nikk.dncmod.DNCMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalLong;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {
    @Shadow @Final
    private OptionalLong fixedTime;
    private final double factor = 1.0D / 24000D;

    @Inject(method = "getSkyAngle", at =@At("HEAD"), cancellable = true)
    private void patchSkyAngle(long time, CallbackInfoReturnable<Float> cir){
        if(DNCMod.CONFIG.patchSkyAngle && fixedTime.isEmpty()) {
            double d = time % 24000L * factor - 0.25D;
            if (d < 0)
                ++d;
            cir.setReturnValue((float) d);
        }
    }
}
