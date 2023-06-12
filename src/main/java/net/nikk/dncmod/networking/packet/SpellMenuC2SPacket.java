package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.networking.Networking;

public class SpellMenuC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeBoolean(player.hasPermissionLevel(4));
        ServerPlayNetworking.send(player, Networking.SPELL_MENU_S2C, buffer);
    }
}
