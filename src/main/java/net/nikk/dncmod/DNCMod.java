package net.nikk.dncmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.config.ModConfig;
import net.nikk.dncmod.event.*;
import net.nikk.dncmod.item.ModItems;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.world.feature.ModConfiguredFeatures;
import net.nikk.dncmod.world.gen.ModOreGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DNCMod implements ModInitializer {
	public static final String MOD_ID = "dncmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ModConfig CONFIG;
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
		ServerPlayerEvents.COPY_FROM.register(new CopyFromEvent());
		ServerPlayerEvents.AFTER_RESPAWN.register(new AfterRespawnEvent());
		AttackEntityCallback.EVENT.register(new AttackEntityEvent());
		}
	public void craftPaths(){
		try{
			if(!Files.isDirectory(Paths.get("./config"))){
				Files.createDirectory(Paths.get("./config"));
			}
			if(!Files.isDirectory(Paths.get("./config/dungeons-and-crafting"))){
				Files.createDirectory(Paths.get("./config/dungeons-and-crafting"));
			}
			if(!Files.exists(Paths.get("./config/dungeons-and-crafting/config.json"))){
				IOManager.generateModConfig();
			}
			if(!Files.exists(Paths.get("./config/dungeons-and-crafting/used_names.json"))){
				IOManager.generateUsedNames();
			}
			CONFIG = IOManager.readModConfig();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void sendConfigSyncPacket(ServerPlayerEntity player){
		if(false) if(!player.getServer().isHost(player.getGameProfile())) {
			PacketByteBuf buf = PacketByteBufs.create();
			ModConfig cfg = DNCMod.CONFIG;
			buf.writeInt(cfg.xp_per_lvl_multi);
			buf.writeBoolean(cfg.isRace_human_approved);
			buf.writeBoolean(cfg.isRace_elf_approved);
			buf.writeBoolean(cfg.isRace_dwarf_approved);
			buf.writeBoolean(cfg.isClass_fighter_approved);
			buf.writeBoolean(cfg.isClass_druid_approved);
			buf.writeBoolean(cfg.isClass_cleric_approved);
			buf.writeBoolean(cfg.isClass_wizard_approved);
			buf.writeBoolean(cfg.isClass_sorcerer_approved);
			buf.writeBoolean(cfg.isClass_monk_approved);
			ServerPlayNetworking.send(player, Networking.SYNC_CONFIG, buf);
			LOGGER.info("[Dungeons & Crafting] Sending config to player");
		}
	}
}
