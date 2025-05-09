package com.cerbon.myths_of_the_sea.item;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.item.custom.LeviathanHeart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public final class MTSItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, MythsOfTheSea.MOD_ID);

    public static final RegistryEntry<Item> LEVIATHAN_HEART = registerItem(
            () -> new LeviathanHeart(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .alwaysEat()
                            .nutrition(4)
                            .saturationMod(1.2f)
                            .build()
                    )
            ),
            "leviathan_heart"
    );

    private static RegistryEntry<Item> registerItem(String id) {
        return registerItem(new Item.Properties(), id);
    }

    private static RegistryEntry<Item> registerItem(Item.Properties itemProperties, String id) {
        return registerItem(() -> new Item(itemProperties), id);
    }

    private static <T extends Item> RegistryEntry<T> registerItem(Supplier<T> item, String id) {
        return ITEMS.register(id, item);
    }

    public static void register() {
        ITEMS.register();
    }
}
