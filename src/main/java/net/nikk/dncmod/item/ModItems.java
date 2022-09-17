package net.nikk.dncmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nikk.dncmod.DNCMod;

public class ModItems {
    public static final Item WHITE_IRON_CRYSTAL = registerItem("white_iron_crystal",
            new Item(new FabricItemSettings().group(ModItemGroup.DNC_TAB)));
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DNCMod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DNCMod.LOGGER.debug("Registering Mod Items for " + DNCMod.MOD_ID);
    }
}
