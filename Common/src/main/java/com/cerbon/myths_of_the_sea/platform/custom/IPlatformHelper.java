package com.cerbon.myths_of_the_sea.platform.custom;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface IPlatformHelper {

    Supplier<Item> krakenTentacleItem(Item.Properties properties);

    Supplier<Item> bunyipClawItem(float attackDamage, float attackSpeed, Item.Properties properties);

    Supplier<Item> abaiaTailItem(Item.Properties properties);

    Attribute blockReachAttribute();

    Attribute entityReachAttribute();
}
