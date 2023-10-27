package net.nikk.dncmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.entity.ModEntities;
import net.nikk.dncmod.item.custom.LongBowItem;
import net.nikk.dncmod.item.custom.ScrollItem;
import net.nikk.dncmod.item.custom.SpellBookItem;
@SuppressWarnings("unused")
public class ModItems {
    public static final Item WHITE_IRON_CRYSTAL = registerItem("white_iron_crystal",
            new Item(new FabricItemSettings()));
    public static final Item TITANIUM_INGOT = registerItem("titanium_ingot",
            new Item(new FabricItemSettings()));
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            new Item(new FabricItemSettings()));
    public static final Item SILVER_INGOT = registerItem("silver_ingot",
            new Item(new FabricItemSettings()));
    public static final Item REDSTONE_INGOT = registerItem("redstone_ingot",
            new Item(new FabricItemSettings()));
    public static final Item PLATINUM_INGOT = registerItem("platinum_ingot",
            new Item(new FabricItemSettings()));
    public static final Item OBSIDIAN_INGOT = registerItem("obsidian_ingot",
            new Item(new FabricItemSettings()));
    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            new Item(new FabricItemSettings()));
    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ELECTRUM_INGOT = registerItem("electrum_ingot",
            new Item(new FabricItemSettings()));
    public static final Item DARK_STONE = registerItem("dark_stone",
            new Item(new FabricItemSettings()));
    public static final Item DARK_STEEL_INGOT = registerItem("dark_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item SHADOWTFRALL = registerItem("shadowthrall",
            new Item(new FabricItemSettings()));
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ADAMANTINE_INGOT = registerItem("adamantine_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ARCANUM_INGOT = registerItem("arcanum_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ASTRAL_CRYSTAL = registerItem("astral_crystal",
            new Item(new FabricItemSettings()));

    public static final Item RAW_ADAMANTINE = registerItem("raw_adamantine",
            new Item(new FabricItemSettings()));
    public static final Item RAW_TITANIUM = registerItem("raw_titanium",
            new Item(new FabricItemSettings()));
    public static final Item RAW_TIN = registerItem("raw_tin",
            new Item(new FabricItemSettings()));
    public static final Item RAW_SILVER = registerItem("raw_silver",
            new Item(new FabricItemSettings()));
    public static final Item RAW_BRONZE = registerItem("raw_bronze",
            new Item(new FabricItemSettings()));
    public static final Item RAW_PLATINUM = registerItem("raw_platinum",
            new Item(new FabricItemSettings()));
    public static final Item RAW_OBSIDIAN = registerItem("raw_obsidian",
            new Item(new FabricItemSettings()));
    public static final Item RAW_LEAD = registerItem("raw_lead",
            new Item(new FabricItemSettings()));
    public static final Item RAW_MITHRIL = registerItem("raw_mithril",
            new Item(new FabricItemSettings()));
    public static final Item RAW_ELECTRUM = registerItem("raw_electrum",
            new Item(new FabricItemSettings()));
    public static final Item LONG_ARROW = registerItem("long_arrow",
            new ArrowItem(new FabricItemSettings()));

    public static final Item CLUB = registerItem("club",
            new SwordItem(ToolMaterials.WOOD,4,1,new FabricItemSettings()));
    public static final Item QUARTERSTAFF = registerItem("quarterstaff",
            new SwordItem(ToolMaterials.WOOD,8,2,new FabricItemSettings()));
    public static final Item DAGGER = registerItem("dagger",
            new SwordItem(ToolMaterials.IRON,2,2,new FabricItemSettings()));
    public static final Item SHORT_SWORD = registerItem("short_sword",
            new SwordItem(ToolMaterials.IRON,4,3,new FabricItemSettings()));
    public static final Item GREAT_SWORD = registerItem("great_sword",
            new SwordItem(ToolMaterials.IRON,6,3,new FabricItemSettings()));
    public static final Item SPEAR = registerItem("spear",
            new SwordItem(ToolMaterials.IRON,4,3,new FabricItemSettings()));
    public static final Item SHORT_BOW = registerItem("short_bow",
            new BowItem(new FabricItemSettings().maxCount(1)));
    public static final Item LONG_BOW = registerItem("long_bow",
            new LongBowItem(new FabricItemSettings().maxCount(1)));
    public static final Item SLING = registerItem("sling",
            new BowItem(new FabricItemSettings().maxCount(1)));
    public static final Item DART = registerItem("dart",
            new SnowballItem(new FabricItemSettings()));
    public static final Item GOBLIN_SPAWN_EGG = registerItem("goblin_spawn_egg",
            new SpawnEggItem(ModEntities.GOBLIN,0x799C65,0x315234,new FabricItemSettings()));
    public static final Item ARRIVAL_JACKET = registerItem("arrival_jacket",
            new ArmorItem(ModArmorMaterials.ARRIVAL, ArmorItem.Type.CHESTPLATE ,new FabricItemSettings()));
    public static final Item ARRIVAL_PANTS = registerItem("arrival_pants",
            new ArmorItem(ModArmorMaterials.ARRIVAL, ArmorItem.Type.LEGGINGS ,new FabricItemSettings()));
    public static final Item ARRIVAL_BOOTS = registerItem("arrival_boots",
            new ArmorItem(ModArmorMaterials.ARRIVAL, ArmorItem.Type.BOOTS ,new FabricItemSettings()));
    public static final Item SPELL_BOOK = registerItem("spell_book",
            new SpellBookItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item PARCHMENT = registerItem("parchment",
            new Item(new FabricItemSettings()));
    public static final Item SCROLL = registerItem("scroll",
            new ScrollItem(new FabricItemSettings().rarity(Rarity.COMMON)));
    public static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }
    public static void addItemsToItemGroups() {
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, WHITE_IRON_CRYSTAL);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, TITANIUM_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, TIN_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, SILVER_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, REDSTONE_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, PLATINUM_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, OBSIDIAN_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, LEAD_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, MITHRIL_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, ELECTRUM_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, DARK_STONE);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, DARK_STEEL_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, SHADOWTFRALL);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, BRONZE_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, ADAMANTINE_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, ARCANUM_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, ASTRAL_CRYSTAL);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_ADAMANTINE);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_TITANIUM);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_TIN);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_SILVER);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_BRONZE);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_PLATINUM);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_OBSIDIAN);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_LEAD);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, MITHRIL_INGOT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, RAW_ELECTRUM);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, LONG_ARROW);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, CLUB);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, QUARTERSTAFF);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, DAGGER);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, SHORT_SWORD);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, GREAT_SWORD);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, SPEAR);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, SHORT_BOW);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, LONG_BOW);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, SLING);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, DART);
        addToItemGroup(ItemGroups.SPAWN_EGGS, GOBLIN_SPAWN_EGG);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, ARRIVAL_JACKET);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, ARRIVAL_PANTS);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, ARRIVAL_BOOTS);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, SPELL_BOOK);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, PARCHMENT);
        addToItemGroup(ModItemGroup.DNC_TAB_ITEMS, SCROLL);
    }
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DNCMod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DNCMod.LOGGER.debug("[Dungeons & Crafting] Registering Mod Items");
        addItemsToItemGroups();
    }
}
