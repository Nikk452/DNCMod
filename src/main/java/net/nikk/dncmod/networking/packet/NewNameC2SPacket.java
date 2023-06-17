package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.WorldSaveHandler;
import net.nikk.dncmod.mixin.PlayerManagerAccess;
import net.nikk.dncmod.mixin.SaveHandlerAccess;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.world.ServerState;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static net.nikk.dncmod.DNCMod.LOGGER;

public class NewNameC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        String name = buf.readString();
        server.execute(()->{
            NbtList list = ServerState.getServerState(player.server).UsedNicknames;
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeBoolean(!(list.contains(NbtString.of(name))));
            ServerPlayNetworking.send(player, Networking.NEWNAMES2C, buffer);
        });
    }
    @Nullable
    private static NbtCompound getNbtFromUUID(ServerPlayerEntity player, String uuid) {
        WorldSaveHandler saveHandler = ((PlayerManagerAccess)player.getServer().getPlayerManager()).getSaveHandler();
        NbtCompound nbtCompound = null;
        try {
            File file = new File(((SaveHandlerAccess)saveHandler).getPlayerDataDir(), uuid + ".dat");
            if (file.exists() && file.isFile()) {
                nbtCompound = NbtIo.readCompressed(file);
            }
        } catch (Exception var4) {
            LOGGER.warn("[Dungeons & Crafting] Failed to load player data for UUID: "+uuid.toString());
        }

        if (nbtCompound != null) {
            int i = nbtCompound.contains("DataVersion", 3) ? nbtCompound.getInt("DataVersion") : -1;
            nbtCompound = NbtHelper.update(((SaveHandlerAccess)saveHandler).getDataFixer(), DataFixTypes.PLAYER, nbtCompound, i);
        }

        return nbtCompound;
    }
}
