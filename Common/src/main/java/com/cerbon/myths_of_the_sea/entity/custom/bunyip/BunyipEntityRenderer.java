package com.cerbon.myths_of_the_sea.entity.custom.bunyip;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BunyipEntityRenderer extends GeoEntityRenderer<BunyipEntity> {

    public BunyipEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BunyipEntityModel());
    }

    @Override
    protected void applyRotations(BunyipEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        if(animatable.isInWater())
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
    public int getPackedOverlay(BunyipEntity entity, float u, float partialTick) {
        return animatable != null && animatable.isDeadOrDying() ? OverlayTexture.NO_OVERLAY : super.getPackedOverlay(animatable, u, partialTick);
    }

    @Override
    protected float getDeathMaxRotation(BunyipEntity animatable) {
        return 0;
    }

    @Override
    public float getMotionAnimThreshold(BunyipEntity animatable) {
        if(animatable.isInWater())
            return 0.0015f;
        return 0.015f;
    }
}
