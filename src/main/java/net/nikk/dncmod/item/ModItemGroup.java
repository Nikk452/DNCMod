package net.nikk.dncmod.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.block.ModBlocks;

public class ModItemGroup {
    public static final ItemGroup DNC_TAB_ITEMS = FabricItemGroupBuilder.build(
            new Identifier(DNCMod.MOD_ID, "dnctab_items"), () -> new ItemStack(ModItems.ASTRAL_CRYSTAL));
    public static final ItemGroup DNC_TAB_BLOCKS = FabricItemGroupBuilder.build(
            new Identifier(DNCMod.MOD_ID, "dnctab_blocks"), () -> new ItemStack(ModBlocks.PLATINUM_ORE));
}
