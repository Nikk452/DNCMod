package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.nikk.dncmod.util.IEntityDataSaver;

public class CreatedCharacterS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound res_nbt = buf.readNbt();
        NbtCompound nbt = ((IEntityDataSaver) client.player).getPersistentData();
        nbt.putString("first_name", res_nbt.getString("first_name"));
        nbt.putString("last_name", res_nbt.getString("last_name"));
        nbt.putString("race", res_nbt.getString("race"));
        nbt.putInt("total_level", res_nbt.getInt("total_level"));
        nbt.putInt("experience", res_nbt.getInt("experience"));
        nbt.putInt("max_experience", res_nbt.getInt("max_experience"));
        nbt.putInt("max_mana", res_nbt.getInt("max_mana"));
        nbt.putInt("mana", res_nbt.getInt("mana"));
        nbt.putInt("max_ki", res_nbt.getInt("max_ki"));
        nbt.putInt("ki", res_nbt.getInt("ki"));
        nbt.putIntArray("stats", res_nbt.getIntArray("stats"));
        nbt.putIntArray("classes", res_nbt.getIntArray("classes"));
        nbt.putIntArray("sub_classes", res_nbt.getIntArray("sub_classes"));
        nbt.putIntArray("skills", res_nbt.getIntArray("skills"));
        nbt.putIntArray("throws_proficiency", res_nbt.getIntArray("throws_proficiency"));
        nbt.putIntArray("stat_mod", res_nbt.getIntArray("stat_mod"));
        nbt.putIntArray("stat_throws", res_nbt.getIntArray("stat_throws"));
        nbt.putInt("hit_dice", res_nbt.getInt("hit_dice"));
        nbt.putInt("proficiency_modifier", res_nbt.getInt("proficiency_modifier"));
        nbt.putString("gender", res_nbt.getString("gender"));
        nbt.putBoolean("created", res_nbt.getBoolean("created"));
        nbt.putIntArray("hit_dices", res_nbt.getIntArray("hit_dices"));
        nbt.putInt("con_health_boost",res_nbt.getInt("con_health_boost"));
        client.execute(() -> {
            NbtCompound nbtCompound = ((IEntityDataSaver) client.player).getPersistentData();
            nbtCompound.putString("first_name", res_nbt.getString("first_name"));
            nbtCompound.putString("last_name", res_nbt.getString("last_name"));
            nbtCompound.putString("race", res_nbt.getString("race"));
            nbtCompound.putInt("total_level", res_nbt.getInt("total_level"));
            nbtCompound.putInt("experience", res_nbt.getInt("experience"));
            nbtCompound.putInt("max_experience", res_nbt.getInt("max_experience"));
            nbtCompound.putInt("max_mana", res_nbt.getInt("max_mana"));
            nbtCompound.putInt("mana", res_nbt.getInt("mana"));
            nbtCompound.putInt("max_ki", res_nbt.getInt("max_ki"));
            nbtCompound.putInt("ki", res_nbt.getInt("ki"));
            nbtCompound.putIntArray("stats", res_nbt.getIntArray("stats"));
            nbtCompound.putIntArray("classes", res_nbt.getIntArray("classes"));
            nbtCompound.putIntArray("sub_classes", res_nbt.getIntArray("sub_classes"));
            nbtCompound.putIntArray("skills", res_nbt.getIntArray("skills"));
            nbtCompound.putIntArray("throws_proficiency", res_nbt.getIntArray("throws_proficiency"));
            nbtCompound.putIntArray("stat_mod", res_nbt.getIntArray("stat_mod"));
            nbtCompound.putIntArray("stat_throws", res_nbt.getIntArray("stat_throws"));
            nbtCompound.putInt("hit_dice", res_nbt.getInt("hit_dice"));
            nbtCompound.putInt("proficiency_modifier", res_nbt.getInt("proficiency_modifier"));
            nbtCompound.putString("gender", res_nbt.getString("gender"));
            nbtCompound.putBoolean("created", res_nbt.getBoolean("created"));
            nbtCompound.putIntArray("hit_dices", res_nbt.getIntArray("hit_dices"));
            nbtCompound.putInt("con_health_boost",res_nbt.getInt("con_health_boost"));
        });
    }
}
