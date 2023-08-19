package net.nikk.dncmod.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.nikk.dncmod.effect.ModEffects;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
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
            if (nbt.getBoolean("created")) if(nbt.getIntArray("skills").length>0) if (nbt.getIntArray("skills")[4] >= 0) {
                int dice = this.random.nextBetween(1,20);
                if(livingEntity.isPlayer()) if(!this.getWorld().isClient()) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeIntArray(new int[]{dice,nbt.getIntArray("skills")[4], 20});
                    ServerPlayNetworking.send((ServerPlayerEntity)livingEntity,Networking.DICE_ID, buf);
                }else{
                    nbt.putString("label0","textures/gui/uifrag.png");
                    nbt.putString("label1","Acrobatics Lv."+nbt.getIntArray("skills")[4]);
                    nbt.putString("label2","Has Been Used!");
                    nbt.putInt("label",3);
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
            double he = getEffectModifier((LivingEntity)(Object)this,ModEffects.HEAVY);
            ci=he>=ci?0:ci-he;
            if(this.isSprinting() && ci!=0){
                NbtCompound nbt = ((IEntityDataSaver)(LivingEntity)(Object)this).getPersistentData();
                if(nbt.getBoolean("created")) if(nbt.getIntArray("skills").length>0) if(nbt.getIntArray("skills")[2]>=0) {
                    int dice = this.random.nextBetween(1,20);
                    double Roll = (dice + nbt.getIntArray("skills")[2] + nbt.getIntArray("stat_mod")[0])/4d;
                    Roll = (int)(Roll*10)/10d;
                    nbt.putIntArray("dice",new int[]{dice,nbt.getIntArray("skills")[2],20});
                    nbt.putString("label0","textures/gui/uifrag.png");
                    nbt.putString("label1","Using");
                    nbt.putString("label2","Jump Lv."+nbt.getIntArray("skills")[2]+"!");
                    nbt.putInt("label",3);
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
        if(nbt.getBoolean("created")) if(nbt.getIntArray("skills").length>0) if(nbt.getIntArray("skills")[3]>=0) {
            int Roll = (this.random.nextBetween(1,20) + nbt.getIntArray("skills")[3] + nbt.getIntArray("stat_mod")[0]);
            float swimMulti = Roll>=2f?Roll/200f:0.01f;
            if(this.isSprinting() && this.isSwimming()) return swimmingModifier+swimMulti;
        }
        return swimmingModifier;
    }
    @Shadow
    public int despawnCounter;
    @Shadow
    public float lastDamageTaken;
    @Shadow @Nullable
    public PlayerEntity attackingPlayer;
    @Shadow
    public int playerHitTimer;
    @Shadow @Nullable
    public DamageSource lastDamageSource;
    @Shadow
    public long lastDamageTime;
    @Shadow
    public void applyDamage(DamageSource source, float amount){}
    @Shadow
    public void takeShieldHit(LivingEntity attacker){}
    @Shadow
    public boolean tryUseTotem(DamageSource source) {return false;}
    @Shadow
    protected void playHurtSound(DamageSource source) {}
    @Shadow @Nullable
    protected SoundEvent getDeathSound() {return null;}
    @Shadow
    public float getSoundVolume() {return 0f;}
    @Inject(method="damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",at = @At("HEAD"), cancellable = true)
    private void injected(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean mbl = false;
        if(source.getAttacker()!=null){
            if(source.getAttacker().isLiving()){
                NbtCompound defender = ((IEntityDataSaver)(LivingEntity)(Object)(this)).getPersistentData();
                NbtCompound attacker = ((IEntityDataSaver)source.getAttacker()).getPersistentData();
                if(defender.getBoolean("created") && attacker.getBoolean("created")){
                    int dice = ((LivingEntity)(Object)this).getRandom().nextBetween(1,20);
                    if(dice==1) mbl = true;
                    else{
                        boolean doesCrit = dice == 20;
                        int AC = 10 + ((LivingEntity)(Object)this).getArmor() + defender.getIntArray("stat_mod")[1];
                        int enemy_stat_mod = (float) attacker.getIntArray("stat_mod")[0]>attacker.getIntArray("stat_mod")[1]?attacker.getIntArray("stat_mod")[0]:attacker.getIntArray("stat_mod")[1];
                        int hit = dice + enemy_stat_mod;
                        if(AC>hit && !doesCrit) mbl = true;
                        else{
                            if(AC*2<hit) doesCrit = true;
                            int past_amount = (int)amount;
                            amount = ((LivingEntity)(Object)this).getRandom().nextBetween(1,(int)amount);
                            if(source.getAttacker().isPlayer()){
                                ItemStack itemstack = ((PlayerEntity)source.getAttacker()).getStackInHand(((PlayerEntity) source.getAttacker()).getActiveHand());
                                if(itemstack.isOf(Items.AIR)){
                                    past_amount = 4;
                                    amount = ((LivingEntity)(Object)this).getRandom().nextBetween(1,4);
                                }
                                PacketByteBuf buf = PacketByteBufs.create();
                                buf.writeIntArray(new int[]{(int)amount,enemy_stat_mod,chooseDice(past_amount)});
                                ServerPlayNetworking.send((ServerPlayerEntity)source.getAttacker(), Networking.DICE_ID, buf);
                            }
                            amount = doesCrit?amount*2:amount;
                        }
                    }
                }
                else if(((LivingEntity)(Object)(this)).isPlayer()) {
                    mbl = true;
                }
            }
        }
        if (this.isInvulnerableTo(source)) {
            cir.setReturnValue(false);
        } else if (((LivingEntity)(Object)this).world.isClient) {
            cir.setReturnValue(false);
        } else if (((LivingEntity)(Object)this).isDead()) {
            cir.setReturnValue(false);
        } else if (source.isFire() && ((LivingEntity)(Object)this).hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
            cir.setReturnValue(false);
        } else if(mbl){
            cir.setReturnValue(false);
        }
        else{
            if (((LivingEntity)(Object)this).isSleeping() && !((LivingEntity)(Object)this).world.isClient) {
                ((LivingEntity)(Object)this).wakeUp();
            }

            this.despawnCounter = 0;
            float f = amount;
            boolean bl = false;
            float g = 0.0F;
            if (amount > 0.0F && ((LivingEntity)(Object)this).blockedByShield(source)) {
                ((LivingEntity)(Object)this).damageShield(amount);
                g = amount;
                amount = 0.0F;
                if (!source.isProjectile()) {
                    Entity entity = source.getSource();
                    if (entity instanceof LivingEntity) {
                        this.takeShieldHit((LivingEntity)entity);
                    }
                }

                bl = true;
            }

            ((LivingEntity)(Object)this).limbDistance = 1.5F;
            boolean bl2 = true;
            if ((float)this.timeUntilRegen > 10.0F) {
                if (amount <= this.lastDamageTaken) {
                    cir.setReturnValue(false);
                }

                this.applyDamage(source, amount - this.lastDamageTaken);
                this.lastDamageTaken = amount;
                bl2 = false;
            } else {
                this.lastDamageTaken = amount;
                ((LivingEntity)(Object)this).timeUntilRegen = 20;
                this.applyDamage(source, amount);
                ((LivingEntity)(Object)this).maxHurtTime = 10;
                ((LivingEntity)(Object)this).hurtTime = ((LivingEntity)(Object)this).maxHurtTime;
            }

            if (source.isFallingBlock() && !((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                ((LivingEntity)(Object)this).damageHelmet(source, amount);
                amount *= 0.75F;
            }

            ((LivingEntity)(Object)this).knockbackVelocity = 0.0F;
            Entity entity2 = source.getAttacker();
            if (entity2 != null) {
                if (entity2 instanceof LivingEntity && !source.isNeutral()) {
                    ((LivingEntity)(Object)this).setAttacker((LivingEntity)entity2);
                }

                if (entity2 instanceof PlayerEntity) {
                    this.playerHitTimer = 100;
                    this.attackingPlayer = (PlayerEntity)entity2;
                } else if (entity2 instanceof WolfEntity) {
                    WolfEntity wolfEntity = (WolfEntity)entity2;
                    if (wolfEntity.isTamed()) {
                        this.playerHitTimer = 100;
                        LivingEntity livingEntity = wolfEntity.getOwner();
                        if (livingEntity != null && livingEntity.getType() == EntityType.PLAYER) {
                            this.attackingPlayer = (PlayerEntity)livingEntity;
                        } else {
                            this.attackingPlayer = null;
                        }
                    }
                }
            }

            if (bl2) {
                if (bl) {
                    ((LivingEntity)(Object)this).world.sendEntityStatus(((LivingEntity)(Object)this), (byte)29);
                } else if (source instanceof EntityDamageSource && ((EntityDamageSource)source).isThorns()) {
                    ((LivingEntity)(Object)this).world.sendEntityStatus(((LivingEntity)(Object)this), (byte)33);
                } else {
                    byte b;
                    if (source == DamageSource.DROWN) {
                        b = 36;
                    } else if (source.isFire()) {
                        b = 37;
                    } else if (source == DamageSource.SWEET_BERRY_BUSH) {
                        b = 44;
                    } else if (source == DamageSource.FREEZE) {
                        b = 57;
                    } else {
                        b = 2;
                    }

                    ((LivingEntity)(Object)this).world.sendEntityStatus(((LivingEntity)(Object)this), b);
                }

                if (source != DamageSource.DROWN && (!bl || amount > 0.0F)) {
                    ((EntityInvoker)(LivingEntity)(Object)this).invokeScheduleVelocityUpdate();
                }

                if (entity2 != null) {
                    double d = entity2.getX() - this.getX();

                    double e;
                    for(e = entity2.getZ() - ((LivingEntity)(Object)this).getZ(); d * d + e * e < 1.0E-4; e = (Math.random() - Math.random()) * 0.01) {
                        d = (Math.random() - Math.random()) * 0.01;
                    }

                    ((LivingEntity)(Object)this).knockbackVelocity = (float)(MathHelper.atan2(e, d) * 57.2957763671875 - (double)((LivingEntity)(Object)this).getYaw());
                    ((LivingEntity)(Object)this).takeKnockback(0.4000000059604645, d, e);
                } else {
                    ((LivingEntity)(Object)this).knockbackVelocity = (float)((int)(Math.random() * 2.0) * 180);
                }
            }

            if (((LivingEntity)(Object)this).isDead()) {
                if (!this.tryUseTotem(source)) {
                    SoundEvent soundEvent = this.getDeathSound();
                    if (bl2 && soundEvent != null) {
                        ((LivingEntity)(Object)this).playSound(soundEvent, this.getSoundVolume(), ((LivingEntity)(Object)this).getSoundPitch());
                    }

                    ((LivingEntity)(Object)this).onDeath(source);
                }
            } else if (bl2) {
                this.playHurtSound(source);
            }

            boolean bl3 = !bl || amount > 0.0F;
            if (bl3) {
                this.lastDamageSource = source;
                this.lastDamageTime = ((LivingEntity)(Object)this).world.getTime();
            }

            if (((LivingEntity)(Object)this) instanceof ServerPlayerEntity) {
                Criteria.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity)((LivingEntity)(Object)this), source, f, amount, bl);
                if (g > 0.0F && g < 3.4028235E37F) {
                    ((ServerPlayerEntity)((LivingEntity)(Object)this)).increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(g * 10.0F));
                }
            }

            if (entity2 instanceof ServerPlayerEntity) {
                Criteria.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity)entity2, ((LivingEntity)(Object)this), source, f, amount, bl);
            }

            cir.setReturnValue(bl3);
        }
        cir.cancel();
    }
    @Redirect(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At(value = "INVOKE", target = "net/minecraft/block/Block.getSlipperiness ()F"))
    private float injected(Block block) {
        float slip = block.getSlipperiness();
        double he = (getEffectModifier(((LivingEntity)(Object)this),ModEffects.HEAVY));
        if(he>0 && he<5){
            slip += he;
        }
        return slip;
    }
    public double getEffectModifier(LivingEntity livingEntity, StatusEffect statusEffect) {
        return livingEntity.hasStatusEffect(statusEffect) ? (double)(0.1F * (float)(livingEntity.getStatusEffect(statusEffect).getAmplifier() + 1)) : 0.0;
    }
    private static int chooseDice(int x){
        if(x>12) return 20;
        else if (x % 2 == 0 && x != 0) return x;
        else if (x==0) return 2;
        else return x+1;
    }
}
