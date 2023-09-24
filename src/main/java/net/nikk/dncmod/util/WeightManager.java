package net.nikk.dncmod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.item.ModItems;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class WeightManager {
    private static final int defaultMaxInventoryWeight = 15000;
    private static final int weightGainPerSTR = 6804;
    public static Map<Item, Integer> itemWeights  = new HashMap<>();
    static {
        itemWeights.put(Items.ENDER_PEARL, 3);
        itemWeights.put(Items.NETHERITE_SCRAP, 300);
        itemWeights.put(Items.GHAST_TEAR, 10);
        itemWeights.put(Items.BLAZE_ROD, 50);
        itemWeights.put(Items.MAGMA_CREAM, 100);
        itemWeights.put(Items.WITHER_SKELETON_SKULL, 600);
        itemWeights.put(Items.PHANTOM_MEMBRANE, 50);
        itemWeights.put(Items.RABBIT_FOOT, 100);
        itemWeights.put(Items.SHULKER_SHELL, 50);
        itemWeights.put(Items.ELYTRA, 500);
        itemWeights.put(Items.TURTLE_HELMET, 50);
        itemWeights.put(Items.TRIDENT, 3100);
        itemWeights.put(Items.NAUTILUS_SHELL, 75);
        itemWeights.put(Items.TOTEM_OF_UNDYING, 3500);
        itemWeights.put(Items.NETHER_STAR, 100);
        itemWeights.put(Items.BEEF, 400);
        itemWeights.put(Items.CHICKEN, 300);
        itemWeights.put(Items.MUTTON, 350);
        itemWeights.put(Items.SNOWBALL, 100);
        itemWeights.put(Items.FEATHER, 1);
        itemWeights.put(Items.LEATHER, 220);
        itemWeights.put(Items.PORKCHOP, 350);
        itemWeights.put(Items.RABBIT, 300);
        itemWeights.put(Items.COD, 200);
        itemWeights.put(Items.SALMON, 200);
        itemWeights.put(Items.PUFFERFISH, 200);
        itemWeights.put(Items.TROPICAL_FISH, 200);
        itemWeights.put(Items.WHEAT_SEEDS, 1);
        itemWeights.put(Items.MELON_SEEDS, 1);
        itemWeights.put(Items.PUMPKIN_SEEDS, 1);
        itemWeights.put(Items.BEETROOT_SEEDS, 1);
        itemWeights.put(Items.CARROT, 60);
        itemWeights.put(Items.POTATO, 150);
        itemWeights.put(Items.POISONOUS_POTATO, 160);
        itemWeights.put(Items.SPIDER_EYE, 35);
        itemWeights.put(Items.GUNPOWDER, 100);
        itemWeights.put(Items.BONE, 10);
        itemWeights.put(Items.ROTTEN_FLESH, 150);
        itemWeights.put(Items.STRING, 1);
        itemWeights.put(Items.BEEHIVE, 1000);
        itemWeights.put(Items.PACKED_ICE, 1000);
        itemWeights.put(Items.SPAWNER, 100);
        itemWeights.put(Items.STONE, 2500);
        itemWeights.put(Items.GRASS_BLOCK, 1500);
        itemWeights.put(Items.FERN, 1);
        itemWeights.put(Items.ANCIENT_DEBRIS, 50);
        itemWeights.put(Items.BASALT, 2950);
        itemWeights.put(Items.BLACKSTONE, 50);
        itemWeights.put(Items.CALCITE, 2750);
        itemWeights.put(Items.CLAY, 1550);
        itemWeights.put(Items.COAL, 90);
        itemWeights.put(Items.RAW_COPPER, 365);
        itemWeights.put(Items.FLINT, 315);
        itemWeights.put(Items.DIAMOND, 390);
        itemWeights.put(Items.DRIPSTONE_BLOCK, 50);
        itemWeights.put(Items.EMERALD, 295);
        itemWeights.put(Items.RAW_GOLD, 1360);
        itemWeights.put(Items.ENCHANTED_GOLDEN_APPLE,98070);
        itemWeights.put(Items.GRAVEL, 1650);
        itemWeights.put(Items.RAW_IRON, 600);
        itemWeights.put(Items.LAPIS_LAZULI, 300);
        itemWeights.put(Items.QUARTZ, 300);
        itemWeights.put(Items.NETHERRACK, 50);
        itemWeights.put(Items.REDSTONE, 1);
        itemWeights.put(Items.SAND, 1650);
        itemWeights.put(Items.SOUL_SAND, 2050);
        itemWeights.put(Items.SOUL_SOIL, 1950);
        itemWeights.put(Items.TUFF, 1250);
        itemWeights.put(Items.KNOWLEDGE_BOOK, 510);
        itemWeights.put(Items.BARRIER, 1);
        itemWeights.put(Items.STICK, 106);
        itemWeights.put(Items.COMMAND_BLOCK, 1000);
        itemWeights.put(Items.CHAIN_COMMAND_BLOCK, 1000);
        itemWeights.put(Items.REPEATING_COMMAND_BLOCK, 1000);
        itemWeights.put(Items.STRUCTURE_BLOCK, 1000);
        itemWeights.put(Items.JIGSAW, 1000);
        itemWeights.put(Items.SLIME_BALL, 400);
        itemWeights.put(Items.IRON_INGOT,590);
        itemWeights.put(Items.GOLD_INGOT, 1360);
        itemWeights.put(Items.NETHER_WART,10);
        itemWeights.put(Items.GLOWSTONE_DUST,30);
        itemWeights.put(Items.INK_SAC, 345);
        itemWeights.put(Items.GLOW_INK_SAC, 360);
        itemWeights.put(Items.DEBUG_STICK, 10);
        itemWeights.put(Items.OAK_LOG, 850);
        itemWeights.put(Items.SPRUCE_LOG, 750);
        itemWeights.put(Items.BIRCH_LOG, 800);
        itemWeights.put(Items.JUNGLE_LOG, 800);
        itemWeights.put(Items.ACACIA_LOG, 800);
        itemWeights.put(Items.DARK_OAK_LOG, 850);
        itemWeights.put(Items.CRIMSON_STEM, 900);
        itemWeights.put(Items.AMETHYST_SHARD, 100);
        itemWeights.put(Items.WARPED_STEM, 850);
        itemWeights.put(Items.SUGAR_CANE,100);
        itemWeights.put(Items.ICE, 1000);
        itemWeights.put(Items.PUMPKIN,1500);
        itemWeights.put(Items.MELON_SLICE,400);
        itemWeights.put(Items.MELON,1600);
        itemWeights.put(Items.APPLE,150);
        itemWeights.put(Items.WHEAT,85);
        itemWeights.put(Items.EGG,100);
        itemWeights.put(Items.TURTLE_EGG,185);
        itemWeights.put(Items.NETHERITE_HELMET,8890);
        itemWeights.put(Items.NETHERITE_CHESTPLATE,10060);
        itemWeights.put(Items.NETHERITE_LEGGINGS,9970);
        itemWeights.put(Items.NETHERITE_BOOTS,8810);
        itemWeights.put(Items.NETHERITE_SWORD,7526);
        itemWeights.put(Items.NETHERITE_SHOVEL,7242);
        itemWeights.put(Items.NETHERITE_AXE,8322);
        itemWeights.put(Items.NETHERITE_PICKAXE,9222);
        itemWeights.put(Items.NETHERITE_HOE,8232);
        itemWeights.put(ModItems.RAW_ADAMANTINE,365);
        itemWeights.put(ModItems.RAW_BRONZE,635);
        itemWeights.put(ModItems.RAW_ELECTRUM,545);
        itemWeights.put(ModItems.RAW_LEAD,1360);
        itemWeights.put(ModItems.RAW_MITHRIL,275);
        itemWeights.put(ModItems.RAW_OBSIDIAN,365);
        itemWeights.put(ModItems.RAW_TIN,315);
        itemWeights.put(ModItems.RAW_SILVER,545);
        itemWeights.put(ModItems.RAW_PLATINUM,635);
        itemWeights.put(ModItems.RAW_TITANIUM,350);
        itemWeights.put(ModItems.ASTRAL_CRYSTAL,450);
        itemWeights.put(ModItems.WHITE_IRON_CRYSTAL,590);
        itemWeights.put(ModItems.SHADOWTFRALL,315);
        itemWeights.put(ModItems.DARK_STONE,11340);
        itemWeights.put(ModItems.DARK_STEEL_INGOT,22680);
        itemWeights.put(ModItems.REDSTONE_INGOT,545);
        itemWeights.put(ModBlocks.ARCANE_WOOD.asItem(),540);
        itemWeights.put(ModItems.PARCHMENT,1000);
        itemWeights.put(ModItems.SCROLL,1000);
        itemWeights.put(ModItems.SPELL_BOOK,1000);
    }
    public static void initialize(MinecraftServer server) {
        RecipeManager recipeManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getRecipeManager();
        DNCMod.LOGGER.info("[Dungeons & Crafting] assigning weight to items");
        for (Item item : Registries.ITEM) {
            if (!itemWeights.containsKey(item)) {
                Stream<Recipe<?>> itemRecipes = recipeManager.values().stream();
                if(hasRecipeOfType(recipeManager,item,RecipeType.SMELTING) || hasRecipeOfType(recipeManager,item,RecipeType.BLASTING)){
                    itemWeights.put(item,addNewItemWeightBasedOnRecipe(recipeManager,item, true));
                } else if(hasRecipeOfType(recipeManager,item,RecipeType.CRAFTING) || hasRecipeOfType(recipeManager,item,RecipeType.STONECUTTING)){
                    itemWeights.put(item,addNewItemWeightBasedOnRecipe(recipeManager,item, true));
                }else if(hasRecipeOfType(recipeManager,item,RecipeType.SMITHING) || hasRecipeOfType(recipeManager,item,RecipeType.STONECUTTING)){
                    itemWeights.put(item,addNewItemWeightBasedOnRecipe(recipeManager,item, true));
                } else if (itemRecipes.noneMatch(recipe -> recipe.getOutput().getItem() == item)) {
                    itemWeights.put(item, 500); // Set the base value to 600
                } else {
                    itemWeights.put(item,addNewItemWeightBasedOnRecipe(recipeManager,item, true));
                }
            }
        }
    }
    public static int getMaxInventoryWeight(PlayerEntity player){
        NbtCompound data = ((IEntityDataSaver)player).getPersistentData();
        return data.getBoolean("created")?data.getIntArray("stats")[0]*weightGainPerSTR: defaultMaxInventoryWeight;
    }
    public static int getWeight(Item item) {
        if (itemWeights.containsKey(item)) {
            return itemWeights.get(item);
        }
        return 1;
    }

    public static boolean canPlayerPickup(PlayerEntity player, ItemStack stack) {
        int totalWeight = getPlayerInventoryWeight(player);
        int itemWeight = getWeight(stack.getItem());
        NbtCompound data = ((IEntityDataSaver)player).getPersistentData();
        int remainingSpace = ((data.getBoolean("created")?data.getIntArray("stats")[0]*weightGainPerSTR: defaultMaxInventoryWeight) - totalWeight) / Math.max(itemWeight,1);
        return stack.getCount() <= remainingSpace;
    }
    public static boolean canPlayerPickup(PlayerEntity player, ItemStack stack, ItemStack lostStack) {
        int totalWeight = getPlayerInventoryWeight(player);
        int itemWeight = getWeight(stack.getItem());
        int lostWeight = getWeight(lostStack.getItem()) * lostStack.getCount();
        NbtCompound data = ((IEntityDataSaver)player).getPersistentData();
        int remainingSpace = ((data.getBoolean("created")?data.getIntArray("stats")[0]*weightGainPerSTR: defaultMaxInventoryWeight) - (totalWeight - lostWeight)) / Math.max(itemWeight,1);
        return stack.getCount() <= remainingSpace;
    }
    public static int getMaxPickUp(PlayerEntity player,ItemStack itemStack){
        int totalWeight = getPlayerInventoryWeight(player);
        int itemWeight = getWeight(itemStack.getItem());
        NbtCompound data = ((IEntityDataSaver)player).getPersistentData();
        return ((data.getBoolean("created")?data.getIntArray("stats")[0]*weightGainPerSTR: defaultMaxInventoryWeight) - totalWeight) / Math.max(itemWeight,1);
    }
    public static int getPlayerInventoryWeight(PlayerEntity player) {
        int totalWeight = 0;
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            totalWeight += getWeight(stack.getItem()) * stack.getCount();
        }
        return totalWeight;
    }
    private static <C extends Inventory,T extends Recipe<C>> boolean hasRecipeOfType(RecipeManager recipeManager, Item item, RecipeType<T> recipeType){
        return recipeManager.listAllOfType(recipeType).stream().anyMatch(recipe -> recipe.getOutput().getItem().equals(item));
    }
    private static int addNewItemWeightBasedOnRecipe(RecipeManager recipeManager,Item item, boolean bl1){
        Stream<Recipe<?>> itemRecipes = recipeManager.values().stream(); // Create a new stream for recipe matching

        Optional<Recipe<?>> matchingRecipe = itemRecipes
                .filter(recipe -> recipe.getOutput().getItem() == item)
                .findFirst();

        if (matchingRecipe.isPresent()) {
            Recipe<?> recipe = matchingRecipe.get();

            int weight = recipe.getIngredients().stream()
                    .mapToInt(input -> {
                        ItemStack[] stacks = input.getMatchingStacks();
                        if (stacks.length > 0) {
                            Item ingredientItem = stacks[0].getItem();
                            return itemWeights.getOrDefault(ingredientItem, bl1?addNewItemWeightBasedOnRecipe(recipeManager,ingredientItem, false):300);
                        } else {
                            return 300;
                        }
                    })
                    .sum();

            int outputCount = recipe.getOutput().getCount(); // Get the number of items produced by the recipe
            if (outputCount > 1) {
                weight /= outputCount; // Divide the weight by the number of items the recipe produces
            }

            return weight;
        } else {
            return 1600; // Set the base value to 1600
        }
    }

    public static int getItemCountInInv(PlayerEntity player, ItemStack itemStack2) {
        int totalCount = 0;
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            totalCount += stack.getCount();
        }
        return totalCount;
    }
}
