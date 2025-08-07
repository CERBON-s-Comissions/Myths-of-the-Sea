package com.cerbon.myths_of_the_sea.forge.overlay;

import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import com.cerbon.myths_of_the_sea.mixin.kraken.GuiMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;

@OnlyIn(Dist.CLIENT)
public class MTSOverlays {
    /**
     * Disables the mount health when riding a kraken, it works similar to {@link GuiMixin} but for Forge
     */
    public static void customizeOverlay(RenderGuiOverlayEvent.Pre event) {
        if(event.getOverlay().equals(GuiOverlayManager.findOverlay(new ResourceLocation("minecraft", "mount_health")))){
            boolean mountIsKraken = Minecraft.getInstance().player != null &&
                    Minecraft.getInstance().player.getVehicle() != null &&
                    Minecraft.getInstance().player.getVehicle() instanceof KrakenEntity;
            if(mountIsKraken)
                event.setCanceled(true);
        }
    }

}
