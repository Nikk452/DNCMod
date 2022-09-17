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
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putString("first_name", res_nbt.getString("first_name"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putString("last_name", res_nbt.getString("last_name"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putString("race", res_nbt.getString("race"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("total_level", res_nbt.getInt("total_level"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("experience", res_nbt.getInt("experience"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("max_experience", res_nbt.getInt("max_experience"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("max_mana", res_nbt.getInt("max_mana"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("mana", res_nbt.getInt("mana"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("max_ki", res_nbt.getInt("max_ki"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("ki", res_nbt.getInt("ki"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putIntArray("stats", res_nbt.getIntArray("stats"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putIntArray("classes", res_nbt.getIntArray("classes"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putIntArray("sub_classes", res_nbt.getIntArray("sub_classes"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putIntArray("skills", res_nbt.getIntArray("skills"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putIntArray("throws_proficiency", res_nbt.getIntArray("throws_proficiency"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putIntArray("stat_mod", res_nbt.getIntArray("stat_mod"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putIntArray("stat_throws", res_nbt.getIntArray("stat_throws"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("hit_dice", res_nbt.getInt("hit_dice"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putInt("proficiency_modifier", res_nbt.getInt("proficiency_modifier"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putString("gender", res_nbt.getString("gender"));
        ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart").putBoolean("created", res_nbt.getBoolean("created"));
    }
}
