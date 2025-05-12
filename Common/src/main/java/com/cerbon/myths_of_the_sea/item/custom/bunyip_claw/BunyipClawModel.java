package com.cerbon.myths_of_the_sea.item.custom.bunyip_claw;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BunyipClawModel extends GeoModel<BaseBunyipClawItem> {

    @Override
    public ResourceLocation getModelResource(BaseBunyipClawItem krakenTentacleItem) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "geo/bunyip_claw.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BaseBunyipClawItem krakenTentacleItem) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "textures/item/bunyip_claw.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BaseBunyipClawItem krakenTentacleItem) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "animations/bunyip_claw.animation.json");
    }
}
