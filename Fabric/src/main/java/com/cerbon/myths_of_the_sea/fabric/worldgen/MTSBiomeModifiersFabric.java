package com.cerbon.myths_of_the_sea.fabric.worldgen;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class MTSBiomeModifiersFabric {

    public static void addEntitySpawns() {
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.WARM_OCEAN),
                MobCategory.WATER_CREATURE,
                MTSEntities.ABAIA.get(),
                25,
                1,
                2
        );

        SpawnPlacements.register(MTSEntities.ABAIA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, AbaiaEntity::checkSurfaceWaterAnimalSpawnRules);
    }
}
