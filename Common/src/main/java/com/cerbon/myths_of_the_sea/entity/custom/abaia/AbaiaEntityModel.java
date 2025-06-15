package com.cerbon.myths_of_the_sea.entity.custom.abaia;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AbaiaEntityModel extends GeoModel<AbaiaEntity> {

    @Override
    public ResourceLocation getModelResource(AbaiaEntity animatable) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "geo/abaia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AbaiaEntity animatable) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "textures/entity/abaia.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AbaiaEntity animatable) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "animations/abaia.animation.json");
    }
}
