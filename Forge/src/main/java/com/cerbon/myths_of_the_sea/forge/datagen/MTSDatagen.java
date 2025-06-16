package com.cerbon.myths_of_the_sea.forge.datagen;

import com.cerbon.myths_of_the_sea.forge.datagen.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class MTSDatagen {

    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new MTSItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new MTSLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new MTSSoundDefinitionsProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeServer(), new MTSRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new MTSDatapackEntriesProvider(packOutput, lookupProvider));
    }
}
