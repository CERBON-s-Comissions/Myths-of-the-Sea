package com.cerbon.myths_of_the_sea.fabric.platform;

import com.cerbon.myths_of_the_sea.fabric.item.AbaiaTailItem;
import com.cerbon.myths_of_the_sea.fabric.item.BunyipClawItem;
import com.cerbon.myths_of_the_sea.fabric.item.KrakenTentacleItem;
import com.cerbon.myths_of_the_sea.fabric.item.KrakenTentacleItemTrinkets;
import com.cerbon.myths_of_the_sea.integration.MTSIntegrations;
import com.cerbon.myths_of_the_sea.platform.custom.IPlatformHelper;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public Supplier<Item> krakenTentacleItem(Item.Properties properties) {
        return MTSIntegrations.isTrinketsLoaded ? () -> new KrakenTentacleItemTrinkets(properties) : () -> new KrakenTentacleItem(properties);
    }

    @Override
    public Supplier<Item> bunyipClawItem(float attackDamage, float attackSpeed, Item.Properties properties) {
        return () -> new BunyipClawItem(attackDamage, attackSpeed, properties);
    }

    @Override
    public Supplier<Item> abaiaTailItem(Item.Properties properties) {
        return () -> new AbaiaTailItem(properties);
    }

    @Override
    public Attribute blockReachAttribute() {
        return ReachEntityAttributes.REACH;
    }

    @Override
    public Attribute entityReachAttribute() {
        return ReachEntityAttributes.ATTACK_RANGE;
    }
}
