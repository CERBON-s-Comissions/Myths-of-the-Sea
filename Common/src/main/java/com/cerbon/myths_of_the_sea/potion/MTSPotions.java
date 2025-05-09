package com.cerbon.myths_of_the_sea.potion;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

public class MTSPotions {
    public static final ResourcefulRegistry<Potion> POTIONS = ResourcefulRegistries.create(
            BuiltInRegistries.POTION,
            MythsOfTheSea.MOD_ID
    );

    public static RegistryEntry<Potion> VERY_LONG_NIGHT_VISION = register(
            new MobEffectInstance(MobEffects.NIGHT_VISION, 72000),
            "very_long_night_vision"
    );

    private static RegistryEntry<Potion> register(MobEffectInstance mobEffectInstance, String id) {
        return POTIONS.register(id, () -> new Potion(mobEffectInstance));
    }

    public static void register() {
        POTIONS.register();
    }
}
