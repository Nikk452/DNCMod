package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.nikk.dncmod.util.ExperienceData;

public class KillEntityHandler implements ServerEntityCombatEvents.AfterKilledOtherEntity {
    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if(entity instanceof PlayerEntity && killedEntity instanceof SkeletonEntity){
            if(!world.isClient){
                //((PlayerEntity)entity).sendMessage(Text.literal(((PlayerEntity)entity).getName().getString()+" has killed a sheep"));
                ExperienceData.addExperience((ServerPlayerEntity)entity,150);
            }
        }
    }
}
