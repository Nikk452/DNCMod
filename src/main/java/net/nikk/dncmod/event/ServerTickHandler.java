package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.nikk.dncmod.effect.ModEffects;
import net.nikk.dncmod.util.ExperienceData;
import net.nikk.dncmod.util.WeightManager;

public class ServerTickHandler implements ServerTickEvents.StartTick{
    @Override
    public void onStartTick(MinecraftServer server) {
        //if(server.getTicks()%72==0){
        if(server.getTicks()%72000==0){
            for (LivingEntity livingEntity:server.getOverworld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), EntityPredicates.VALID_LIVING_ENTITY)) {
                ExperienceData.addExperience((ServerWorld) livingEntity.getWorld(),livingEntity,1);
            }
        }
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if(server.getTicks()%7200==0){
                ExperienceData.addExperience(player.getServerWorld(),player, 1);
            }
            if(server.getTicks()%20==0) {
                float weightP = (float)WeightManager.getPlayerInventoryWeight(player)/WeightManager.getMaxInventoryWeight(player);
                if(weightP>0.5){
                    if(weightP>1.5f){
                        player.addStatusEffect(new StatusEffectInstance(ModEffects.HEAVY, 21, 60,false,false,false));
                    } else if (weightP>1) {
                        player.addStatusEffect(new StatusEffectInstance(ModEffects.HEAVY, 21, 50,false,false,false));
                    } else if (weightP>0.8) {
                        player.addStatusEffect(new StatusEffectInstance(ModEffects.HEAVY, 21, 2,false,false,false));
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(ModEffects.HEAVY, 21, 0,false,false,false));
                    }
                }
            }
        }
    }
}
