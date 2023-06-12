package net.nikk.dncmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.packet.*;

public class Networking {
    public static final Identifier CREATION_ID = new Identifier(DNCMod.MOD_ID,"creation");
    public static final Identifier DICE_ID = new Identifier(DNCMod.MOD_ID,"dice");
    public static final Identifier CREATION_SYNC_ID = new Identifier(DNCMod.MOD_ID,"creation_sync");
    public static final Identifier SYNC_CONFIG = new Identifier(DNCMod.MOD_ID,"sync_config");
    public static final Identifier ROLL_CREATION_ID = new Identifier(DNCMod.MOD_ID,"roll_creation");
    public static final Identifier REFRESH_STATS_ID = new Identifier(DNCMod.MOD_ID, "refresh_stats_id");
    public static final Identifier REFRESH_CLIENT_ID = new Identifier(DNCMod.MOD_ID, "refresh_stats_id");
    public static final Identifier SYNC_STATS = new Identifier(DNCMod.MOD_ID, "sync_stats");
    public static final Identifier EXAMPLE_S2C = new Identifier(DNCMod.MOD_ID, "example_s2c");
    public static final Identifier EXAMPLE_C2S = new Identifier(DNCMod.MOD_ID, "example_c2s");
    public static final Identifier LEVELUPS2C = new Identifier(DNCMod.MOD_ID, "levelups2c");
    public static final Identifier NEWNAMEC2S = new Identifier(DNCMod.MOD_ID, "new_name_c2s");
    public static final Identifier NEWNAMES2C = new Identifier(DNCMod.MOD_ID, "new_name_s2c");
    public static final Identifier SPELL_MENU_C2S = new Identifier(DNCMod.MOD_ID, "spell_menu_c2s");
    public static final Identifier SPELL_MENU_S2C = new Identifier(DNCMod.MOD_ID, "spell_menu_s2c");
    public static final Identifier SPELL_CREATE_C2S = new Identifier(DNCMod.MOD_ID, "spell_create_c2s");
    public static void RegisterC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(CREATION_ID, FinishCreationC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ROLL_CREATION_ID, RollStatsC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REFRESH_STATS_ID, StatsC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_C2S, ExampleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(NEWNAMEC2S,NewNameC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SPELL_MENU_C2S,SpellMenuC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SPELL_CREATE_C2S,SpellCreateC2SPacket::receive);
    }
    public static void RegisterS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(DICE_ID, diceS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SYNC_STATS, syncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CREATION_SYNC_ID, CreatedCharacterS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(EXAMPLE_S2C, ExampleS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(REFRESH_CLIENT_ID, StatsS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(LEVELUPS2C, LevelUpS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SYNC_CONFIG,SyncConfigS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(NEWNAMES2C,NewNameS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SPELL_MENU_S2C,SpellMenuS2CPacket::receive);
    }
}
