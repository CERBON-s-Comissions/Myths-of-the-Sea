package com.cerbon.myths_of_the_sea.entity.custom.abaia;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AbaiaEntityRenderer extends GeoEntityRenderer<AbaiaEntity> {

    public AbaiaEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AbaiaEntityModel());
    }

    @Override
    protected void applyRotations(AbaiaEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        float yaw = Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(180f - yaw));

        float pitch = Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
    }

    @Override
    public int getPackedOverlay(AbaiaEntity animatable, float u, float partialTick) {
        return animatable != null && animatable.isDeadOrDying() ? OverlayTexture.NO_OVERLAY : super.getPackedOverlay(animatable, u, partialTick);
    }

    @Override
    protected float getDeathMaxRotation(AbaiaEntity animatable) {
        return 0F;
    }
}
