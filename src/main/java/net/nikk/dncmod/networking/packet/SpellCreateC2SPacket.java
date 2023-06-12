package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.item.ModItems;
import net.nikk.dncmod.networking.Networking;

import java.util.List;

public class SpellCreateC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if(player.hasPermissionLevel(4)){
            NbtCompound nbtCompound = buf.readNbt();
            server.execute(()-> {
                ItemStack Scroll = new ItemStack(ModItems.SCROLL);
                NbtCompound display = new NbtCompound();
                NbtList list = new NbtList();
                List<String> strings = List.of("Type: "+nbtCompound.getString("type"),"Level: "+nbtCompound.getInt("level"));
                list.add(NbtString.of("\""+strings.get(0)+"\""));
                list.add(NbtString.of("\""+strings.get(1)+"\""));
                display.put("Lore",list);
                Scroll.getOrCreateNbt().put("display",display);
                Scroll.getOrCreateNbt().put("spell",nbtCompound);
                player.getInventory().offerOrDrop(Scroll);
            });
        }else {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeBoolean(false);
            ServerPlayNetworking.send(player, Networking.SPELL_MENU_S2C, buffer);
        }
    }
}
