package net.nikk.dncmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.networking.Networking;
@SuppressWarnings("unused")
public class StatData {
    public static void addStat(IEntityDataSaver player, int type, String key, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int[] stat = nbt.getIntArray(key).length == 0? new int[]{0, 0, 0, 0, 0, 0} :nbt.getIntArray(key);
        if(stat[type] + amount >= 100) {
            stat[type] = 100;
        } else {
            stat[type] += amount;
        }
        nbt.putIntArray(key, stat);
        syncStats(stat, (ServerPlayerEntity) player);
    }

    public static int[] removeStat(IEntityDataSaver player, int type, String key, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int[] stat = nbt.getIntArray(key).length == 0? new int[]{0, 0, 0, 0, 0, 0} :nbt.getIntArray(key);
        if(stat[type] - amount < 0) {
            stat[type] = 0;
        } else {
            stat[type] -= amount;
        }
        nbt.putIntArray(key, stat);
        syncStats(stat, (ServerPlayerEntity) player);
        return stat;
    }
    public static void syncStats(int[] stats, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeIntArray(stats);
        ServerPlayNetworking.send(player, Networking.SYNC_STATS, buffer);
    }
}
