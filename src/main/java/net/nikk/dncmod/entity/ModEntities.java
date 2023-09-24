package net.nikk.dncmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.entity.custom.GoblinEntity;
import net.nikk.dncmod.entity.custom.SpellEntity;

public class ModEntities {
    public static final EntityType<GoblinEntity> GOBLIN = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(DNCMod.MOD_ID, "goblin"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GoblinEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 1.5f)).build());
    public static final EntityType<SpellEntity> SPELL = Registry.register(
            Registries.ENTITY_TYPE,new Identifier(DNCMod.MOD_ID,"spell"),
            FabricEntityTypeBuilder.<SpellEntity>create(SpawnGroup.MISC, SpellEntity::new)
            .dimensions(EntityDimensions.changing(1.0f,1.0f)).build());
    public static void registerModEntities() {
        DNCMod.LOGGER.debug("[Dungeons & Crafting] Registering Mod Entities");
    }
}
