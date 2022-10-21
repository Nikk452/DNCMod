package net.nikk.dncmod.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private int restTimer = 0;
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Shadow public abstract boolean canResetTimeBySleeping();

    @Shadow public abstract void wakeUp(boolean bl, boolean bl2);
    @Inject(method = "createPlayerAttributes", at = @At("RETURN"), cancellable = true)
    private static void setMaxHealthAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
        DefaultAttributeContainer.Builder builder = cir.getReturnValue();
        int custom_health = 4;
        builder.add(EntityAttributes.GENERIC_MAX_HEALTH,custom_health);
    }

    @Inject(method = "getName", at = @At("RETURN"))
    private Text getNewName(CallbackInfoReturnable<Text> cir){
        if(((IEntityDataSaver)(PlayerEntity) (Object) this).getPersistentData().getBoolean("created"))
            return Text.literal(((IEntityDataSaver)(PlayerEntity) (Object) this).getPersistentData().getString("first_name")+" "+((IEntityDataSaver)(PlayerEntity) (Object) this).getPersistentData().getString("last_name"));
        return cir.getReturnValue();
    }

    @Redirect(method = "tick", at =@At(value = "INVOKE", target = "net/minecraft/entity/player/PlayerEntity.wakeUp (ZZ)V"))
    private void disableDayCheck(PlayerEntity playerEntity, boolean bl, boolean updateSleepingPlayers){
        if(!DNCMod.CONFIG.syncWithSystemTime){
            this.wakeUp(false, true);
        }
    }

    @Inject(method = "trySleep", at =@At("HEAD"))
    private void onPlayerStartedSleeping(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir){
        this.restTimer = 0;
    }

    @Inject(method = "tick", at =@At("TAIL"))
    private void onPlayerTick(CallbackInfo ci){
        if(DNCMod.CONFIG.syncWithSystemTime && this.canResetTimeBySleeping()){
            ++restTimer;
            if(restTimer > 60){
                this.heal(1.0F);
                restTimer = 0;
            }
        }
    }
    int successTimes = 0;
    @Inject(method = "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F", at = @At("RETURN"), cancellable = true)
    private void blockBreak(BlockState state, CallbackInfoReturnable<Float> cir) {
        Random random = new Random();
        NbtCompound nbt = ((IEntityDataSaver)(PlayerEntity) (Object) this).getPersistentData();
        float H = state.getBlock().getHardness();
        int DC = (int)(5*((Math.pow(H<1?1:H,2))/2f));
        int Jump_mod = nbt.getIntArray("skills")[1]+nbt.getIntArray("stat_mod")[0];
        int Roll = random.nextInt(0,20)+1+Jump_mod;
        ((PlayerEntity) (Object) this).sendMessage(Text.literal("Dc: "+DC+" Roll: "+Roll));
        if(Roll>DC) cir.setReturnValue(cir.getReturnValue() * random.nextFloat(0f,(Jump_mod>0?(float)Jump_mod:0.1f)/(Roll>DC*2?Roll>DC*4?1f:2f:4f)));
        else cir.setReturnValue(8.1E-4F*cir.getReturnValue());
    }
}
