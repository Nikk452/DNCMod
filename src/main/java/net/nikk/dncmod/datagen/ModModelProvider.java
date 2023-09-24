package net.nikk.dncmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ARCANE_WOOD);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DARK_STONE_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.LEAD_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TIN_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PLATINUM_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SILVER_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TITANIUM_ORE);
        blockStateModelGenerator.registerAmethyst(ModBlocks.WHITE_IRON_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_WHITE_IRON_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_WHITE_IRON_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_WHITE_IRON_BUD);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ADAMANTINE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ARCANUM_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.LEAD_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ELECTRUM_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DARK_STONE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SILVER_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.BRONZE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MITHRIL_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PLATINUM_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TIN_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TITANIUM_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DARK_STEEL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ARRIVAL_JACKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ARRIVAL_PANTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ARRIVAL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ADAMANTINE_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.ARCANUM_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.ASTRAL_CRYSTAL,Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.LONG_ARROW,Models.GENERATED);
        itemModelGenerator.register(ModItems.CLUB,Models.HANDHELD_ROD);
        itemModelGenerator.register(ModItems.GREAT_SWORD,Models.HANDHELD_ROD);
        itemModelGenerator.register(ModItems.DARK_STEEL_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.DARK_STONE,Models.GENERATED);
        itemModelGenerator.register(ModItems.DART,Models.HANDHELD);
        itemModelGenerator.register(ModItems.DAGGER,Models.HANDHELD_ROD);
        itemModelGenerator.register(ModItems.ELECTRUM_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATINUM_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_ADAMANTINE,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_BRONZE,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_ELECTRUM,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_LEAD,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_MITHRIL,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_OBSIDIAN,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_PLATINUM,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_SILVER,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_TIN,Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_TITANIUM,Models.GENERATED);
        itemModelGenerator.register(ModItems.REDSTONE_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.SHADOWTFRALL,Models.GENERATED);
        itemModelGenerator.register(ModItems.SHORT_SWORD,Models.HANDHELD);
        itemModelGenerator.register(ModItems.SILVER_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.TIN_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.TITANIUM_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.WHITE_IRON_CRYSTAL,Models.GENERATED);
        itemModelGenerator.register(ModItems.SCROLL,Models.GENERATED);
        itemModelGenerator.register(ModItems.SPELL_BOOK,Models.GENERATED);
        itemModelGenerator.register(ModItems.PARCHMENT,Models.GENERATED);
    }
}
