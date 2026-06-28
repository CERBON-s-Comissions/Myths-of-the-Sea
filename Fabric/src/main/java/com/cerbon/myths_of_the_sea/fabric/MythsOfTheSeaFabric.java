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
        MTSEntitiesFabric.registerSpawnPlacements();
        MTSBiomeModifiersFabric.registerEntitySpawns();

        MTSConfigFabric.init("myths_of_the_sea", "Myths of the Sea", MTSConfigFabric.class);
//        CommandRegistrationCallback.EVENT.register((dispatcher, access, env) -> {
//            dispatcher.register(CommandManager.literal("bcp_reload").executes(context -> {
//                MTSConfigFabric.init("bcp", "Bewitchment Compatibility Patches", MTSConfigFabric.class);
//
//                context.getSource().sendMessage(Text.literal("§2[Bewitchment Compatibility Patches]:§a Config reload complete"));
//                return 1;
//            } ));
//        });
    }

    @Override
    public void onInitializeClient() {
        MTSEntitiesFabric.registerEntityRenderers();
    }
}