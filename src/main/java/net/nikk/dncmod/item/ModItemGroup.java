package net.nikk.dncmod.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;

public class ModItemGroup {
    public static final ItemGroup DNC_TAB = FabricItemGroupBuilder.build(
            new Identifier(DNCMod.MOD_ID, "dnctab"), () -> new ItemStack(ModItems.WHITE_IRON_CRYSTAL));
}
