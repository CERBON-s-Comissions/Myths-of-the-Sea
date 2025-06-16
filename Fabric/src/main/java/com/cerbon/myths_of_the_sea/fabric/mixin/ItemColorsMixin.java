package com.cerbon.myths_of_the_sea.fabric.mixin;

import com.cerbon.myths_of_the_sea.item.custom.MTSSpawnEggItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public abstract class ItemColorsMixin {

    @Inject(method = "createDefault", at = @At("TAIL"))
    private static void registerItemColors(BlockColors colors, CallbackInfoReturnable<ItemColors> cir, @Local(ordinal = 0) ItemColors itemColors) {
        MTSSpawnEggItem.registerSpawnEggsColors(itemColors);
    }
}
