package net.nikk.dncmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.block.ModBlocks;

public class ModItemGroup {
    public static ItemGroup DNC_TAB_ITEMS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(DNCMod.MOD_ID, "dnctab_items"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.dnctab_items"))
                    .icon(() -> new ItemStack(ModItems.ASTRAL_CRYSTAL)).entries((displayContext, entries) -> {
                        entries.add(ModItems.WHITE_IRON_CRYSTAL);
                        entries.add(ModItems.TITANIUM_INGOT);
                        entries.add(ModItems.TIN_INGOT);
                        entries.add(ModItems.SILVER_INGOT);
                        entries.add(ModItems.REDSTONE_INGOT);
                        entries.add(ModItems.PLATINUM_INGOT);
                        entries.add(ModItems.OBSIDIAN_INGOT);
                        entries.add(ModItems.LEAD_INGOT);
                        entries.add(ModItems.MITHRIL_INGOT);
                        entries.add(ModItems.ELECTRUM_INGOT);
                        entries.add(ModItems.DARK_STONE);
                        entries.add(ModItems.DARK_STEEL_INGOT);
                        entries.add(ModItems.SHADOWTFRALL);
                        entries.add(ModItems.BRONZE_INGOT);
                        entries.add(ModItems.ADAMANTINE_INGOT);
                        entries.add(ModItems.ARCANUM_INGOT);
                        entries.add(ModItems.ASTRAL_CRYSTAL);
                        entries.add(ModItems.RAW_ADAMANTINE);
                        entries.add(ModItems.RAW_TITANIUM);
                        entries.add(ModItems.RAW_TIN);
                        entries.add(ModItems.RAW_SILVER);
                        entries.add(ModItems.RAW_BRONZE);
                        entries.add(ModItems.RAW_PLATINUM);
                        entries.add(ModItems.RAW_OBSIDIAN);
                        entries.add(ModItems.RAW_LEAD);
                        entries.add(ModItems.RAW_MITHRIL);
                        entries.add(ModItems.RAW_ELECTRUM);
                        entries.add(ModItems.LONG_ARROW);
                        entries.add(ModItems.CLUB);
                        entries.add(ModItems.QUARTERSTAFF);
                        entries.add(ModItems.DAGGER);
                        entries.add(ModItems.SHORT_SWORD);
                        entries.add(ModItems.GREAT_SWORD);
                        entries.add(ModItems.SPEAR);
                        entries.add(ModItems.SHORT_BOW);
                        entries.add(ModItems.LONG_BOW);
                        entries.add(ModItems.SLING);
                        entries.add(ModItems.DART);
                        entries.add(ModItems.ARRIVAL_JACKET);
                        entries.add(ModItems.ARRIVAL_PANTS);
                        entries.add(ModItems.ARRIVAL_BOOTS);
                        entries.add(ModItems.SPELL_BOOK);
                        entries.add(ModItems.PARCHMENT);
                        entries.add(ModItems.SCROLL);
                    }).build());
    public static ItemGroup DNC_TAB_BLOCKS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(DNCMod.MOD_ID, "dnctab_blocks"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.dnctab_blocks"))
                    .icon(() -> new ItemStack(ModBlocks.PLATINUM_ORE)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.PLATINUM_ORE);
                    }).build());
    public static void registerItemGroup() {
        DNCMod.LOGGER.info("registering item groups");
    }
}
