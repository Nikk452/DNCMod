package net.nikk.dncmod.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.nikk.dncmod.entity.custom.GoblinEntity;

public class GoblinEntityModel <T extends GoblinEntity> extends SinglePartEntityModel<T> {
    ModelPart goblin;
    public GoblinEntityModel(ModelPart part) {
        this.goblin = part.getChild("body");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 10).cuboid(-3.6F, -3.6F, -1.8F, 7.0F, 10.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 41).cuboid(-3.6F, 2.4F, -3.4875F, 7.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.5F, 0.0F));

        ModelPartData bodycloths = body.addChild("bodycloths", ModelPartBuilder.create().uv(11, 57).cuboid(-3.75F, -1.8375F, -1.9125F, 7.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 9.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.6F, -9.0F, -1.8F, 7.0F, 7.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.8F, -1.8F));

        ModelPartData accesories = head.addChild("accesories", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.6F, 0.0F));

        ModelPartData earL_r1 = accesories.addChild("earL_r1", ModelPartBuilder.create().uv(10, 36).cuboid(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.8F, 3.6F, 0.4363F, 1.2217F, 0.0F));

        ModelPartData nose_r1 = accesories.addChild("nose_r1", ModelPartBuilder.create().uv(10, 41).cuboid(-0.9F, 0.0F, -6.3F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.8F, 0.0F, 0.0873F, 0.0F, 0.0F));

        ModelPartData earR = accesories.addChild("earR", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData earR_r1 = earR.addChild("earR_r1", ModelPartBuilder.create().uv(0, 36).cuboid(-2.7F, 0.0F, 0.0F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.8F, 3.6F, 0.4363F, -1.2217F, 0.0F));

        ModelPartData hexadecagon = earR.addChild("hexadecagon", ModelPartBuilder.create().uv(3, 57).cuboid(-0.1701F, -0.1692F, -0.8505F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, -0.1692F, 0.5103F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, 0.5103F, -0.1692F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, -0.8505F, -0.1692F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, -2.1F, 3.3F, 0.0F, -0.2618F, 0.0F));

        ModelPartData hexadecagon_r1 = hexadecagon.addChild("hexadecagon_r1", ModelPartBuilder.create().uv(3, 57).cuboid(-0.1701F, -0.8505F, -0.1692F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, 0.5103F, -0.1692F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, -0.1692F, 0.5103F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, -0.1692F, -0.8505F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        ModelPartData hexadecagon_r2 = hexadecagon.addChild("hexadecagon_r2", ModelPartBuilder.create().uv(3, 57).cuboid(-0.1701F, -0.8505F, -0.1692F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, 0.5103F, -0.1692F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, -0.1692F, 0.5103F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, -0.1692F, -0.8505F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        ModelPartData hexadecagon_r3 = hexadecagon.addChild("hexadecagon_r3", ModelPartBuilder.create().uv(3, 57).cuboid(-0.1701F, -0.1692F, 0.5103F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, -0.1692F, -0.8505F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData hexadecagon_r4 = hexadecagon.addChild("hexadecagon_r4", ModelPartBuilder.create().uv(3, 57).cuboid(-0.1701F, -0.1692F, 0.5103F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 57).cuboid(-0.1701F, -0.1692F, -0.8505F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData armR = body.addChild("armR", ModelPartBuilder.create().uv(0, 19).cuboid(-3.45F, -0.9F, -1.65F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.75F, -2.7F, 0.0F));

        ModelPartData armL = body.addChild("armL", ModelPartBuilder.create().uv(12, 10).cuboid(-0.15F, -0.9F, -1.65F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(3.75F, -2.7F, 0.0F));

        ModelPartData legR = body.addChild("legR", ModelPartBuilder.create().uv(8, 28).cuboid(-1.35F, -0.6F, -1.8F, 3.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.25F, 7.8F, 0.0F));

        ModelPartData legL = body.addChild("legL", ModelPartBuilder.create().uv(0, 28).cuboid(-2.25F, -0.6F, -1.8F, 3.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.25F, 7.8F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public ModelPart getPart() {
        return goblin;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        //super.setAngles(entity);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        goblin.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
