package net.nikk.dncmod.entity.client;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.entity.custom.GoblinEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GoblinModel extends AnimatedGeoModel<GoblinEntity> {
    @Override
    public Identifier getModelResource(GoblinEntity object) {
        return new Identifier(DNCMod.MOD_ID, "geo/goblin.geo.json");
    }
    @Override
    public Identifier getTextureResource(GoblinEntity object) {
        return GoblinRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(GoblinEntity animatable) {
        return new Identifier(DNCMod.MOD_ID, "animations/goblin.animation.json");
    }
}