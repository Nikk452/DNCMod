package net.nikk.dncmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.block.ModBlocks;

public class ModItemGroup {
    public static ItemGroup DNC_TAB_ITEMS;
    public static ItemGroup DNC_TAB_BLOCKS;
    public static void registerItemGroup() {
        DNC_TAB_ITEMS = FabricItemGroup.builder(
                        new Identifier(DNCMod.MOD_ID, "dnctab_items")).displayName(Text.literal("DNC Item Group"))
                .icon(() -> new ItemStack(ModItems.ASTRAL_CRYSTAL)).build();
        DNC_TAB_BLOCKS = FabricItemGroup.builder(
                        new Identifier(DNCMod.MOD_ID, "dnctab_blocks")).displayName(Text.literal("DNC Block Group"))
                .icon(() -> new ItemStack(ModBlocks.PLATINUM_ORE)).build();
    }
}
