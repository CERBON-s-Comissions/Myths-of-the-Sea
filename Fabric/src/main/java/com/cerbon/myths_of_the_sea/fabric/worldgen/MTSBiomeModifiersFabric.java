package com.cerbon.myths_of_the_sea.fabric.worldgen;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;

public class MTSBiomeModifiersFabric {

    public static void registerEntitySpawns() {
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.WARM_OCEAN),
                MobCategory.WATER_CREATURE,
                MTSEntities.ABAIA.get(),
                25,
                1,
                2
        );
    }
}
