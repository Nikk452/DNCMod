package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.nikk.dncmod.util.ExperienceData;
import net.nikk.dncmod.util.IEntityDataSaver;

public class KillEntityHandler implements ServerEntityCombatEvents.AfterKilledOtherEntity {
    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if(entity instanceof PlayerEntity && killedEntity instanceof HostileEntity){
            if(!world.isClient){
                //((PlayerEntity)entity).sendMessage(Text.literal(((PlayerEntity)entity).getName().getString()+" has killed a sheep"));
                ExperienceData.addExperience((ServerPlayerEntity)entity,entity.world.random.nextBetween(5,10));
            }
        }else if(entity instanceof PlayerEntity && killedEntity instanceof PlayerEntity){
            int level = ((IEntityDataSaver)killedEntity).getPersistentData().getInt("total_level");
            int amount;
            if(level>0){
                amount = 500;
                for(int i = 1;i<level;i++) amount *=2;
            }else amount = ((IEntityDataSaver)killedEntity).getPersistentData().getInt("current_experience");
            ExperienceData.addExperience((ServerPlayerEntity)entity,amount/2);
        }
    }
}
