package net.nikk.dncmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.packet.*;
import net.nikk.dncmod.screen.CharCreationScreen1;
import net.nikk.dncmod.screen.StatScreen1;
import net.nikk.dncmod.util.IEntityDataSaver;

public class Networking {
    public static final Identifier CREATION_ID = new Identifier(DNCMod.MOD_ID,"creation");
    public static final Identifier CREATION_SYNC_ID = new Identifier(DNCMod.MOD_ID,"creation_sync");

    public static final Identifier ROLL_CREATION_ID = new Identifier(DNCMod.MOD_ID,"roll_creation");
    public static final Identifier REFRESH_STATS_ID = new Identifier(DNCMod.MOD_ID, "refresh_stats_id");
    public static final Identifier REFRESH_CLIENT_ID = new Identifier(DNCMod.MOD_ID, "refresh_stats_id");
    public static final Identifier SYNC_STATS = new Identifier(DNCMod.MOD_ID, "sync_stats");
    public static final Identifier EXAMPLE_S2C = new Identifier(DNCMod.MOD_ID, "example_s2c");
    public static final Identifier EXAMPLE_C2S = new Identifier(DNCMod.MOD_ID, "example_c2s");
    public static final Identifier LEVELUPS2C = new Identifier(DNCMod.MOD_ID, "levelups2c");
    public static void RegisterC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(CREATION_ID, FinishCreationC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ROLL_CREATION_ID, RollStatsC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REFRESH_STATS_ID, StatsC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_C2S, ExampleC2SPacket::receive);

    }
    public static void RegisterS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SYNC_STATS, syncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CREATION_SYNC_ID, CreatedCharacterS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(EXAMPLE_S2C, ExampleS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(REFRESH_CLIENT_ID, StatsS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(LEVELUPS2C, LevelUpS2CPacket::receive);
    }
}
