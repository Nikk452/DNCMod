package net.nikk.dncmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.command.SetXpMultiCommand;
import net.nikk.dncmod.config.ModConfig;
import net.nikk.dncmod.effect.ModEffects;
import net.nikk.dncmod.entity.ModEntities;
import net.nikk.dncmod.entity.custom.GoblinEntity;
import net.nikk.dncmod.event.*;
import net.nikk.dncmod.item.ModItemGroup;
import net.nikk.dncmod.item.ModItems;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.ModScreenHandlers;
import net.nikk.dncmod.world.feature.ModConfiguredFeatures;
import net.nikk.dncmod.world.gen.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DNCMod implements ModInitializer {
	public static final String MOD_ID = "dncmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean isExclusive = false;
	public static ModConfig CONFIG;
	@Override
	public void onInitialize() {
		IOManager.craftPaths();
		ModItemGroup.registerItemGroup();
		ModItems.registerModItems();
		ModWorldGen.generateWorldGen();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModScreenHandlers.registerAllScreenHandlers();
		ModEffects.registerEffects();
		Networking.RegisterC2SPackets();
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(new KillEntityHandler());
		ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartedEvent());
		ServerPlayerEvents.COPY_FROM.register(new CopyFromEvent());
		ServerPlayerEvents.AFTER_RESPAWN.register(new AfterRespawnEvent());
		PlayerBlockBreakEvents.AFTER.register(new MineBlockEvent());
		ServerTickEvents.START_SERVER_TICK.register(new ServerTickHandler());
		CommandRegistrationCallback.EVENT.register(SetXpMultiCommand::register);
		FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN, GoblinEntity.setAttributes());
	}
}
