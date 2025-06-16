package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.potion.MTSPotions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
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
