package com.cerbon.myths_of_the_sea.mixin.kraken;

import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("all")
@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow protected abstract Player getCameraPlayer();

    /**
     * Disables the mount health when riding a kraken, only works in Fabric, for Forge uses {@link MythsOfTheSeaForge#customizeOverlay(RenderGuiOverlayEvent.Pre)}
     */
    @Inject(method = "getPlayerVehicleWithHealth", at = @At("RETURN"), cancellable = true)
    private void mtg$disableMountHealth(CallbackInfoReturnable<LivingEntity> cir){
        Player player = this.getCameraPlayer();

        Entity mount = player.getVehicle();

        if(mount instanceof KrakenEntity){
            cir.setReturnValue(null);
        }
    }

}
