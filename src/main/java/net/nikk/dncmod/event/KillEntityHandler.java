package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.util.AttributeData;
import net.nikk.dncmod.util.ExperienceData;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class KillEntityHandler implements ServerEntityCombatEvents.AfterKilledOtherEntity {
    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if(!(killedEntity instanceof ArmorStandEntity)){
            int amount = ((IEntityDataSaver)killedEntity).getPersistentData().getInt("current_experience");
            if(killedEntity instanceof HostileEntity) amount = killedEntity.defaultMaxHealth;
            else if(killedEntity instanceof PlayerEntity){
                int level = ((IEntityDataSaver)killedEntity).getPersistentData().getInt("total_level");
                if(level>0) amount = 500 << (level - 1);
            }
            amount=(amount/2) * DNCMod.CONFIG.xp_per_lvl_multi;

            if(amount>0){
                ChunkPos chunkPos = killedEntity.getChunkPos();
                Box box = new Box(chunkPos.getStartX(), world.getBottomY(), chunkPos.getStartZ(), chunkPos.getEndX(), world.getHeight(), chunkPos.getEndZ()).expand(64,0,64);
                List<Entity> list = world.getOtherEntities(killedEntity, box, EntityPredicates.VALID_LIVING_ENTITY).stream()
                        .filter(entity2 -> (!(entity2 instanceof ArmorStandEntity) && (entity2 instanceof LivingEntity))).toList();
                if(amount/list.size()>0){
                    Iterator<Entity> var1 = list.iterator();

                    Entity entity1;
                    while(var1.hasNext()){
                        entity1 = var1.next();
                        ExperienceData.addExperience(world, (LivingEntity)entity1,amount/list.size());
                        if(!(entity1 instanceof PlayerEntity)){
                            NbtCompound nbt = ((IEntityDataSaver)entity1).getPersistentData();
                            if(nbt.getInt("experience")>=nbt.getInt("max_experience")){
                                int[] dices = nbt.getIntArray("hit_dices");
                                int total_level = nbt.getInt("total_level");
                                total_level++;
                                dices[total_level-1]=ExperienceData.RollHPDice((entity1 instanceof HostileEntity)?0:1,total_level);
                                nbt.putIntArray("hit_dices",dices);
                                nbt.putInt("hit_dice",dices[total_level-1]);
                                nbt.putInt("total_level", total_level);
                                nbt.putInt("experience",nbt.getInt("experience")-nbt.getInt("max_experience"));
                                nbt.putInt("max_experience",nbt.getInt("max_experience")*2);
                                AttributeData.setHealth((LivingEntity) entity1,total_level>1?(total_level-1)*nbt.getInt("con_health_boost"):nbt.getInt("con_health_boost"),total_level*nbt.getInt("con_health_boost"),"con_health_boost","80b3a28a-42cd-4926-8327-91e75ab0191f");
                                AttributeData.setHealth((LivingEntity) entity1,total_level>=2?dices[total_level-2]:1,dices[total_level-1],"dice_health","f00905f3-63f1-4dd4-3977-dea03d419d5d");
                            }
                        }
                    }
                }
            }
        }
    }
}
