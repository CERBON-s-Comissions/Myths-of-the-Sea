package com.cerbon.myths_of_the_sea.mixin.kraken;

import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z"))
    private boolean mtg$disableRidingPose(boolean isRiding, @Local(argsOnly = true) T entity) {

        return isRiding && !(entity.getVehicle()!=null && entity.getVehicle() instanceof KrakenEntity);
    }
}
