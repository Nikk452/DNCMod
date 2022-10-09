package net.nikk.dncmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.event.KillEntityHandler;
import net.nikk.dncmod.item.ModItems;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.world.feature.ModConfiguredFeatures;
import net.nikk.dncmod.world.gen.ModOreGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DNCMod implements ModInitializer {
	public static final String MOD_ID = "dncmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModConfiguredFeatures.registerConfiguredFeatures();
		ModItems.registerModItems();
		ModOreGeneration.generateOres();
		ModBlocks.registerModBlocks();
		Networking.RegisterC2SPackets();
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(new KillEntityHandler());
		}
}
