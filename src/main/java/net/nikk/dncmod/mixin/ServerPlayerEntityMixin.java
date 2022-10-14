package net.nikk.dncmod.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nikk.dncmod.DNCMod;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }
    @Inject(method = "trySleep", at = @At(value = "FIELD", target = "net/minecraft/entity/player/PlayerEntity$SleepFailureReason.NOT_POSSIBLE_NOW : Lnet/minecraft/entity/player/PlayerEntity$SleepFailureReason;"), cancellable = true)
    private void patchSleep(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir){
        if(DNCMod.CONFIG.syncWithSystemTime) cir.setReturnValue(patchSleepMechanics(pos));
    }

    private Either<PlayerEntity.SleepFailureReason, Unit> patchSleepMechanics(BlockPos pos){
        if (!this.isCreative()) {
            Vec3d vec3d = Vec3d.ofBottomCenter(pos);
            List<HostileEntity> list = this.world.getEntitiesByClass(HostileEntity.class, new Box(vec3d.getX() - 8.0D, vec3d.getY() - 5.0D, vec3d.getZ() - 8.0D, vec3d.getX() + 8.0D, vec3d.getY() + 5.0D, vec3d.getZ() + 8.0D), (hostileEntity) -> hostileEntity.isAngryAt(this));
            if (!list.isEmpty()) {
                return Either.left(PlayerEntity.SleepFailureReason.NOT_SAFE);
            }
        }

        Either<PlayerEntity.SleepFailureReason, Unit> either = super.trySleep(pos).ifRight((unit) -> {
            this.incrementStat(Stats.SLEEP_IN_BED);
            Criteria.SLEPT_IN_BED.trigger((ServerPlayerEntity) (Object)this);
        });
        ((ServerWorld)this.world).updateSleepingPlayers();
        return either;
    }
}
