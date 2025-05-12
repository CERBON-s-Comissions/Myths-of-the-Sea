package com.cerbon.myths_of_the_sea.item.custom.kraken_tentacle;

import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class BaseKrakenTentacleItem extends Item implements GeoItem {
    private AnimatableInstanceCache animatableCache = GeckoLibUtil.createInstanceCache(this);

    public BaseKrakenTentacleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableCache;
    }
}
