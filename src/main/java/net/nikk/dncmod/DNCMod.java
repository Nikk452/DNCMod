package net.nikk.dncmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.config.ModConfig;
import net.nikk.dncmod.config.SystemTimeConfig;
import net.nikk.dncmod.config.TimeDataStorage;
import net.nikk.dncmod.event.ExclusiveServer;
import net.nikk.dncmod.event.KillEntityHandler;
import net.nikk.dncmod.item.ModItems;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.world.feature.ModConfiguredFeatures;
import net.nikk.dncmod.world.gen.ModOreGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class DNCMod implements ModInitializer {
	public static final String MOD_ID = "dncmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static HashMap<String, TimeDataStorage> timeDataMap;
	public static HashMap<String, SystemTimeConfig> sysTimeMap;
	public static ModConfig CONFIG;
	public static SystemTimeConfig systemTimeConfig;
	@Override
	public void onInitialize() {
		craftPaths();
		ModConfiguredFeatures.registerConfiguredFeatures();
		ModItems.registerModItems();
		ModOreGeneration.generateOres();
		ModBlocks.registerModBlocks();
		Networking.RegisterC2SPackets();
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(new KillEntityHandler());
		ServerLifecycleEvents.SERVER_STARTED.register(new ExclusiveServer());
		}
	public void craftPaths(){
		try{
			if(!Files.isDirectory(Paths.get("./config"))){
				Files.createDirectory(Paths.get("./config"));
			}
			if(!Files.isDirectory(Paths.get("./config/dungeons-and-crafting"))){
				Files.createDirectory(Paths.get("./config/dungeons-and-crafting"));
			}
			if(!Files.exists(Paths.get("./config/dungeons-and-crafting/time-data.json"))){
				IOManager.genTimeData();
			}
			if(!Files.exists(Paths.get("./config/dungeons-and-crafting/config.json"))){
				IOManager.generateModConfig();
			}
			if(!Files.exists(Paths.get("./config/dungeons-and-crafting/system-time-data.json"))){
				IOManager.generateSysTimeCfg();
			}
			if(!Files.exists(Paths.get("./config/dungeons-and-crafting/system-time-data-global.json"))){
				IOManager.generateSysTimeCfg();
			}
			if(!Files.exists(Paths.get("./config/dungeons-and-crafting/system-time-data.json"))){
				IOManager.generateMapSysTime();
			}
			CONFIG = IOManager.readModConfig();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public static String getFormattedTime(long ms){
		long seconds = ms;
		long hours = seconds / 3600;
		seconds -= (hours * 3600);
		long minutes = seconds / 60;
		seconds -= (minutes * 60);
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	public static void sendConfigSyncPacket(ServerPlayerEntity player){
		if(false) if(!player.getServer().isHost(player.getGameProfile())) {
			PacketByteBuf buf = PacketByteBufs.create();
			ModConfig cfg = DNCMod.CONFIG;
			SystemTimeConfig cfgs = DNCMod.systemTimeConfig;
			buf.writeBoolean(cfg.patchSkyAngle);
			buf.writeBoolean(cfg.syncWithSystemTime);
			buf.writeBoolean(cfg.systemTimePerDimensions);
			buf.writeBoolean(cfg.enableNightSkipAcceleration);
			buf.writeInt(cfg.accelerationSpeed);
			buf.writeBoolean(cfg.enableThreshold);
			buf.writeInt(cfg.thresholdPercentage);
			buf.writeBoolean(cfg.flatAcceleration);

			buf.writeString(cfgs.sunrise);
			buf.writeString(cfgs.sunset);
			buf.writeString(cfgs.timeZone);

			buf.writeMap(DNCMod.timeDataMap, PacketByteBuf::writeString, (packetByteBuf, timeDataStorage) -> {
				packetByteBuf.writeLong(timeDataStorage.dayDuration);
				packetByteBuf.writeLong(timeDataStorage.nightDuration);
			});
			buf.writeMap(DNCMod.sysTimeMap, PacketByteBuf::writeString, (packetByteBuf, systemTimeConfig1) -> {
				packetByteBuf.writeString(systemTimeConfig1.sunrise);
				packetByteBuf.writeString(systemTimeConfig1.sunset);
				packetByteBuf.writeString(systemTimeConfig1.timeZone);
			});
			ServerPlayNetworking.send(player, Networking.SYNC_CONFIG, buf);
			LOGGER.info("[Dungeons & Crafting] Sending config to player");
		} else ServerPlayNetworking.send(player, Networking.SETUP_TIME, PacketByteBufs.create());
	}
}
