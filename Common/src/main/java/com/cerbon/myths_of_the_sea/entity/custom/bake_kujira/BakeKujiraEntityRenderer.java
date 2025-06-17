package com.cerbon.myths_of_the_sea.entity.custom.bake_kujira;

import com.cerbon.myths_of_the_sea.entity.custom.util.MTSGeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BakeKujiraEntityRenderer extends MTSGeoEntityRenderer<BakeKujiraEntity> {

    public BakeKujiraEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BakeKujiraEntityModel());
    }
}
