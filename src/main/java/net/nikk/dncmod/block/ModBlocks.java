package net.nikk.dncmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.item.ModItemGroup;

public class ModBlocks {
    //public static final Block EGGPLANT_CROP = registerBlockWithoutItem("eggplant_crop",
    //        new Block(FabricBlockSettings.copy(Blocks.WHEAT)));

    //public static final Block TANZANITE_BLOCK = registerBlock("tanzanite_block",
    //        new Block(FabricBlockSettings.of(Material.METAL).strength(4f).requiresTool()), ModItemGroup.TANZANITE);
    public static final Block ARCANE_WOOD = registerBlock("arcane_wood",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2f).requiresTool()));
    public static final Block SILVER_ORE = registerBlock("silver_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).strength(3.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 5)));
    public static final Block TIN_ORE = registerBlock("tin_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).strength(3.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 7)));
    public static final Block TITANIUM_ORE = registerBlock("titanium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).strength(3.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 7)));
    public static final Block PLATINUM_ORE = registerBlock("platinum_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).strength(3.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 4)));
    public static final Block LEAD_ORE = registerBlock("lead_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).strength(3.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 5)));
    public static final Block DARK_STONE_ORE = registerBlock("dark_stone_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).strength(5f).requiresTool(),
                    UniformIntProvider.create(2, 5)));
    public static final Block ADAMANTINE_BLOCK = registerBlock("adamantine_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(5.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 5)));
    public static final Block ARCANUM_BLOCK = registerBlock("arcanum_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 7)));
    public static final Block BRONZE_BLOCK = registerBlock("bronze_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 7)));
    public static final Block MITHRIL_BLOCK = registerBlock("mithril_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 4)));
    public static final Block ELECTRUM_BLOCK = registerBlock("electrum_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 5)));
    public static final Block DARK_STONE_BLOCK = registerBlock("dark_stone_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(5f).requiresTool(),
                    UniformIntProvider.create(2, 5)));
    public static final Block DARK_STEEL_BLOCK = registerBlock("dark_steel_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(5f).requiresTool(),
                    UniformIntProvider.create(2, 5)));
    public static final Block SILVER_BLOCK = registerBlock("silver_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 5)));
    public static final Block TIN_BLOCK = registerBlock("tin_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 7)));
    public static final Block TITANIUM_BLOCK = registerBlock("titanium_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 7)));
    public static final Block PLATINUM_BLOCK = registerBlock("platinum_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(5.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 4)));
    public static final Block LEAD_BLOCK = registerBlock("lead_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f,3.0f).requiresTool(),
                    UniformIntProvider.create(2, 5)));

    public static final Block WHITE_IRON_CLUSTER = registerBlock("white_iron_cluster",
            new AmethystClusterBlock(7, 3, FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(3.0f,3.0f).luminance((state) -> {
                return 5;
            })));
    public static final Block LARGE_WHITE_IRON_BUD = registerBlock("large_white_iron_bud",
            new AmethystClusterBlock(5, 3, FabricBlockSettings.copy(WHITE_IRON_CLUSTER).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(3.0f,3.0f).luminance((state) -> {
                return 4;
            })));
    public static final Block MEDIUM_WHITE_IRON_BUD = registerBlock("medium_white_iron_bud",
            new AmethystClusterBlock(4, 3, FabricBlockSettings.copy(WHITE_IRON_CLUSTER).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
                return 2;
            })));
    public static final Block SMALL_WHITE_IRON_BUD = registerBlock("small_white_iron_bud",
            new AmethystClusterBlock(3, 4, FabricBlockSettings.copy(WHITE_IRON_CLUSTER).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(3.0f,3.0f).luminance((state) -> {
                return 1;
            })));
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(DNCMod.MOD_ID, name), block);
    }
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(DNCMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Item temp = Registry.register(Registries.ITEM, new Identifier(DNCMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        return temp;
    }

    public static void registerModBlocks() {
        DNCMod.LOGGER.debug("[Dungeons & Crafting] Registering Mod Blocks");
    }
}
