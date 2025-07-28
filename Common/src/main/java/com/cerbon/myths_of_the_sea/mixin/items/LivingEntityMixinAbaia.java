package com.cerbon.myths_of_the_sea.mixin.items;

import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixinAbaia {

    @Unique
    LivingEntity mts$THIS = (LivingEntity)(Object)this;

    @ModifyVariable(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "STORE", ordinal = 1), ordinal = 2)
    private float mtg$uncapDepthStriderLimit(float depthStriderLevel) {
        return (float) EnchantmentHelper.getDepthStrider(mts$THIS);
    }

    @ModifyVariable(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private float mtg$addLessDrag(float waterDrag) {
        if(MTSUtils.getWaterSpeedAmount(mts$THIS)>0 && mts$THIS.isSprinting() && !mts$THIS.hasEffect(MobEffects.DOLPHINS_GRACE)){
            return 0.91F;
        }

        return waterDrag;
    }

    @ModifyVariable(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "STORE", ordinal = 1), ordinal = 0)
    private float mtg$addLessDrag2(float waterDrag) {
        if(MTSUtils.getWaterSpeedAmount(mts$THIS)>0 && mts$THIS.isSprinting() && !mts$THIS.hasEffect(MobEffects.DOLPHINS_GRACE)){
            return 0.91F;
        }

        return waterDrag;
    }
}
