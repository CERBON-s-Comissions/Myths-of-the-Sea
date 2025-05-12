package com.cerbon.myths_of_the_sea.item.custom.kraken_tentacle;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KrakenTentacleModel extends GeoModel<BaseKrakenTentacleItem> {

    @Override
    public ResourceLocation getModelResource(BaseKrakenTentacleItem krakenTentacleItem) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "geo/kraken_tentacle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BaseKrakenTentacleItem krakenTentacleItem) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "textures/item/kraken_tentacle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BaseKrakenTentacleItem krakenTentacleItem) {
        return new ResourceLocation(MythsOfTheSea.MOD_ID, "animations/kraken_tentacle.animation.json");
    }
}
