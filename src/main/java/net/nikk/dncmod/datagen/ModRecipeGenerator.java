package net.nikk.dncmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, List.of(ModBlocks.SILVER_ORE), ModItems.SILVER_INGOT,
                0.7f, 200, "silver_ingot");
        offerSmelting(exporter, List.of(ModItems.RAW_SILVER), ModItems.SILVER_INGOT,
                0.7f, 200, "silver_ingot");
        offerBlasting(exporter, List.of(ModBlocks.SILVER_ORE), ModItems.SILVER_INGOT,
                0.7f, 200, "silver_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_SILVER), ModItems.SILVER_INGOT,
                0.7f, 200, "silver_ingot");

        //offerReversibleCompactingRecipes(exporter, ModItems.TANZANITE, ModBlocks.TANZANITE_BLOCK);

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
