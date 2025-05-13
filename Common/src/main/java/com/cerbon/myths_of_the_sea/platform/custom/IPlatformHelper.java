package com.cerbon.myths_of_the_sea.platform.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public interface IPlatformHelper {

    Supplier<Item> krakenTentacleItem(Item.Properties properties);

    Supplier<Item> bunyipClawItem(float attackDamage, float attackSpeed, Item.Properties properties);

    Attribute blockReachAttribute();

    Attribute entityReachAttribute();

    ItemStack getItemFromCuriosSlot(LivingEntity livingEntity, Item item);
}
