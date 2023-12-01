package net.nikk.dncmod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.nikk.dncmod.entity.variant.GoblinVariant;
import net.nikk.dncmod.item.ModItems;
import org.jetbrains.annotations.Nullable;
public class GoblinEntity extends HostileEntity implements RangedAttackMob {
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT;
    private final BowAttackGoal<GoblinEntity> bowAttackGoal = new BowAttackGoal<>(this, 1.0, 20, 15.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
        public void stop() {
            super.stop();
            GoblinEntity.this.setAttacking(false);
        }

        public void start() {
            super.start();
            GoblinEntity.this.setAttacking(true);
        }
    };
    public GoblinEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.75f, 1));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    // example of damage source immunity
    //@Override
    //public boolean isInvulnerableTo(DamageSource damageSource) {
    //    return damageSource.isProjectile() || super.isInvulnerableTo(damageSource);
    //}
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP, 0.15f, 1.0f);
    }
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
                                 SpawnReason spawnReason, @Nullable EntityData entityData,
                                 @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.initEquipment(random,difficulty);
        GoblinVariant variant;
        if (entityData instanceof GoblinEntity.GoblinData) {
            variant = ((GoblinData)entityData).variant;
        } else {
            variant = Util.getRandom(GoblinVariant.values(), this.random);
            entityData = new GoblinEntity.GoblinData(variant);
        }
        this.setVariant(variant);
        return entityData;
    }

    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(random.nextBetween(0,4)==1?ModItems.SHORT_BOW:ModItems.DAGGER));
        this.equipStack(EquipmentSlot.HEAD,new ItemStack(Items.IRON_HELMET));
        this.equipStack(EquipmentSlot.CHEST,new ItemStack(Items.IRON_CHESTPLATE));
        this.equipStack(EquipmentSlot.LEGS,new ItemStack(Items.IRON_LEGGINGS));
        this.equipStack(EquipmentSlot.FEET,new ItemStack(Items.IRON_BOOTS));
    }
    public void updateAttackType() {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            this.goalSelector.remove(this.meleeAttackGoal);
            this.goalSelector.remove(this.bowAttackGoal);
            ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, ModItems.SHORT_BOW));
            if (itemStack.isOf(Items.BOW)) {
                int i = 20;
                if (this.getWorld().getDifficulty() != Difficulty.HARD) {
                    i = 40;
                }

                this.bowAttackGoal.setAttackInterval(i);
                this.goalSelector.add(4, this.bowAttackGoal);
            } else {
                this.goalSelector.add(4, this.meleeAttackGoal);
            }

        }
    }

    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getProjectileType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, ModItems.SHORT_BOW)));
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack, pullProgress);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.setVelocity(d, e + g * 0.20000000298023224, f, 1.6F, (float)(14 - this.getWorld().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.getWorld().spawnEntity(persistentProjectileEntity);
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier);
    }

    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return weapon == ModItems.SHORT_BOW;
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
        this.updateAttackType();
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }
    public GoblinVariant getVariant() {
        return GoblinVariant.byId(this.getTypeVariant() & 255);
    }
    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }
    private void setVariant(GoblinVariant variant) {
        this.setVariant(variant.getId() & 255);
    }
    private void setVariant(int variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant);
    }
    static {
         DATA_ID_TYPE_VARIANT = DataTracker.registerData(GoblinEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
    public record GoblinData(GoblinVariant variant) implements EntityData {
    }
}
