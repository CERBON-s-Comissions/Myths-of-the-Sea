package com.cerbon.myths_of_the_sea.creative_tab;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MTSCreativeModeTabs {
    public static final ResourcefulRegistry<CreativeModeTab> CREATIVE_MODE_TABS = ResourcefulRegistries.create(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            MythsOfTheSea.MOD_ID
    );

    public static final RegistryEntry<CreativeModeTab> MTS_CREATIVE_TAB = CREATIVE_MODE_TABS.register(
            "item_group_" + MythsOfTheSea.MOD_ID,
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 6)
                    .title(Component.translatable("itemGroup." + MythsOfTheSea.MOD_ID))
                    .icon(() -> new ItemStack(MTSItems.LEVIATHAN_HEART.get()))
                    .displayItems((itemDisplayParameters, output) -> MTSItems.ITEMS.boundStream().forEach(output::accept))
                    .build()
    );

    public static void register() {
        CREATIVE_MODE_TABS.register();
    }
}
