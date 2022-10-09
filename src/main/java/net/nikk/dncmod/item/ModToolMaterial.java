package net.nikk.dncmod.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    BRONZE(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.BRONZE_INGOT)),
    TIN(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.TIN_INGOT)),
    ADAMANTINE(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.ADAMANTINE_INGOT)),
    TITANIUM(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.TITANIUM_INGOT)),
    PLATINUM(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.PLATINUM_INGOT)),
    LEAD(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.LEAD_INGOT)),
    DARK_STEEL(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.DARK_STEEL_INGOT)),
    ELECTRUM(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.ELECTRUM_INGOT)),
    SHADOWTFRALL(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.SHADOWTFRALL)),
    OBSIDIAN(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.OBSIDIAN_INGOT)),
    REDSTONE(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.REDSTONE_INGOT)),
    MITHRIL(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.MITHRIL_INGOT)),
    ARCANUM(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.ARCANUM_INGOT)),
    SILVER(5, 1800, 7.0F, 7.0F, 25,
            () -> Ingredient.ofItems(ModItems.SILVER_INGOT));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    private ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage,
                            int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
