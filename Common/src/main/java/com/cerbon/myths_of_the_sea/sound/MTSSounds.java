package com.cerbon.myths_of_the_sea.sound;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class MTSSounds {
    public static final ResourcefulRegistry<SoundEvent> SOUNDS = ResourcefulRegistries.create(
            BuiltInRegistries.SOUND_EVENT,
            MythsOfTheSea.MOD_ID
    );

    private static RegistryEntry<SoundEvent> register(String name) {
        ResourceLocation id = new ResourceLocation(MythsOfTheSea.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        SOUNDS.register();
    }
}
