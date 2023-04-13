package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.nikk.dncmod.item.ModItems;
import net.nikk.dncmod.networking.Networking;

public class SpellCreateC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if(player.hasPermissionLevel(4)){
            NbtCompound nbtCompound = buf.readNbt();
            ItemStack Scroll = ModItems.SCROLL.getDefaultStack().setCustomName(Text.literal("Magic Scroll"));
            Scroll.getOrCreateNbt().put("spell",nbtCompound);
            player.giveItemStack(Scroll);
        }else {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeBoolean(false);
            ServerPlayNetworking.send(player, Networking.SPELL_MENU_S2C, buffer);
        }
    }
}
