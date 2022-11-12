package net.nikk.dncmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.JUMPY_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ARRIVAL_JACKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ARRIVAL_PANTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ARRIVAL_BOOTS, Models.GENERATED);
    }
}
