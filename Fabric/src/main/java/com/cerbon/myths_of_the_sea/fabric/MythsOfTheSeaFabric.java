package com.cerbon.myths_of_the_sea.fabric;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class MythsOfTheSeaFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        MythsOfTheSea.init();
    }

    @Override
    public void onInitializeClient() {}
}