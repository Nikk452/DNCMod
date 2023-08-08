package net.nikk.dncmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.nikk.dncmod.util.AttributeData;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    /*@ModifyArg(method = "spawnEntity", at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld.addEntity (Lnet/minecraft/entity/Entity;)Z"), index = 0)
    private Entity injected2(Entity entity4) {
        NbtCompound nbt = ((IEntityDataSaver)entity).getPersistentData();
        return entity;
    }*/
    @ModifyVariable(method = "spawnEntity", at = @At(value = "HEAD"), ordinal = 0)
    private Entity injected(Entity entity) {
        if(entity instanceof LivingEntity && !(entity instanceof PlayerEntity) && !(entity instanceof ArmorStandEntity)){
            NbtCompound nbt = ((IEntityDataSaver)entity).getPersistentData();
            Random random = Random.create();
            nbt.putInt("total_level", 0);
            nbt.putInt("experience", 0);
            nbt.putInt("max_experience", 500);
            int[] new_stats = new int[]{0,0,0,0,0,0};
            for(int i = 0;i<6;i++) for(int z =0;z<(entity instanceof HostileEntity?3:1);z++) new_stats[i] += random.nextBetween(1,entity instanceof HostileEntity?6:4);
            nbt.putIntArray("stats", new_stats);
            nbt.putInt("hit_dice", 0);
            nbt.putBoolean("created", true);
            nbt.putIntArray("hit_dices", new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
            int maxhp = (int)((LivingEntity) entity).getMaxHealth();
            int con_health = new_stats[2]>maxhp?new_stats[2]-maxhp:new_stats[2];
            nbt.putInt("con_health_boost",con_health);
            for(int i=0;i<6;i++) new_stats[i]=((Math.max(new_stats[i], 10))-10)/2;
            nbt.putIntArray("stat_mod", new_stats);
            AttributeData.addHealth((LivingEntity) entity,con_health,"con_health_boost","80b3a28a-42cd-4926-8327-91e75ab0191f");
        }
        return entity;
    }
}
