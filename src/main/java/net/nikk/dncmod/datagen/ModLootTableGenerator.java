package net.nikk.dncmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.block.ModBlocks;

import java.util.function.BiConsumer;

public class ModLootTableGenerator extends SimpleFabricLootTableProvider {
    public ModLootTableGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer) {
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/bronze_block"),
                BlockLootTableGenerator.drops(ModBlocks.BRONZE_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/adamantine_block"),
                BlockLootTableGenerator.drops(ModBlocks.ADAMANTINE_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/arcanum_block"),
                BlockLootTableGenerator.drops(ModBlocks.ARCANUM_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/arcane_wood"),
                BlockLootTableGenerator.drops(ModBlocks.ARCANE_WOOD));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/silver_block"),
                BlockLootTableGenerator.drops(ModBlocks.SILVER_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/tin_block"),
                BlockLootTableGenerator.drops(ModBlocks.TIN_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/titanium_block"),
                BlockLootTableGenerator.drops(ModBlocks.TITANIUM_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/mithril_block"),
                BlockLootTableGenerator.drops(ModBlocks.MITHRIL_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/lead_block"),
                BlockLootTableGenerator.drops(ModBlocks.LEAD_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/electrum_block"),
                BlockLootTableGenerator.drops(ModBlocks.ELECTRUM_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/dark_steel_block"),
                BlockLootTableGenerator.drops(ModBlocks.DARK_STEEL_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/dark_stone_block"),
                BlockLootTableGenerator.drops(ModBlocks.DARK_STONE_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/platinum_block"),
                BlockLootTableGenerator.drops(ModBlocks.PLATINUM_BLOCK));
    }
}
