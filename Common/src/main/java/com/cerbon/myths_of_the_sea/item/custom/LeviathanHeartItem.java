package com.cerbon.myths_of_the_sea.item.custom;

import com.cerbon.myths_of_the_sea.util.mixin.IServerPlayerMixin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LeviathanHeartItem extends Item {

    public LeviathanHeartItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack itemStack = super.finishUsingItem(stack, level, livingEntity);

        if (livingEntity instanceof ServerPlayer player) {
            IServerPlayerMixin playerMixin = (IServerPlayerMixin) player;

            if (playerMixin.mts$getLeviathanHeartsEaten() < 5) {
                playerMixin.mts$increaseLeviathanHeartsEaten();
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getAttribute(Attributes.MAX_HEALTH).getBaseValue() + 4.0D);
            }
        }

        return itemStack;
    }
}
