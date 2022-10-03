package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.ExperienceData;

public class KillEntityHandler implements ServerEntityCombatEvents.AfterKilledOtherEntity {
    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if(entity instanceof PlayerEntity && killedEntity instanceof SheepEntity){
            if(!world.isClient){
                ((PlayerEntity)entity).sendMessage(Text.literal(((PlayerEntity)entity).getName().getString()+" has killed a sheep"));
                ExperienceData.addExperience((ServerPlayerEntity)entity,450);
            }
        }
    }
}
