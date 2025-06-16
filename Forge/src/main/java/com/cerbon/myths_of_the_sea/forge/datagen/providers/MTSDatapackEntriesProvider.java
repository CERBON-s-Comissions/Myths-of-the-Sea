package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.forge.worldgen.MTSBiomeModifiersForge;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MTSDatapackEntriesProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, MTSBiomeModifiersForge::bootstrap);

    public MTSDatapackEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MythsOfTheSea.MOD_ID));
    }
}
