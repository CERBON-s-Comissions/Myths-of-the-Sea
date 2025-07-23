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
    public static RegistryEntry<SoundEvent> ABAIA_FLOP = register("abaia_flop");

    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_IDLE = register("bake_kujira_idle");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_MOVEMENT = register("bake_kujira_movement");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_DAMAGE = register("bake_kujira_damage");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_ATTACK = register("bake_kujira_attack");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_DEATH = register("bake_kujira_death");
    public static RegistryEntry<SoundEvent> BAKE_KUJIRA_FLOP = register("bake_kujira_flop");

    public static RegistryEntry<SoundEvent> BUNYIP_IDLE = register("bunyip_idle");
    public static RegistryEntry<SoundEvent> BUNYIP_MOVEMENT = register("bunyip_movement");
    public static RegistryEntry<SoundEvent> BUNYIP_DAMAGE = register("bunyip_damage");
    public static RegistryEntry<SoundEvent> BUNYIP_ATTACK = register("bunyip_attack");
    public static RegistryEntry<SoundEvent> BUNYIP_DEATH = register("bunyip_death");

    public static RegistryEntry<SoundEvent> LEVIATHAN_IDLE = register("leviathan_idle");
    public static RegistryEntry<SoundEvent> LEVIATHAN_MOVEMENT = register("leviathan_movement");
    public static RegistryEntry<SoundEvent> LEVIATHAN_DAMAGE = register("leviathan_damage");
    public static RegistryEntry<SoundEvent> LEVIATHAN_ATTACK = register("leviathan_attack");
    public static RegistryEntry<SoundEvent> LEVIATHAN_DEATH = register("leviathan_death");

    public static RegistryEntry<SoundEvent> HIPPOCAMPUS_IDLE = register("hippocampus_idle");
    public static RegistryEntry<SoundEvent> HIPPOCAMPUS_MOVEMENT = register("hippocampus_movement");
    public static RegistryEntry<SoundEvent> HIPPOCAMPUS_DAMAGE = register("hippocampus_damage");
    public static RegistryEntry<SoundEvent> HIPPOCAMPUS_ATTACK = register("hippocampus_attack");
    public static RegistryEntry<SoundEvent> HIPPOCAMPUS_DEATH = register("hippocampus_death");
    public static RegistryEntry<SoundEvent> HIPPOCAMPUS_FLOP = register("hippocampus_flop");
    public static RegistryEntry<SoundEvent> HIPPOCAMPUS_EAT = register("hippocampus_eat");

    public static RegistryEntry<SoundEvent> KRAKEN_IDLE = register("kraken_idle");
    public static RegistryEntry<SoundEvent> KRAKEN_MOVEMENT = register("kraken_movement");
    public static RegistryEntry<SoundEvent> KRAKEN_DAMAGE = register("kraken_damage");
    public static RegistryEntry<SoundEvent> KRAKEN_ATTACK = register("kraken_attack");
    public static RegistryEntry<SoundEvent> KRAKEN_DEATH = register("kraken_death");


    private static RegistryEntry<SoundEvent> register(String name) {
        ResourceLocation id = new ResourceLocation(MythsOfTheSea.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        SOUNDS.register();
    }
}
