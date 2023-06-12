package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.nikk.dncmod.util.IEntityDataSaver;

public class NewNameS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        Boolean bl = buf.readBoolean();
        NbtCompound nbt = ((IEntityDataSaver) client.player).getPersistentData();
        client.execute(() -> nbt.putString("allowed_name", Boolean.toString(bl)));
    }
}
