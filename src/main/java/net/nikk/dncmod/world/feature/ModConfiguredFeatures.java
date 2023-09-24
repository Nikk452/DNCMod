package net.nikk.dncmod.world.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> SILVER_ORE_KEY = registerKey("silver_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> TIN_ORE_KEY = registerKey("tin_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> LEAD_ORE_KEY = registerKey("lead_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> TITANIUM_ORE_KEY = registerKey("titanium_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> PLATINUM_ORE_KEY = registerKey("platinum_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> DARK_STONE_ORE_KEY = registerKey("dark_stone_ore");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        var placedFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreFeatureConfig.Target> overworldSilverOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.SILVER_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldTinOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.TIN_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldLeadOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.LEAD_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldTitaniumOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.TITANIUM_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldPlatinumOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.PLATINUM_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldDarkstoneOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.DARK_STONE_ORE.getDefaultState()));

        register(context, SILVER_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSilverOres, 9));
        register(context, TIN_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldTinOres, 9));
        register(context, LEAD_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldLeadOres, 9));
        register(context, TITANIUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldTitaniumOres, 9));
        register(context, PLATINUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldPlatinumOres, 9));
        register(context, DARK_STONE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldDarkstoneOres, 9));
    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(DNCMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
