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

    public static RegistryEntry<SoundEvent> ABAIA_IDLE = register("abaia_idle");
    public static RegistryEntry<SoundEvent> ABAIA_MOVEMENT = register("abaia_movement");
    public static RegistryEntry<SoundEvent> ABAIA_DAMAGE = register("abaia_damage");
    public static RegistryEntry<SoundEvent> ABAIA_ATTACK = register("abaia_attack");
    public static RegistryEntry<SoundEvent> ABAIA_DEATH = register("abaia_death");

    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_IDLE = register("bake_kujira_idle");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_MOVEMENT = register("bake_kujira_movement");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_DAMAGE = register("bake_kujira_damage");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_ATTACK = register("bake_kujira_attack");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_DEATH = register("bake_kujira_death");

    public static RegistryEntry<SoundEvent> BUNYIP_IDLE = register("bunyip_idle");
    public static RegistryEntry<SoundEvent> BUNYIP_MOVEMENT = register("bunyip_movement");
    public static RegistryEntry<SoundEvent> BUNYIP_DAMAGE = register("bunyip_damage");
    public static RegistryEntry<SoundEvent> BUNYIP_ATTACK = register("bunyip_attack");
    public static RegistryEntry<SoundEvent> BUNYIP_DEATH = register("bunyip_death");

    private static RegistryEntry<SoundEvent> register(String name) {
        ResourceLocation id = new ResourceLocation(MythsOfTheSea.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        SOUNDS.register();
    }
}
