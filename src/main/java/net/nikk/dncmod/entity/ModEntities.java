package net.nikk.dncmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.entity.custom.GoblinEntity;
import net.nikk.dncmod.entity.custom.SpellEntity;

public class ModEntities {
    public static final EntityType<GoblinEntity> GOBLIN = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(DNCMod.MOD_ID, "goblin"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GoblinEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 1.5f)).build());
    public static final EntityType<SpellEntity> SPELL = Registry.register(
            Registry.ENTITY_TYPE,new Identifier(DNCMod.MOD_ID,"spell"),
            FabricEntityTypeBuilder.<SpellEntity>create(SpawnGroup.MISC, SpellEntity::new)
            .dimensions(EntityDimensions.changing(3.0f,3.0f)).build());
    public static void registerModEntities() {
        DNCMod.LOGGER.debug("[Dungeons & Crafting] Registering Mod Entities");
    }
}
