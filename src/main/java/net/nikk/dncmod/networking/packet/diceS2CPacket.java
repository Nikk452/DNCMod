package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.nikk.dncmod.util.IEntityDataSaver;

public class diceS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        int dice = buf.readInt();
        client.execute(() -> ((IEntityDataSaver) client.player).getPersistentData().putInt("d20", dice));
    }
}
