package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
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
    }

    private void addItemTranslation(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();

        String translation = Arrays.stream(path.split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));

        add(item, translation);
    }
}
