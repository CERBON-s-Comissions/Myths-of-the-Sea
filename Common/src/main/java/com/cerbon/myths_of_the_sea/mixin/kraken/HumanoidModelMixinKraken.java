package com.cerbon.myths_of_the_sea.mixin.kraken;

import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixinKraken<T extends LivingEntity> {

    @ModifyExpressionValue(
            method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/HumanoidModel;crouching:Z", opcode = Opcodes.GETFIELD))
    private boolean mtg$disableCrouchingPose(boolean crouching, @Local(argsOnly = true) T entity) {

        return crouching && !(entity.getVehicle()!=null && entity.getVehicle() instanceof KrakenEntity);
    }
}
