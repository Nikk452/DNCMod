package net.nikk.dncmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.item.ModItemGroup;

public class ModBlocks {
    //public static final Block EGGPLANT_CROP = registerBlockWithoutItem("eggplant_crop",
    //        new Block(FabricBlockSettings.copy(Blocks.WHEAT)));

    //public static final Block TANZANITE_BLOCK = registerBlock("tanzanite_block",
    //        new Block(FabricBlockSettings.of(Material.METAL).strength(4f).requiresTool()), ModItemGroup.TANZANITE);
    public static final Block ARCANE_WOOD = registerBlock("arcane_wood",
            new Block(FabricBlockSettings.of(Material.WOOD).strength(2f).requiresTool()), ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block SILVER_ORE = registerBlock("silver_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(2, 5)), ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block TIN_ORE = registerBlock("tin_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(2, 7)), ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block TITANIUM_ORE = registerBlock("titanium_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(2, 7)), ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block PLATINUM_ORE = registerBlock("platinum_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(2, 4)), ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block LEAD_ORE = registerBlock("lead_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(2, 5)), ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block DARK_STONE_ORE = registerBlock("dark_stone_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(2, 5)), ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block WHITE_IRON_CLUSTER = registerBlock("white_iron_cluster",
            new AmethystClusterBlock(7, 3, FabricBlockSettings.of(Material.METAL).nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
                return 5;
            })),ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block LARGE_WHITE_IRON_BUD = registerBlock("large_white_iron_bud",
            new AmethystClusterBlock(5, 3, FabricBlockSettings.copy(WHITE_IRON_CLUSTER).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
                return 4;
            })),ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block MEDIUM_WHITE_IRON_BUD = registerBlock("medium_white_iron_bud",
            new AmethystClusterBlock(4, 3, FabricBlockSettings.copy(WHITE_IRON_CLUSTER).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
                return 2;
            })),ModItemGroup.DNC_TAB_BLOCKS);
    public static final Block SMALL_WHITE_IRON_BUD = registerBlock("small_white_iron_bud",
            new AmethystClusterBlock(3, 4, FabricBlockSettings.copy(WHITE_IRON_CLUSTER).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
                return 1;
            })),ModItemGroup.DNC_TAB_BLOCKS);
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(DNCMod.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(DNCMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(DNCMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static void registerModBlocks() {
        DNCMod.LOGGER.debug("Registering ModBlocks for " + DNCMod.MOD_ID);
    }
}
