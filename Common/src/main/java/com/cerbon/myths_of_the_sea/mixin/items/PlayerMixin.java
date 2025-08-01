package com.cerbon.myths_of_the_sea.mixin.items;

import com.cerbon.myths_of_the_sea.util.MTSUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @SuppressWarnings("all")
    @Unique
    Player THIS = (Player) (Object) this;

    @Unique
    private float mts$bunyipClawExtraDamage = 0;

    @Inject(method = "attack", at = @At("HEAD"))
    private void assignExtraDamage(Entity target, CallbackInfo ci){
        if(MTSUtils.hasDualwieldWeapon(THIS)){
            mts$bunyipClawExtraDamage=2;
        } else {
            mts$bunyipClawExtraDamage=0;
        }
    }

    @ModifyVariable(method = "attack", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private float injectClawExtraDamage(float value, @Local(argsOnly = true) Entity target){
        return value + mts$bunyipClawExtraDamage;
    }

}
