package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.screen.CharCreationScreen1;
import net.nikk.dncmod.screen.StatScreen1;
import net.nikk.dncmod.util.IEntityDataSaver;

public class StatsS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound res_nbt = buf.readNbt();
        boolean b1 = res_nbt.getBoolean("created");
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
            nbtCompound.putBoolean("created", b1);
            if(b1) client.setScreen(new StatScreen1(true));
            else client.setScreen(new CharCreationScreen1("","", "fighter","",nbtCompound.getIntArray("stats").length==6?nbtCompound.getIntArray("stats"): new int[]{0, 0, 0, 0, 0, 0},0,new int[]{0,1,2,3,4,5},true));
        });
    }
}
