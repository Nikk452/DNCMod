package net.nikk.dncmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
@SuppressWarnings("unused")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    float JumpSkillAmp = 0;

    @Shadow
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Redirect(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", at = @At(value = "INVOKE", target = "net/minecraft/entity/LivingEntity.computeFallDamage (FF)I"))
    private int acrobaticsSkill(LivingEntity livingEntity,float fallDistance, float damageMultiplier) {
        int normal_damage = this.computeFallDamage(fallDistance, damageMultiplier);
        if(fallDistance>2) {
            NbtCompound nbt = ((IEntityDataSaver) (LivingEntity) (Object) this).getPersistentData();
            if (nbt.getBoolean("created")) if (nbt.getIntArray("skills")[3] >= 0) {
                double Roll = (this.random.nextBetween(1, 21) + nbt.getIntArray("skills")[3] + nbt.getIntArray("stat_mod")[1]) / 4d;
                normal_damage -= MathHelper.ceil(Roll);
                this.playSound(SoundEvents.BLOCK_WOOL_STEP, 1, 0.9F + this.random.nextFloat() * 0.2F);
                return Math.max(normal_damage, 0);
            }
        }
        return normal_damage;
    }
    @ModifyVariable(method="computeFallDamage(FF)I", at = @At("STORE"), ordinal = 2)
    private float jumpFallDamage(float jumpBoostAMP){
        float x = jumpBoostAMP + this.JumpSkillAmp;
        this.JumpSkillAmp = 0;
        return x;
    }

    @ModifyArg(method = "jump()V", at =@At(value = "INVOKE",target="net/minecraft/entity/LivingEntity.setVelocity (DDD)V"),index=1)
    private double jumpSkill(double ci){
            if(InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(),InputUtil.GLFW_KEY_LEFT_CONTROL)){
                NbtCompound nbt = ((IEntityDataSaver)(LivingEntity)(Object)this).getPersistentData();
                if(nbt.getBoolean("created")) if(nbt.getIntArray("skills")[2]>=0) {
                    double Roll = (this.random.nextBetween(1,21) + nbt.getIntArray("skills")[2] + nbt.getIntArray("stat_mod")[0])/4d;
                    Roll = (int)(Roll*10)/10d;
                    this.JumpSkillAmp = (float)Roll;
                    this.playSound(SoundEvents.BLOCK_WOOL_FALL, 1, 0.9F + this.random.nextFloat() * 0.2F);
                    return Roll*0.1d<0.2d?ci+0.2d:ci+((Roll)*0.1d);
                }
            }
            this.JumpSkillAmp = 0;
            return ci;
    }
}
