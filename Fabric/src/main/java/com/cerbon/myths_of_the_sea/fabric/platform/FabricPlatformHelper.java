package com.cerbon.myths_of_the_sea.fabric.platform;

import com.cerbon.myths_of_the_sea.fabric.item.BunyipClawItem;
import com.cerbon.myths_of_the_sea.fabric.item.KrakenTentacleItem;
import com.cerbon.myths_of_the_sea.fabric.item.KrakenTentacleItemTrinkets;
import com.cerbon.myths_of_the_sea.integration.MTSIntegrations;
import com.cerbon.myths_of_the_sea.platform.custom.IPlatformHelper;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
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
    public Attribute blockReachAttribute() {
        return ReachEntityAttributes.REACH;
    }

    @Override
    public Attribute entityReachAttribute() {
        return ReachEntityAttributes.ATTACK_RANGE;
    }

    @Override
    public ItemStack getItemFromCuriosSlot(LivingEntity livingEntity, Item item) {
        if (MTSIntegrations.isTrinketsLoaded) {
            return TrinketsApi.getTrinketComponent(livingEntity).map(component -> {
                List<Tuple<SlotReference, ItemStack>> res = component.getEquipped(itemStack -> itemStack.is(item));
                return !res.isEmpty() ? res.get(0).getB() : ItemStack.EMPTY;
            }).orElse(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }
}
