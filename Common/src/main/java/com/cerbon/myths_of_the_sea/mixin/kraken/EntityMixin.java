package com.cerbon.myths_of_the_sea.mixin.kraken;

import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Unique
    Entity mts$THIS = (Entity)(Object)this;

    @Shadow public abstract @Nullable Entity getVehicle();

    @Inject(method = "isShiftKeyDown", at = @At("RETURN"), cancellable = true)
    private void mtg$cantShiftIfKraken(CallbackInfoReturnable<Boolean> cir) {
        if(this.getVehicle()!=null && this.getVehicle() instanceof KrakenEntity && !(mts$THIS instanceof Player player && player.isCreative())){
            cir.setReturnValue(false);
        }
    }

}
