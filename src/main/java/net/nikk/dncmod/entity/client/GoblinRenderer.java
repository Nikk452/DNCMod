package net.nikk.dncmod.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3f;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.entity.custom.GoblinEntity;
import net.nikk.dncmod.entity.variant.GoblinVariant;
import org.jetbrains.annotations.Nullable;
import software.bernie.example.client.DefaultBipedBoneIdents;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

import java.util.Map;

public class GoblinRenderer extends ExtendedGeoEntityRenderer<GoblinEntity> {
    protected ItemStack mainHandItem, offHandItem, helmetItem, chestplateItem, leggingsItem, bootsItem;
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
    public void renderEarly(GoblinEntity animatable, MatrixStack poseStack, float partialTick, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);

        this.mainHandItem = animatable.getEquippedStack(EquipmentSlot.MAINHAND);
        this.offHandItem = animatable.getEquippedStack(EquipmentSlot.OFFHAND);
        this.helmetItem = animatable.getEquippedStack(EquipmentSlot.HEAD);
        this.chestplateItem = animatable.getEquippedStack(EquipmentSlot.CHEST);
        this.leggingsItem = animatable.getEquippedStack(EquipmentSlot.LEGS);
        this.bootsItem = animatable.getEquippedStack(EquipmentSlot.FEET);
    }

    @Override
    public Identifier getTextureResource(GoblinEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderLayer getRenderType(GoblinEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

    @Override
    public float getWidthScale(GoblinEntity animatable) {
        return super.getWidthScale(animatable) *1.2f;
    }

    @Override
    public float getHeightScale(GoblinEntity entity) {
        return super.getHeightScale(entity)*1.2f;
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return bone.getName().startsWith("armor");
    }

    @Nullable
    @Override
    protected Identifier getTextureForBone(String boneName, GoblinEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, GoblinEntity currentEntity) {
        return switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT -> currentEntity.isLeftHanded() ? mainHandItem : offHandItem;
            case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT -> currentEntity.isLeftHanded() ? offHandItem : mainHandItem;
            default -> null;
        };
    }

    @Override
    protected ModelTransformation.Mode getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT, DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT -> ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND; // Do Defaults
            default -> ModelTransformation.Mode.NONE;
        };
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, GoblinEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(MatrixStack PoseStack, ItemStack item, String boneName, GoblinEntity currentEntity, IBone bone) {
        if (item == this.mainHandItem) {
            PoseStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90f));

            if (item.getItem() instanceof ShieldItem)
                PoseStack.translate(0, 0.125, -0.25);
        }
        else if (item == this.offHandItem) {
            PoseStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90f));

            if (item.getItem() instanceof ShieldItem) {
                PoseStack.translate(0, 0.125, 0.25);
                PoseStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            }
        }
    }

    @Override
    protected void preRenderBlock(MatrixStack PoseStack, BlockState block, String boneName, GoblinEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(MatrixStack PoseStack, ItemStack item, String boneName, GoblinEntity currentEntity, IBone bone) {

    }

    @Override
    protected void postRenderBlock(MatrixStack PoseStack, BlockState block, String boneName, GoblinEntity currentEntity) {

    }

    @Nullable
    @Override
    protected ItemStack getArmorForBone(String boneName, GoblinEntity currentEntity) {
        return switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_2_IDENT -> this.bootsItem;
            case DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_2_IDENT -> this.leggingsItem;
            case DefaultBipedBoneIdents.BODY_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_ARM_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_ARM_ARMOR_BONE_IDENT -> this.chestplateItem;
            case DefaultBipedBoneIdents.HEAD_ARMOR_BONE_IDENT -> this.helmetItem;
            default -> null;
        };
    }

    @Nullable
    @Override
    protected EquipmentSlot getEquipmentSlotForArmorBone(String boneName, GoblinEntity currentEntity) {
        return switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_2_IDENT -> EquipmentSlot.FEET;
            case DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_2_IDENT -> EquipmentSlot.LEGS;
            case DefaultBipedBoneIdents.RIGHT_ARM_ARMOR_BONE_IDENT -> !currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            case DefaultBipedBoneIdents.LEFT_ARM_ARMOR_BONE_IDENT -> currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            case DefaultBipedBoneIdents.BODY_ARMOR_BONE_IDENT -> EquipmentSlot.CHEST;
            case DefaultBipedBoneIdents.HEAD_ARMOR_BONE_IDENT -> EquipmentSlot.HEAD;
            default -> null;
        };
    }

    @Nullable
    @Override
    protected ModelPart getArmorPartForBone(String name, BipedEntityModel<?> armorModel) {
        return switch (name) {
            case DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_2_IDENT -> armorModel.leftLeg;
            case DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_2_IDENT -> armorModel.rightLeg;
            case DefaultBipedBoneIdents.RIGHT_ARM_ARMOR_BONE_IDENT -> armorModel.rightArm;
            case DefaultBipedBoneIdents.LEFT_ARM_ARMOR_BONE_IDENT -> armorModel.leftArm;
            case DefaultBipedBoneIdents.BODY_ARMOR_BONE_IDENT -> armorModel.body;
            case DefaultBipedBoneIdents.HEAD_ARMOR_BONE_IDENT -> armorModel.head;
            default -> null;
        };
    }
}
