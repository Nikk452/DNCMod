package net.nikk.dncmod.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
@SuppressWarnings("unused")
public class ModTags {
    public static class Blocks {

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(Registries.BLOCK.getKey(), new Identifier(DNCMod.MOD_ID, name));
        }

        private static TagKey<Block> createCommonTag(String name) {
            return TagKey.of(Registries.BLOCK.getKey(), new Identifier("c", name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(Registries.ITEM.getKey(), new Identifier(DNCMod.MOD_ID, name));
        }

        private static TagKey<Item> createCommonTag(String name) {
            return TagKey.of(Registries.ITEM.getKey(), new Identifier("c", name));
        }
    }
}
