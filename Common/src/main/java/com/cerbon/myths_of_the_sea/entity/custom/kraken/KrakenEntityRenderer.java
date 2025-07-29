package com.cerbon.myths_of_the_sea.entity.custom.kraken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KrakenEntityRenderer extends GeoEntityRenderer<KrakenEntity> {
    public KrakenEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KrakenEntityModel());
    }

    @Override
    protected void applyRotations(KrakenEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {

        if(animatable.isInWater())
        {
            float yaw =    Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot());
            float pitch =  Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());

            //To have a smooth transition from holding to normal
            float blend = animatable.vehicleRotationProgress;

            float smoothedYaw = (180f - yaw) * blend;
            float smoothedPitch = -pitch * blend;

            if(!animatable.isVehicle()){
                poseStack.mulPose(Axis.YP.rotationDegrees(smoothedYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(smoothedPitch));
            } else {
                super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
            }
        } else {
            super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        }
    }

    @Override
    public int getPackedOverlay(KrakenEntity entity, float u, float partialTick) {
        return animatable != null && animatable.isDeadOrDying() ? OverlayTexture.NO_OVERLAY : super.getPackedOverlay(animatable, u, partialTick);
    }

    @Override
    protected float getDeathMaxRotation(KrakenEntity animatable) {
        return 0;
    }

    @Override
    public float getMotionAnimThreshold(KrakenEntity animatable) {
        return 0.0015f;
    }
}
