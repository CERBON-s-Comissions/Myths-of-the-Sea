package com.cerbon.myths_of_the_sea.forge;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import com.cerbon.myths_of_the_sea.forge.datagen.MTSDatagen;
import com.cerbon.myths_of_the_sea.forge.entity.MTSEntitiesForge;
import com.cerbon.myths_of_the_sea.forge.item.ItemsForge;
import com.cerbon.myths_of_the_sea.forge.item.KrakenTentacleItem;
import com.cerbon.myths_of_the_sea.forge.recipe.brewing.BrewingRecipes;
import com.cerbon.myths_of_the_sea.integration.MTSIntegrations;
import com.cerbon.myths_of_the_sea.mixin.kraken.GuiMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MythsOfTheSea.MOD_ID)
public class MythsOfTheSeaForge {

    public MythsOfTheSeaForge() {
        MythsOfTheSea.init();

        IEventBus modEventBus   = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        modEventBus.addListener(BrewingRecipes::register);

        modEventBus.addListener(ItemsForge::registerColors);
        modEventBus.addListener(ItemsForge::registerSpawnEggsDispenserBehaviour);

        modEventBus.addListener(MTSEntitiesForge::registerEntityRenderers);
        modEventBus.addListener(MTSEntitiesForge::registerEntityAttributes);
        modEventBus.addListener(MTSEntitiesForge::registerSpawnPlacements);

        modEventBus.addListener(MTSDatagen::onGatherData);

        forgeEventBus.addListener(MythsOfTheSeaForge::customizeOverlay);

        if (MTSIntegrations.isCuriosLoaded)
            forgeEventBus.addListener(KrakenTentacleItem::registerCuriosAttributes);
    }

    /**
     * Disables the mount health when riding a kraken, it works similar to {@link GuiMixin} but for Forge
     */
    public static void customizeOverlay(RenderGuiOverlayEvent.Pre event) {

        if(event.getOverlay().equals(GuiOverlayManager.findOverlay(new ResourceLocation("minecraft", "mount_health")))){
            boolean mountIsKraken = Minecraft.getInstance().player !=null && Minecraft.getInstance().player.getVehicle()!=null && Minecraft.getInstance().player.getVehicle() instanceof KrakenEntity;
            if(mountIsKraken)
                event.setCanceled(true);
        }
    }
}