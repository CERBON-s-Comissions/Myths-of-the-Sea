package com.cerbon.myths_of_the_sea.item.custom.abaia_tail;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AbaiaTailModel extends GeoModel<BaseAbaiaTailItem> {
    @Override
    public ResourceLocation getModelResource(BaseAbaiaTailItem animatable) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "geo/abaia_tail.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BaseAbaiaTailItem animatable) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "textures/item/abaia_tail.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BaseAbaiaTailItem animatable) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "animations/bunyip_claw.animation.json");
    }
}
