package net.nikk.dncmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nikk.dncmod.DNCMod;

public class ModItems {
    public static final Item WHITE_IRON_CRYSTAL = registerItem("white_iron_crystal",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item TITANIUM_INGOT = registerItem("titanium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item SILVER_INGOT = registerItem("silver_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item REDSTONE_INGOT = registerItem("redstone_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item PLATINUM_INGOT = registerItem("platinum_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item OBSIDIAN_INGOT = registerItem("obsidian_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item ELECTRUM_INGOT = registerItem("electrum_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item DARK_STONE = registerItem("dark_stone",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item DARK_STEEL_INGOT = registerItem("dark_steel_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item SHADOWTFRALL = registerItem("shadowthrall",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item ADAMANTINE_INGOT = registerItem("adamantine_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item ARCANUM_INGOT = registerItem("arcanum_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    public static final Item ASTRAL_CRYSTAL = registerItem("astral_crystal",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DNCMod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DNCMod.LOGGER.debug("Registering Mod Items for " + DNCMod.MOD_ID);
    }
}
