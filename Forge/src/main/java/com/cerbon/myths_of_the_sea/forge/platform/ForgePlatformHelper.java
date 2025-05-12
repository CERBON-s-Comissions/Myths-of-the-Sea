package com.cerbon.myths_of_the_sea.forge.platform;

import com.cerbon.myths_of_the_sea.forge.item.KrakenTentacleItem;
import com.cerbon.myths_of_the_sea.platform.custom.IPlatformHelper;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeMod;

import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public Supplier<Item> krakenTentacleItem(Item.Properties properties) {
        return () -> new KrakenTentacleItem(properties);
    }

    @Override
    public Attribute blockReachAttribute() {
        return ForgeMod.BLOCK_REACH.get();
    }

    @Override
    public Attribute entityReachAttribute() {
        return ForgeMod.ENTITY_REACH.get();
    }
}
