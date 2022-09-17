package net.nikk.dncmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.packet.*;

public class Networking {
    public static final Identifier CREATION_ID = new Identifier(DNCMod.MOD_ID,"creation");
    public static final Identifier CREATION_SYNC_ID = new Identifier(DNCMod.MOD_ID,"creation_sync");

    public static final Identifier ROLL_CREATION_ID = new Identifier(DNCMod.MOD_ID,"roll_creation");
    public static final Identifier REFRESH_STATS_ID = new Identifier(DNCMod.MOD_ID, "refresh_stats-id");
    public static final Identifier SYNC_STATS = new Identifier(DNCMod.MOD_ID, "sync_stats");
    public static void RegisterC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(CREATION_ID, FinishCreationC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ROLL_CREATION_ID, RollStatsC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REFRESH_STATS_ID, StatsC2SPacket::receive);

    }
    public static void RegisterS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SYNC_STATS, syncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CREATION_SYNC_ID, CreatedCharacterS2CPacket::receive);
    }
}
