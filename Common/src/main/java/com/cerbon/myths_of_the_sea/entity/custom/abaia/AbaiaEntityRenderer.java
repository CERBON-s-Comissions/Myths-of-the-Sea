package com.cerbon.myths_of_the_sea.entity.custom.abaia;

import com.cerbon.myths_of_the_sea.entity.custom.util.MTSGeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AbaiaEntityRenderer extends MTSGeoEntityRenderer<AbaiaEntity> {

    public AbaiaEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AbaiaEntityModel());
    }
}
