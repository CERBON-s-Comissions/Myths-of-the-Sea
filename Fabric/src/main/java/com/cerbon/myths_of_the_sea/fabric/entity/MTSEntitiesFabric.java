package com.cerbon.myths_of_the_sea.fabric.entity;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntity;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class MTSEntitiesFabric {

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(MTSEntities.ABAIA.get(), AbaiaEntityRenderer::new);
    }

    public static void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(MTSEntities.ABAIA.get(), AbaiaEntity.createAttributes());
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(MTSEntities.ABAIA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, AbaiaEntity::checkSurfaceWaterAnimalSpawnRules);
    }
}
