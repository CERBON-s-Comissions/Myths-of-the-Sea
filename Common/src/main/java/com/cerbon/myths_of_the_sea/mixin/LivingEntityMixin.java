package com.cerbon.myths_of_the_sea.mixin;

import com.cerbon.myths_of_the_sea.item.custom.armor.MTSArmorMaterials;
import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"), cancellable = true)
    private void mtg$canAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        if (thisEntity.getMobType() == MobType.UNDEAD && target instanceof Player player && MTSUtils.isUsingFullArmorSet(MTSArmorMaterials.BAKE_KUJIRA, player))
            cir.setReturnValue(false);
    }
}
