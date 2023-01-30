package net.nikk.dncmod.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.entity.custom.GoblinEntity;
import net.nikk.dncmod.entity.variant.GoblinVariant;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class GoblinRenderer extends GeoEntityRenderer<GoblinEntity> {
    public static final Map<GoblinVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GoblinVariant.class), (map) -> {
                map.put(GoblinVariant.DEFAULT,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin0.png"));
                map.put(GoblinVariant.BELLY_1,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin1.png"));
                map.put(GoblinVariant.BELLY_2,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin2.png"));
                map.put(GoblinVariant.BELLY_3,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin3.png"));
                map.put(GoblinVariant.BELLY_4,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin4.png"));
                map.put(GoblinVariant.BELLY_5,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin5.png"));
                map.put(GoblinVariant.BELLY_6,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin6.png"));
                map.put(GoblinVariant.DEFAULT_1,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin7.png"));
                map.put(GoblinVariant.DEFAULT_2,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin8.png"));
                map.put(GoblinVariant.DEFAULT_3,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin9.png"));
                map.put(GoblinVariant.DEFAULT_4,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin10.png"));
                map.put(GoblinVariant.DEFAULT_5,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin11.png"));
                map.put(GoblinVariant.DEFAULT_6,
                        new Identifier(DNCMod.MOD_ID, "textures/entity/goblin12.png"));
            });
    public GoblinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GoblinModel());
        this.shadowRadius = 0.4f;
    }
    @Override
    public Identifier getTextureResource(GoblinEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderLayer getRenderType(GoblinEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        stack.scale(1.2f, 1.2f, 1.2f);

        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
