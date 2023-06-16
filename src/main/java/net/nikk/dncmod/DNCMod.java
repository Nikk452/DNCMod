package net.nikk.dncmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.command.SetXpMultiCommand;
import net.nikk.dncmod.config.ModConfig;
import net.nikk.dncmod.effect.ModEffects;
import net.nikk.dncmod.entity.ModEntities;
import net.nikk.dncmod.entity.custom.GoblinEntity;
import net.nikk.dncmod.event.*;
import net.nikk.dncmod.item.ModItems;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.ModScreenHandlers;
import net.nikk.dncmod.world.feature.ModConfiguredFeatures;
import net.nikk.dncmod.world.gen.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;
import java.util.Objects;

public class DNCMod implements ModInitializer {
	public static final String MOD_ID = "dncmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean isExclusive = false;
	public static ModConfig CONFIG;
	@Override
	public void onInitialize() {
		IOManager.craftPaths();
		ModConfiguredFeatures.registerConfiguredFeatures();
		ModItems.registerModItems();
		ModWorldGen.generateWorldGen();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModScreenHandlers.registerAllScreenHandlers();
		ModEffects.registerEffects();
		Networking.RegisterC2SPackets();
		GeckoLib.initialize();
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(new KillEntityHandler());
		ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartedEvent());
		ServerPlayerEvents.COPY_FROM.register(new CopyFromEvent());
		ServerPlayerEvents.AFTER_RESPAWN.register(new AfterRespawnEvent());
		AttackEntityCallback.EVENT.register(new AttackEntityEvent());
		PlayerBlockBreakEvents.AFTER.register(new MineBlockEvent());
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
		CommandRegistrationCallback.EVENT.register(SetXpMultiCommand::register);
		FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN, GoblinEntity.setAttributes());
	}

	public static void sendConfigSyncPacket(ServerPlayerEntity player){
		if(!player.getServer().isHost(player.getGameProfile())) {
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
