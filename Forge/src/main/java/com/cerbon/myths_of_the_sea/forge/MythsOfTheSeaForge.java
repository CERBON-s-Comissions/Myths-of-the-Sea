package com.cerbon.myths_of_the_sea.forge;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.forge.datagen.MTSDatagen;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MythsOfTheSea.MOD_ID)
public class MythsOfTheSeaForge {

    public MythsOfTheSeaForge() {
        MythsOfTheSea.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(MTSDatagen::onGatherData);
    }
}