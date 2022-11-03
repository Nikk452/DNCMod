package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.nikk.dncmod.screen.CharCreationScreen1;
import net.nikk.dncmod.screen.StatScreen1;
import net.nikk.dncmod.util.IEntityDataSaver;

public class LevelUpS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound nbt_res = buf.readNbt();
        NbtCompound nbt = ((IEntityDataSaver) client.player).getPersistentData();
        boolean leveled = nbt_res.getBoolean("leveled");
        client.execute(() -> {
            if(leveled){
                nbt.putIntArray("classes",nbt_res.getIntArray("classes"));
                nbt.putIntArray("hit_dices",nbt_res.getIntArray("hit_dices"));
                nbt.putInt("total_level", nbt_res.getInt("total_level"));
                nbt.putInt("max_experience", nbt_res.getInt("max_experience"));
                nbt.putInt("proficiency_modifier", nbt_res.getInt("proficiency_modifier"));
                client.player.sendMessage(Text.literal("You have leveled up!"));
            }
            nbt.putInt("experience", nbt_res.getInt("experience"));
        });
    }
}
