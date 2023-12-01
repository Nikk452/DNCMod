package net.nikk.dncmod.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.GhastEntityModel;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.entity.custom.GoblinEntity;
import net.nikk.dncmod.entity.layer.ModModelLayers;

public class GoblinEntityRenderer extends MobEntityRenderer<GoblinEntity, GoblinEntityModel<GoblinEntity>> {
    public GoblinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinEntityModel<>(context.getPart(ModModelLayers.GOBLIN)), 1.0f);
    }

    @Override
    public Identifier getTexture(GoblinEntity entity) {
        return new Identifier(DNCMod.MOD_ID, "textures/entity/goblin0.png");
    }
}
