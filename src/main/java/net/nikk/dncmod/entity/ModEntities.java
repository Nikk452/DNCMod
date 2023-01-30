package net.nikk.dncmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.entity.custom.GoblinEntity;

public class ModEntities {
    public static final EntityType<GoblinEntity> GOBLIN = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(DNCMod.MOD_ID, "goblin"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GoblinEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 1.5f)).build());
}
