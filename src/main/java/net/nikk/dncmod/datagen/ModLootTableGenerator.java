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
        //identifierBuilderBiConsumer.accept(new Identifier(DNCMod.MOD_ID, "blocks/dogwood_planks"),
        //        BlockLootTableGenerator.drops(ModBlocks.DOGWOOD_PLANKS));
    }
}
