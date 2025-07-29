package com.cerbon.myths_of_the_sea.entity.custom.hippocampus;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;

public class HippocampusEntityRenderer extends DynamicGeoEntityRenderer<HippocampusEntity> {

    public HippocampusEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HippocampusEntityModel());
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, HippocampusEntity hippocampus, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        if (!isReRender && (widthScale != 1.0F || heightScale != 1.0F)) {
            poseStack.scale(widthScale, heightScale, widthScale);
        }

        float normal = 1F;
        if (hippocampus.isBaby()) {
            normal *= 0.5F;
        }

        poseStack.scale(normal, normal, normal);
    }

    @Override
    protected @Nullable ResourceLocation getTextureOverrideForBone(GeoBone bone, HippocampusEntity animatable, float partialTick) {
        if((bone.getName().equals("saddle") || bone.getName().equals("headpiece2") || bone.getName().equals("headpiece"))){
            return animatable.isSaddled()? this.model.getTextureResource(animatable) : new ResourceLocation(MythsOfTheSea.MOD_ID, "textures/entity/empty.png");
        }

        if(bone.getName().equals("right_rein") || bone.getName().equals("left_rein")){
            return animatable.isSaddled() && animatable.isVehicle()? this.model.getTextureResource(animatable) : new ResourceLocation(MythsOfTheSea.MOD_ID, "textures/entity/empty.png");
        }

        return null;
    }

    @Override
    protected void applyRotations(HippocampusEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        if(animatable.isInWater() || animatable.isVehicle())
        {
            float yaw = Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot());
            poseStack.mulPose(Axis.YP.rotationDegrees(180f - yaw));

            float pitch = Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());
            poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
        } else {
            super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        }
    }



    @Override
    public int getPackedOverlay(HippocampusEntity entity, float u, float partialTick) {
        return animatable != null && animatable.isDeadOrDying() ? OverlayTexture.NO_OVERLAY : super.getPackedOverlay(animatable, u, partialTick);
    }

    @Override
    protected float getDeathMaxRotation(HippocampusEntity animatable) {
        return 0;
    }
}
