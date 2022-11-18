package net.nikk.dncmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.item.custom.LongBowItem;

public class ModItems {
    public static final Item WHITE_IRON_CRYSTAL = registerItem("white_iron_crystal",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item TITANIUM_INGOT = registerItem("titanium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item SILVER_INGOT = registerItem("silver_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item REDSTONE_INGOT = registerItem("redstone_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item PLATINUM_INGOT = registerItem("platinum_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item OBSIDIAN_INGOT = registerItem("obsidian_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item ELECTRUM_INGOT = registerItem("electrum_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item DARK_STONE = registerItem("dark_stone",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item DARK_STEEL_INGOT = registerItem("dark_steel_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item SHADOWTFRALL = registerItem("shadowthrall",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item ADAMANTINE_INGOT = registerItem("adamantine_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item ARCANUM_INGOT = registerItem("arcanum_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item ASTRAL_CRYSTAL = registerItem("astral_crystal",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));

    public static final Item RAW_ADAMANTINE = registerItem("raw_adamantine",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_TITANIUM = registerItem("raw_titanium",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_TIN = registerItem("raw_tin",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_SILVER = registerItem("raw_silver",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_BRONZE = registerItem("raw_bronze",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_PLATINUM = registerItem("raw_platinum",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_OBSIDIAN = registerItem("raw_obsidian",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_LEAD = registerItem("raw_lead",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_MITHRIL = registerItem("raw_mithril",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item RAW_ELECTRUM = registerItem("raw_electrum",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item LONG_ARROW = registerItem("long_arrow",
            new ArrowItem(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));

    public static final Item CLUB = registerItem("club",
            new SwordItem(ToolMaterials.WOOD,4,5,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item QUARTERSTAFF = registerItem("quarterstaff",
            new SwordItem(ToolMaterials.WOOD,8,2,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item DAGGER = registerItem("dagger",
            new SwordItem(ToolMaterials.IRON,4,3,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item SHORT_SWORD = registerItem("short_sword",
            new SwordItem(ToolMaterials.IRON,6,3,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item GREAT_SWORD = registerItem("great_sword",
            new SwordItem(ToolMaterials.IRON,8,3,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item SPEAR = registerItem("spear",
            new SwordItem(ToolMaterials.IRON,6,3,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item SHORT_BOW = registerItem("short_bow",
            new BowItem(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS).maxCount(1)));
    public static final Item LONG_BOW = registerItem("long_bow",
            new LongBowItem(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS).maxCount(1)));
    public static final Item SLING = registerItem("sling",
            new BowItem(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS).maxCount(1)));
    public static final Item DART = registerItem("dart",
            new SnowballItem(new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item ARRIVAL_JACKET = registerItem("arrival_jacket",
            new ArmorItem(ModArmorMaterials.ARRIVAL, EquipmentSlot.CHEST ,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item ARRIVAL_PANTS = registerItem("arrival_pants",
            new ArmorItem(ModArmorMaterials.ARRIVAL, EquipmentSlot.LEGS ,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    public static final Item ARRIVAL_BOOTS = registerItem("arrival_boots",
            new ArmorItem(ModArmorMaterials.ARRIVAL, EquipmentSlot.FEET ,new FabricItemSettings().group(ModItemGroup.DNC_TAB_ITEMS)));
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DNCMod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DNCMod.LOGGER.debug("Registering Mod Items for " + DNCMod.MOD_ID);
    }
}
