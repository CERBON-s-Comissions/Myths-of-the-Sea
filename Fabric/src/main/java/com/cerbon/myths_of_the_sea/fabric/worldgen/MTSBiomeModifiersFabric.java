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

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.OCEAN),
                MobCategory.WATER_CREATURE,
                MTSEntities.BAKE_KUJIRA.get(),
                3,
                1,
                1
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.SWAMP, Biomes.MANGROVE_SWAMP),
                MobCategory.MONSTER,
                MTSEntities.BUNYIP.get(),
                4,
                3,
                4
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN),
                MobCategory.MONSTER,
                MTSEntities.LEVIATHAN.get(),
                2,
                1,
                1
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.WARM_OCEAN),
                MobCategory.WATER_CREATURE,
                MTSEntities.HIPPOCAMPUS.get(),
                12,
                2,
                4
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN),
                MobCategory.MONSTER,
                MTSEntities.KRAKEN.get(),
                1,
                1,
                1
        );
    }
}
