package com.cerbon.myths_of_the_sea.entity.custom.leviathan;

import com.cerbon.myths_of_the_sea.entity.custom.util.MTSGeoEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class LeviathanEntityRenderer extends MTSGeoEntityRenderer<LeviathanEntity> {
    public LeviathanEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LeviathanEntityModel());
    }

    @Override
    protected void applyRotations(LeviathanEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        if(entity.isInWater())
        {
            float yaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
            poseStack.mulPose(Axis.YP.rotationDegrees(180f - yaw));

            float pitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
            poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
        } else {
            super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick);
        }
    }
}
