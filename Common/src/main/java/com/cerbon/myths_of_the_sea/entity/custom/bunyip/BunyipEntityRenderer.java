package com.cerbon.myths_of_the_sea.entity.custom.bunyip;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BunyipEntityRenderer extends GeoEntityRenderer<BunyipEntity> {

    public BunyipEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BunyipEntityModel());
    }

    @Override
    public int getPackedOverlay(BunyipEntity animatable, float u, float partialTick) {
        return animatable != null && animatable.isDeadOrDying() ? OverlayTexture.NO_OVERLAY : super.getPackedOverlay(animatable, u, partialTick);
    }

    @Override
    protected float getDeathMaxRotation(BunyipEntity animatable) {
        return 0;
    }
}
