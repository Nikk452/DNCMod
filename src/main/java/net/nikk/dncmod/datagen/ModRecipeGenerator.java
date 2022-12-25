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

        offerSmelting(exporter, List.of(ModBlocks.TIN_ORE), ModItems.TIN_INGOT,
                0.7f, 200, "tin_ingot");
        offerSmelting(exporter, List.of(ModItems.RAW_TIN), ModItems.TIN_INGOT,
                0.7f, 200, "tin_ingot");
        offerBlasting(exporter, List.of(ModBlocks.TIN_ORE), ModItems.TIN_INGOT,
                0.7f, 200, "tin_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_TIN), ModItems.TIN_INGOT,
                0.7f, 200, "tin_ingot");

        offerSmelting(exporter, List.of(ModBlocks.LEAD_ORE), ModItems.LEAD_INGOT,
                0.7f, 200, "lead_ingot");
        offerSmelting(exporter, List.of(ModItems.RAW_LEAD), ModItems.LEAD_INGOT,
                0.7f, 200, "lead_ingot");
        offerBlasting(exporter, List.of(ModBlocks.LEAD_ORE), ModItems.LEAD_INGOT,
                0.7f, 200, "lead_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_LEAD), ModItems.LEAD_INGOT,
                0.7f, 200, "lead_ingot");

        offerSmelting(exporter, List.of(ModBlocks.DARK_STONE_ORE), ModItems.DARK_STEEL_INGOT,
                0.7f, 200, "dark_stone_ingot");
        offerSmelting(exporter, List.of(ModItems.DARK_STONE), ModItems.DARK_STEEL_INGOT,
                0.7f, 200, "dark_stone_ingot");
        offerBlasting(exporter, List.of(ModBlocks.DARK_STONE_ORE), ModItems.DARK_STEEL_INGOT,
                0.7f, 200, "dark_stone_ingot");
        offerBlasting(exporter, List.of(ModItems.DARK_STONE), ModItems.DARK_STEEL_INGOT,
                0.7f, 200, "dark_stone_ingot");

        offerSmelting(exporter, List.of(ModBlocks.PLATINUM_ORE), ModItems.PLATINUM_INGOT,
                0.7f, 200, "platinum_ingot");
        offerSmelting(exporter, List.of(ModItems.RAW_PLATINUM), ModItems.PLATINUM_INGOT,
                0.7f, 200, "platinum_ingot");
        offerBlasting(exporter, List.of(ModBlocks.PLATINUM_ORE), ModItems.PLATINUM_INGOT,
                0.7f, 200, "platinum_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_PLATINUM), ModItems.PLATINUM_INGOT,
                0.7f, 200, "platinum_ingot");

        offerSmelting(exporter, List.of(ModBlocks.TITANIUM_ORE), ModItems.TITANIUM_INGOT,
                0.7f, 200, "titanium_ingot");
        offerSmelting(exporter, List.of(ModItems.RAW_TITANIUM), ModItems.TITANIUM_INGOT,
                0.7f, 200, "titanium_ingot");
        offerBlasting(exporter, List.of(ModBlocks.TITANIUM_ORE), ModItems.TITANIUM_INGOT,
                0.7f, 200, "titanium_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_TITANIUM), ModItems.TITANIUM_INGOT,
                0.7f, 200, "titanium_ingot");

        offerSmelting(exporter, List.of(ModItems.RAW_ADAMANTINE), ModItems.ADAMANTINE_INGOT,
                0.7f, 200, "adamantine_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_ADAMANTINE), ModItems.ADAMANTINE_INGOT,
                0.7f, 200, "adamantine_ingot");

        offerSmelting(exporter, List.of(ModItems.RAW_BRONZE), ModItems.BRONZE_INGOT,
                0.7f, 200, "bronze_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_BRONZE), ModItems.BRONZE_INGOT,
                0.7f, 200, "bronze_ingot");

        offerSmelting(exporter, List.of(ModItems.RAW_MITHRIL), ModItems.MITHRIL_INGOT,
                0.7f, 200, "mithril_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_MITHRIL), ModItems.MITHRIL_INGOT,
                0.7f, 200, "mithril_ingot");

        offerSmelting(exporter, List.of(ModItems.RAW_ELECTRUM), ModItems.ELECTRUM_INGOT,
                0.7f, 200, "electrum_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_ELECTRUM), ModItems.ELECTRUM_INGOT,
                0.7f, 200, "electrum_ingot");

        offerSmelting(exporter, List.of(ModItems.RAW_OBSIDIAN), ModItems.OBSIDIAN_INGOT,
                0.7f, 200, "obsidian_ingot");
        offerBlasting(exporter, List.of(ModItems.RAW_OBSIDIAN), ModItems.OBSIDIAN_INGOT,
                0.7f, 200, "obsidian_ingot");

        offerReversibleCompactingRecipes(exporter, ModItems.ADAMANTINE_INGOT, ModBlocks.ADAMANTINE_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.ARCANUM_INGOT, ModBlocks.ARCANUM_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.TIN_INGOT, ModBlocks.TIN_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.BRONZE_INGOT, ModBlocks.BRONZE_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.SILVER_INGOT, ModBlocks.SILVER_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.LEAD_INGOT, ModBlocks.LEAD_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.MITHRIL_INGOT, ModBlocks.MITHRIL_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.TITANIUM_INGOT, ModBlocks.TITANIUM_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.ELECTRUM_INGOT, ModBlocks.ELECTRUM_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.PLATINUM_INGOT, ModBlocks.PLATINUM_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.DARK_STONE, ModBlocks.DARK_STONE_BLOCK);
        offerReversibleCompactingRecipes(exporter, ModItems.DARK_STEEL_INGOT, ModBlocks.DARK_STEEL_BLOCK);


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
