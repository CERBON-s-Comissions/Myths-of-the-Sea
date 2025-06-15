package com.cerbon.myths_of_the_sea.forge;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.forge.datagen.MTSDatagen;
import com.cerbon.myths_of_the_sea.forge.entity.MTSEntitiesForge;
import com.cerbon.myths_of_the_sea.forge.item.KrakenTentacleItem;
import com.cerbon.myths_of_the_sea.forge.recipe.brewing.BrewingRecipes;
import com.cerbon.myths_of_the_sea.integration.MTSIntegrations;
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

        modEventBus.addListener(MTSEntitiesForge::registerEntityRenderers);
        modEventBus.addListener(MTSEntitiesForge::registerEntityAttributes);

        modEventBus.addListener(MTSDatagen::onGatherData);

        if (MTSIntegrations.isCuriosLoaded)
            forgeEventBus.addListener(KrakenTentacleItem::registerCuriosAttributes);
    }
}