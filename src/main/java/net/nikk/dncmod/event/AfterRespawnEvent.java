package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.IEntityDataSaver;

public class AfterRespawnEvent implements ServerPlayerEvents.AfterRespawn{
    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if(alive){
            NbtCompound nbt = ((IEntityDataSaver)newPlayer).getPersistentData();
            PacketByteBuf buffer = PacketByteBufs.create().writeNbt(nbt);
            buffer.writeBoolean(false);
            ServerPlayNetworking.send(newPlayer, Networking.REFRESH_CLIENT_ID, buffer);
        }
    }
}
