package net.nikk.dncmod.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> SILVER_ORE_PLACED = PlacedFeatures.register("silver_ore_placed",
            ModConfiguredFeatures.SILVER_ORE, modifiersWithCount(9,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(80))));
    public static final RegistryEntry<PlacedFeature> TIN_ORE_PLACED = PlacedFeatures.register("tin_ore_placed",
            ModConfiguredFeatures.TIN_ORE, modifiersWithCount(9,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(80))));
    public static final RegistryEntry<PlacedFeature> LEAD_ORE_PLACED = PlacedFeatures.register("lead_ore_placed",
            ModConfiguredFeatures.LEAD_ORE, modifiersWithCount(9,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(80))));
    public static final RegistryEntry<PlacedFeature> TITANIUM_ORE_PLACED = PlacedFeatures.register("titanium_ore_placed",
            ModConfiguredFeatures.TITANIUM_ORE, modifiersWithCount(9,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(80))));
    public static final RegistryEntry<PlacedFeature> PLATINUM_ORE_PLACED = PlacedFeatures.register("platinum_ore_placed",
            ModConfiguredFeatures.PLATINUM_ORE, modifiersWithCount(9,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(80))));
    public static final RegistryEntry<PlacedFeature> DARK_STONE_ORE_PLACED = PlacedFeatures.register("dark_stone_ore_placed",
            ModConfiguredFeatures.DARK_STONE_ORE, modifiersWithCount(9,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(80))));

    //public static final RegistryEntry<PlacedFeature> NETHER_TANZANITE_ORE_PLACED = PlacedFeatures.register("nether_tanzanite_ore_placed",
    //        ModConfiguredFeatures.NETHER_TANZANITE_ORE, modifiersWithCount(10,
    //                HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

    //public static final RegistryEntry<PlacedFeature> END_TANZANITE_ORE_PLACED = PlacedFeatures.register("end_tanzanite_ore_placed",
    //        ModConfiguredFeatures.END_TANZANITE_ORE, modifiersWithCount(10,
    //                HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));



    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }
    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }
    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
