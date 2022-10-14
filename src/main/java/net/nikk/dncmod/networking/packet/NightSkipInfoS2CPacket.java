package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.nikk.dncmod.util.IEntityDataSaver;
import net.nikk.dncmod.util.ITimeOperations;

public class NightSkipInfoS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if(buf.readableBytes() > 0) {
            ClientWorld world = MinecraftClient.getInstance().world;
            if(world != null){
                ITimeOperations ops = (ITimeOperations) world;
                ops.setSkipState(buf.readBoolean());
                ops.setSpeed(buf.readInt());
            }
        }
    }
}
