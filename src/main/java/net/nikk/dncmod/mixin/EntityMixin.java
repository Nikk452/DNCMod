package net.nikk.dncmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@SuppressWarnings("unused")
@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "isInvisibleTo(Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("RETURN"), cancellable = true)
    private void perceptionSkill(PlayerEntity player, CallbackInfoReturnable<Boolean> ci) {
        NbtCompound nbt = ((IEntityDataSaver)(Object)(this)).getPersistentData();
        NbtCompound nbt2 = ((IEntityDataSaver)(player)).getPersistentData();
        if(nbt.getBoolean("created")) if(nbt.getIntArray("skills")[5]>=0)
            if(((Entity)(Object)(this)).isSneaking()){
                if(nbt2.getBoolean("created")){
                    int perception_p = 8+nbt2.getIntArray("skills")[18]+nbt2.getIntArray("stat_mod")[4];
                    int stealth_p = 8+nbt.getIntArray("skills")[5]+nbt.getIntArray("stat_mod")[1];
                    if(stealth_p<perception_p) ci.setReturnValue(false);
                    else ci.setReturnValue(ci.getReturnValue());
                }else ci.setReturnValue(ci.getReturnValue());
            }
        ci.setReturnValue(ci.getReturnValue());
    }
    @Inject(method = "isInvisible()Z", at = @At("RETURN"), cancellable = true)
    private void stealthSkill(CallbackInfoReturnable<Boolean> ci) {
        NbtCompound nbt = ((IEntityDataSaver)(Object)(this)).getPersistentData();
        if(nbt.getBoolean("created")){
            if(nbt.getIntArray("skills")[5]>=0) {
                if(((Entity)(Object)(this)).isSneaking()){
                    ci.setReturnValue(true);
                }else ci.setReturnValue(ci.getReturnValue());
            }else ci.setReturnValue(ci.getReturnValue());
        }else ci.setReturnValue(ci.getReturnValue());
    }
}
