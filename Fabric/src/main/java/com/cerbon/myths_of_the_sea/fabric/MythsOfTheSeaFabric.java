package com.cerbon.myths_of_the_sea.fabric;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.fabric.entity.MTSEntitiesFabric;
import com.cerbon.myths_of_the_sea.fabric.recipe.brewing.BrewingRecipes;
import com.cerbon.myths_of_the_sea.fabric.worldgen.MTSBiomeModifiersFabric;
import com.cerbon.myths_of_the_sea.item.custom.MTSSpawnEggItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class MythsOfTheSeaFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        MythsOfTheSea.init();

        BrewingRecipes.register();
        MTSSpawnEggItem.registerSpawnEggsDispenserBehaviour();
        MTSEntitiesFabric.registerEntityAttributes();
        MTSBiomeModifiersFabric.addEntitySpawns();
    }

    @Override
    public void onInitializeClient() {
        MTSEntitiesFabric.registerEntityRenderers();
    }
}