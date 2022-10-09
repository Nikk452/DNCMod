package net.nikk.dncmod.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final List<OreFeatureConfig.Target> OVERWORLD_SILVER_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.SILVER_ORE.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SILVER_ORE =
            ConfiguredFeatures.register("silver_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_SILVER_ORES, 9));
    public static final List<OreFeatureConfig.Target> OVERWORLD_TIN_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.TIN_ORE.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> TIN_ORE =
            ConfiguredFeatures.register("tin_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_TIN_ORES, 9));
    public static final List<OreFeatureConfig.Target> OVERWORLD_LEAD_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.LEAD_ORE.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> LEAD_ORE =
            ConfiguredFeatures.register("lead_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_LEAD_ORES, 9));
    public static final List<OreFeatureConfig.Target> OVERWORLD_TITANIUM_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.TITANIUM_ORE.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> TITANIUM_ORE =
            ConfiguredFeatures.register("titanium_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_TITANIUM_ORES, 9));
    public static final List<OreFeatureConfig.Target> OVERWORLD_PLATINUM_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.PLATINUM_ORE.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> PLATINUM_ORE =
            ConfiguredFeatures.register("platinum_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_PLATINUM_ORES, 9));
    public static final List<OreFeatureConfig.Target> OVERWORLD_DARK_STONE_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.DARK_STONE_ORE.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> DARK_STONE_ORE =
            ConfiguredFeatures.register("dark_stone_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_DARK_STONE_ORES, 9));

    public static void registerConfiguredFeatures() {
        DNCMod.LOGGER.debug("Registering the ModConfiguredFeatures for " + DNCMod.MOD_ID);
    }
}
