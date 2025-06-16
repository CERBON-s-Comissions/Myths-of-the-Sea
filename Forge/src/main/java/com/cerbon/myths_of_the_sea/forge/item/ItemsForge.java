package com.cerbon.myths_of_the_sea.forge.item;

import com.cerbon.myths_of_the_sea.item.custom.MTSSpawnEggItem;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ItemsForge {

    public static void registerSpawnEggsDispenserBehaviour(FMLCommonSetupEvent event) {
        MTSSpawnEggItem.registerSpawnEggsDispenserBehaviour();
    }

    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        MTSSpawnEggItem.registerSpawnEggsColors(event.getItemColors());
    }
}
