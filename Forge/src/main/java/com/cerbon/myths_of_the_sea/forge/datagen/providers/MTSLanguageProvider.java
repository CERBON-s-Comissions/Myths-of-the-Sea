package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.potion.MTSPotions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
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
        MTSItems.ITEMS.getEntries().forEach(item -> addItemTranslation(item.get()));

        addPotionTranslation(MTSPotions.VERY_LONG_NIGHT_VISION.get(), "Night Vision");

        add("itemGroup.myths_of_the_sea", "Myths of The Sea");
    }

    private void addItemTranslation(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();

        String translation = Arrays.stream(path.split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));

        add(item, translation);
    }

    private void addPotionTranslation(Potion potion, String baseName) {
        String path = BuiltInRegistries.POTION.getKey(potion).getPath();

        add("item.minecraft.potion.effect." + path, "Potion of " + baseName);
        add("item.minecraft.splash_potion.effect." + path, "Splash Potion of " + baseName);
        add("item.minecraft.lingering_potion.effect." + path, "Lingering Potion of " + baseName);
    }
}
