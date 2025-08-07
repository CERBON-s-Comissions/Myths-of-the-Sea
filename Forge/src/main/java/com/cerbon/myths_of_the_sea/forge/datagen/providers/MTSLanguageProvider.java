package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.potion.MTSPotions;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MTSLanguageProvider extends LanguageProvider {

    public MTSLanguageProvider(PackOutput output) {
        super(output, MythsOfTheSea.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        MTSItems.ITEMS.getEntries().forEach(this::addItemTranslation);
        MTSEntities.ENTITY_TYPES.getEntries().forEach(this::addEntityTranslation);

        addPotionTranslation(MTSPotions.VERY_LONG_NIGHT_VISION.get(), "Night Vision");

        add("itemGroup.myths_of_the_sea", "Myths of The Sea");

        add("attribute.name.water_speed", "Depth Strider");

        add("item.minecraft.tipped_arrow.effect.very_long_night_vision", "Arrow of Night Vision");

        add("item.myths_of_the_sea.bunyip_claw.tooltip", "+2 Attack Damage when dual wielding");

//        MTSSounds.SOUNDS.getEntries().forEach(this::addSubtitleTranslation);
        //Subtitles
        addSubtitleTranslation(MTSSounds.ABAIA_IDLE, "Abaia groans");
        addSubtitleTranslation(MTSSounds.ABAIA_ATTACK, "Abaia attacks");
        addSubtitleTranslation(MTSSounds.ABAIA_DAMAGE, "Abaia hurts");
        addSubtitleTranslation(MTSSounds.ABAIA_MOVEMENT, "Abaia swims");
        addSubtitleTranslation(MTSSounds.ABAIA_DEATH, "Abaia dies");
        addSubtitleTranslation(MTSSounds.ABAIA_FLOP, "Abaia flops");

        addSubtitleTranslation(MTSSounds.BAKE_KUJIRA_IDLE, "Bake Kujira groans");
        addSubtitleTranslation(MTSSounds.BAKE_KUJIRA_ATTACK, "Bake Kujira attacks");
        addSubtitleTranslation(MTSSounds.BAKE_KUJIRA_DAMAGE, "Bake Kujira hurts");
        addSubtitleTranslation(MTSSounds.BAKE_KUJIRA_MOVEMENT, "Bake Kujira swims");
        addSubtitleTranslation(MTSSounds.BAKE_KUJIRA_DEATH, "Bake Kujira dies");
        addSubtitleTranslation(MTSSounds.BAKE_KUJIRA_FLOP, "Bake Kujira flops");

        addSubtitleTranslation(MTSSounds.LEVIATHAN_IDLE, "Leviathan roars");
        addSubtitleTranslation(MTSSounds.LEVIATHAN_ATTACK, "Leviathan attacks");
        addSubtitleTranslation(MTSSounds.LEVIATHAN_DAMAGE, "Leviathan hurts");
        addSubtitleTranslation(MTSSounds.LEVIATHAN_MOVEMENT, "Leviathan swims");
        addSubtitleTranslation(MTSSounds.LEVIATHAN_DEATH, "Leviathan dies");

        addSubtitleTranslation(MTSSounds.HIPPOCAMPUS_IDLE, "Hippocampus neighs");
        addSubtitleTranslation(MTSSounds.HIPPOCAMPUS_ATTACK, "Leviathan attacks");
        addSubtitleTranslation(MTSSounds.HIPPOCAMPUS_DAMAGE, "Hippocampus hurts");
        addSubtitleTranslation(MTSSounds.HIPPOCAMPUS_MOVEMENT, "Hippocampus swims");
        addSubtitleTranslation(MTSSounds.HIPPOCAMPUS_DEATH, "Hippocampus dies");
        addSubtitleTranslation(MTSSounds.HIPPOCAMPUS_FLOP, "Hippocampus flops");
        addSubtitleTranslation(MTSSounds.HIPPOCAMPUS_EAT, "Hippocampus eats");

        addSubtitleTranslation(MTSSounds.BUNYIP_IDLE, "Bunyip groans");
        addSubtitleTranslation(MTSSounds.BUNYIP_ATTACK, "Bunyip attacks");
        addSubtitleTranslation(MTSSounds.BUNYIP_DAMAGE, "Bunyip hurts");
        addSubtitleTranslation(MTSSounds.BUNYIP_MOVEMENT, "Footsteps");
        addSubtitleTranslation(MTSSounds.BUNYIP_DEATH, "Bunyip dies");

        addSubtitleTranslation(MTSSounds.KRAKEN_IDLE, "Kraken rumbles");
        addSubtitleTranslation(MTSSounds.KRAKEN_ATTACK, "Kraken attacks");
        addSubtitleTranslation(MTSSounds.KRAKEN_DAMAGE, "Kraken hurts");
        addSubtitleTranslation(MTSSounds.KRAKEN_MOVEMENT, "Kraken swims");
        addSubtitleTranslation(MTSSounds.KRAKEN_DEATH, "Kraken dies");
    }

    private void addItemTranslation(RegistryEntry<Item> item) {
        String path = item.getId().getPath();
        String translation = translate(path);
        add(item.get(), translation);
    }

    private void addEntityTranslation(RegistryEntry<EntityType<?>> entity) {
        String path = entity.getId().getPath();
        String translation = translate(path);
        add(entity.get(), translation);
    }

    private void addSubtitleTranslation(RegistryEntry<SoundEvent> sound, String translation) {
        String path = sound.getId().getPath();
        String subtitle = "subtitles.myths_of_the_sea."+path;

        add(subtitle, translation);
    }

    private String translate(String path) {
        return Arrays.stream(path.split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    private void addPotionTranslation(Potion potion, String baseName) {
        String path = BuiltInRegistries.POTION.getKey(potion).getPath();

        add("item.minecraft.potion.effect." + path, "Potion of " + baseName);
        add("item.minecraft.splash_potion.effect." + path, "Splash Potion of " + baseName);
        add("item.minecraft.lingering_potion.effect." + path, "Lingering Potion of " + baseName);
    }
}
