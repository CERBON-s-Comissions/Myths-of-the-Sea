package com.cerbon.myths_of_the_sea.forge;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.forge.datagen.MTSDatagen;
import com.cerbon.myths_of_the_sea.forge.entity.MTSEntitiesForge;
import com.cerbon.myths_of_the_sea.forge.item.ItemsForge;
import com.cerbon.myths_of_the_sea.forge.item.KrakenTentacleItem;
import com.cerbon.myths_of_the_sea.forge.overlay.MTSOverlays;
import com.cerbon.myths_of_the_sea.forge.recipe.brewing.BrewingRecipes;
import com.cerbon.myths_of_the_sea.integration.MTSIntegrations;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.commons.lang3.tuple.Pair;

@Mod(MythsOfTheSea.MOD_ID)
public class MythsOfTheSeaForge {

    public static final MTSConfigForge COMMON_CONFIG;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        Pair<MTSConfigForge, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder()
                .configure(MTSConfigForge::new);
        COMMON_CONFIG = pair.getLeft();
        COMMON_SPEC = pair.getRight();

    }

    public MythsOfTheSeaForge() {
        MythsOfTheSea.init();

        IEventBus modEventBus   = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);

        modEventBus.addListener(BrewingRecipes::register);

        modEventBus.addListener(ItemsForge::registerSpawnEggsDispenserBehaviour);

        modEventBus.addListener(MTSEntitiesForge::registerEntityRenderers);
        modEventBus.addListener(MTSEntitiesForge::registerEntityAttributes);
        modEventBus.addListener(MTSEntitiesForge::registerSpawnPlacements);

        modEventBus.addListener(MTSDatagen::onGatherData);

        if (FMLLoader.getDist().isClient())
            forgeEventBus.addListener(MTSOverlays::customizeOverlay);

        if (MTSIntegrations.isCuriosLoaded){
            forgeEventBus.addListener(KrakenTentacleItem::registerCuriosAttributes);
        }
    }
}