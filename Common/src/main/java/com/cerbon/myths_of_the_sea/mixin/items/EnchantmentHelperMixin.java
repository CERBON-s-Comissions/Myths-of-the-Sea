package com.cerbon.myths_of_the_sea.mixin.items;

import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "getDepthStrider", at = @At("RETURN"), cancellable = true)
    private static void mtg$applyWaterSpeed(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValueI() + MTSUtils.getWaterSpeedAmount(entity));
    }
}
