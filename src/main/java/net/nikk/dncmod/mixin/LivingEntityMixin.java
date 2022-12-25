package net.nikk.dncmod.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("unused")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    float JumpSkillAmp = 0;
    @Shadow
    protected void damageArmor(DamageSource source, float amount) {}
    @Shadow
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }
    @Redirect(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", at = @At(value = "INVOKE", target = "net/minecraft/entity/LivingEntity.computeFallDamage (FF)I"))
    private int acrobaticsSkill(LivingEntity livingEntity,float fallDistance, float damageMultiplier) {
        int normal_damage = this.computeFallDamage(fallDistance, damageMultiplier);
        if(fallDistance>2) {
            NbtCompound nbt = ((IEntityDataSaver) (LivingEntity) (Object) this).getPersistentData();
            if (nbt.getBoolean("created")) if (nbt.getIntArray("skills")[4] >= 0) {
                int dice = this.random.nextBetween(1,20);
                if(!this.getWorld().isClient()) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeIntArray(new int[]{dice,nbt.getIntArray("skills")[4], 20});
                    ServerPlayNetworking.send((ServerPlayerEntity)livingEntity,Networking.DICE_ID, buf);
                }
                double Roll = (dice + nbt.getIntArray("skills")[4] + nbt.getIntArray("stat_mod")[1]) / 4d;
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
            if(this.isSprinting()){
                NbtCompound nbt = ((IEntityDataSaver)(LivingEntity)(Object)this).getPersistentData();
                if(nbt.getBoolean("created")) if(nbt.getIntArray("skills")[2]>=0) {
                    int dice = this.random.nextBetween(1,20);
                    double Roll = (dice + nbt.getIntArray("skills")[2] + nbt.getIntArray("stat_mod")[0])/4d;
                    Roll = (int)(Roll*10)/10d;
                    nbt.putIntArray("dice",new int[]{dice,nbt.getIntArray("skills")[2],20});
                    this.JumpSkillAmp = (float)Roll;
                    this.playSound(SoundEvents.BLOCK_WOOL_FALL, 1, 0.9F + this.random.nextFloat() * 0.2F);
                    this.getWorld().addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian()* 0.02, this.random.nextGaussian()* 0.02, this.random.nextGaussian()* 0.02);
                    return Roll*0.1d<0.2d?ci+0.2d:ci+((Roll)*0.1d);
                }
            }
            this.JumpSkillAmp = 0;
            return ci;
    }
    @ModifyVariable(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private float swimSkill(float swimmingModifier) {
        NbtCompound nbt = ((IEntityDataSaver)(LivingEntity)(Object)this).getPersistentData();
        if(nbt.getBoolean("created")) if(nbt.getIntArray("skills")[3]>=0) {
            int Roll = (this.random.nextBetween(1,20) + nbt.getIntArray("skills")[3] + nbt.getIntArray("stat_mod")[0]);
            float swimMulti = Roll>=2f?Roll/200f:0.01f;
            if(this.isSprinting() && this.isSwimming()) return swimmingModifier+swimMulti;
        }
        return swimmingModifier;
    }
    //@Redirect(method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", at = @At(value = "INVOKE", target = "net/minecraft/entity/LivingEntity.applyArmorToDamage (Lnet/minecraft/entity/damage/DamageSource;F)F"))
    //private float injected(LivingEntity entity,DamageSource source, float amount) {
    //    return 0;
    //}

    @Inject(method = "applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At("HEAD"), cancellable = true)
    private void ArmorSystem(DamageSource source, float amount,CallbackInfoReturnable<Float> cir) {
        if (!source.bypassesArmor()) {
            NbtCompound nbt = ((IEntityDataSaver) (LivingEntity) (Object) this).getPersistentData();
            int AC = 10 + ((LivingEntity)(Object)this).getArmor();
            int damage = (int)amount;
            int critical = 2;
            int attack = ((LivingEntity)(Object)this).getRandom().nextBetween(1,20);
            if(nbt.getBoolean("created")){
                AC += nbt.getIntArray("stat_mod")[1];
                if(source.getSource().isPlayer()){
                    critical = (int) ((amount-damage)*100);
                    float num = ((amount-damage)*100);
                    attack = Integer.parseInt(String.valueOf(num).substring(String.valueOf(num).indexOf('.')+1));
                }
            }
            amount = attack>(AC*2)?damage*critical:(attack>AC?damage:0);
            this.damageArmor(source, amount);
        }
        cir.setReturnValue(amount);
    }

}
