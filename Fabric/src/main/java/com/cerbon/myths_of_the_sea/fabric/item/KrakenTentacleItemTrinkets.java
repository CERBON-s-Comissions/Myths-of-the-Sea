package com.cerbon.myths_of_the_sea.fabric.item;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class KrakenTentacleItemTrinkets extends KrakenTentacleItem {

    public KrakenTentacleItemTrinkets(Properties properties) {
        super(properties);
        TrinketsApi.registerTrinket(this, new Trinket() {

            @Override
            public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
                var modifiers = Trinket.super.getModifiers(stack, slot, entity, uuid);
                stack.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach(modifiers::put);
                return modifiers;
            }
        });
    }
}
