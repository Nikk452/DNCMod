package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.nikk.dncmod.effect.ModEffects;
import net.nikk.dncmod.util.IEntityDataSaver;
import net.nikk.dncmod.util.WeightManager;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
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
