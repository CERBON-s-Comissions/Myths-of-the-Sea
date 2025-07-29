package com.cerbon.myths_of_the_sea.entity;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.function.Supplier;

public class MTSEntityAttributes {
    public static final ResourcefulRegistry<Attribute> ATTRIBUTE = ResourcefulRegistries.create(
            BuiltInRegistries.ATTRIBUTE,
            MythsOfTheSea.MOD_ID
    );

    public static final RegistryEntry<Attribute> WATER_SPEED =
            registerAttribute(
                    ()-> new RangedAttribute("attribute.name.water_speed",
                            //Default
                            0.0,
                            //Min
                            0.0,
                            //Max
                            100.0)
                            .setSyncable(true),"water_speed");

    private static <T extends Attribute> RegistryEntry<T> registerAttribute(Supplier<T> item, String id) {
        return ATTRIBUTE.register(id, item);
    }

    public static void register() {
        ATTRIBUTE.register();
    }
}
