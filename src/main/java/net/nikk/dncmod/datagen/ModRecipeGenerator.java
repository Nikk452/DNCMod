package net.nikk.dncmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        /*ShapedRecipeJsonBuilder.create(ModItems.EIGHT_BALL)
                .pattern("###")
                .pattern("#I#")
                .pattern("###")
                .input('I', Items.IRON_INGOT)
                .input('#', ModItems.TANZANITE)
                .criterion(RecipeProvider.hasItem(Items.IRON_INGOT),
                        RecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .criterion(RecipeProvider.hasItem(ModItems.TANZANITE),
                        RecipeProvider.conditionsFromItem(ModItems.TANZANITE))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.EIGHT_BALL)));
         */
    }
}
