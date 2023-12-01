package net.nikk.dncmod.entity.layer;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;

public class ModModelLayers {
    public static final EntityModelLayer GOBLIN =
            new EntityModelLayer(new Identifier(DNCMod.MOD_ID, "goblin"), "main");

}
