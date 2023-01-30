package net.nikk.dncmod.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.nikk.dncmod.entity.ModEntities;

public class ModEntitySpawn {
    public static void AddEntitySpawn(){
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES), SpawnGroup.MONSTER, ModEntities.GOBLIN,100,2,5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST), SpawnGroup.MONSTER, ModEntities.GOBLIN,250,2,10);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST), SpawnGroup.MONSTER, ModEntities.GOBLIN,150,2,7);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS), SpawnGroup.MONSTER, ModEntities.GOBLIN,20,1,4);
        SpawnRestriction.register(ModEntities.GOBLIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canMobSpawn);
    }
}
