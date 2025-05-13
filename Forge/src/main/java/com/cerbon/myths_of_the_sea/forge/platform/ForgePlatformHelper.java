package com.cerbon.myths_of_the_sea.forge.platform;

import com.cerbon.myths_of_the_sea.forge.item.BunyipClawItem;
import com.cerbon.myths_of_the_sea.forge.item.KrakenTentacleItem;
import com.cerbon.myths_of_the_sea.integration.MTSIntegrations;
import com.cerbon.myths_of_the_sea.platform.custom.IPlatformHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public Supplier<Item> krakenTentacleItem(Item.Properties properties) {
        return () -> new KrakenTentacleItem(properties);
    }

    @Override
    public Supplier<Item> bunyipClawItem(float attackDamage, float attackSpeed, Item.Properties properties) {
        return () -> new BunyipClawItem(attackDamage, attackSpeed, properties);
    }

    @Override
    public Attribute blockReachAttribute() {
        return ForgeMod.BLOCK_REACH.get();
    }

    @Override
    public Attribute entityReachAttribute() {
        return ForgeMod.ENTITY_REACH.get();
    }

    @Override
    public ItemStack getItemFromCuriosSlot(LivingEntity livingEntity, Item item) {
        if (MTSIntegrations.isCuriosLoaded)
            return CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, item).map(SlotResult::stack).orElse(ItemStack.EMPTY);

        return ItemStack.EMPTY;
    }
}
