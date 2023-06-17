package net.nikk.dncmod.world;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.nikk.dncmod.DNCMod;


public class ServerState extends PersistentState {
    public NbtList UsedNicknames = new NbtList();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put("usedNicknames", UsedNicknames);
        return nbt;
    }

    public static ServerState createFromNbt(NbtCompound tag) {
        ServerState playerState = new ServerState();
        playerState.UsedNicknames = tag.getList("usedNicknames",8);
        return playerState;
    }

    public static ServerState getServerState(MinecraftServer server) {
        // First we get the persistentStateManager for the OVERWORLD
        PersistentStateManager persistentStateManager = server
                .getWorld(World.OVERWORLD).getPersistentStateManager();

        // Calling this reads the file from the disk if it exists, or creates a new one and saves it to the disk
        // You need to use a unique string as the key. You should already have a MODID variable defined by you somewhere in your code. Use that.
        ServerState serverState = persistentStateManager.getOrCreate(ServerState::createFromNbt, ServerState::new, DNCMod.MOD_ID);

        return serverState;
    }
}
