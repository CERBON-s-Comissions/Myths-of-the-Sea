package com.cerbon.myths_of_the_sea.fabric.entity;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntity;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntityRenderer;
import com.cerbon.myths_of_the_sea.entity.custom.bake_kujira.BakeKujiraEntity;
import com.cerbon.myths_of_the_sea.entity.custom.bake_kujira.BakeKujiraEntityRenderer;
import com.cerbon.myths_of_the_sea.entity.custom.bunyip.BunyipEntity;
import com.cerbon.myths_of_the_sea.entity.custom.bunyip.BunyipEntityRenderer;
import com.cerbon.myths_of_the_sea.entity.custom.hippocampus.HippocampusEntity;
import com.cerbon.myths_of_the_sea.entity.custom.hippocampus.HippocampusEntityRenderer;
import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntityRenderer;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.LeviathanEntity;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.LeviathanEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class MTSEntitiesFabric {

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(MTSEntities.ABAIA.get(), AbaiaEntityRenderer::new);
        EntityRendererRegistry.register(MTSEntities.BAKE_KUJIRA.get(), BakeKujiraEntityRenderer::new);
        EntityRendererRegistry.register(MTSEntities.BUNYIP.get(), BunyipEntityRenderer::new);
        EntityRendererRegistry.register(MTSEntities.LEVIATHAN.get(), LeviathanEntityRenderer::new);
        EntityRendererRegistry.register(MTSEntities.HIPPOCAMPUS.get(), HippocampusEntityRenderer::new);
        EntityRendererRegistry.register(MTSEntities.KRAKEN.get(), KrakenEntityRenderer::new);
    }

    public static void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(MTSEntities.ABAIA.get(), AbaiaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MTSEntities.BAKE_KUJIRA.get(), BakeKujiraEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MTSEntities.BUNYIP.get(), BunyipEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MTSEntities.LEVIATHAN.get(), LeviathanEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MTSEntities.HIPPOCAMPUS.get(), HippocampusEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MTSEntities.KRAKEN.get(), KrakenEntity.createAttributes());
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(MTSEntities.ABAIA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, AbaiaEntity::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(MTSEntities.BAKE_KUJIRA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, BakeKujiraEntity::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(MTSEntities.BUNYIP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BunyipEntity::checkMobSpawnRules);

        SpawnPlacements.register(MTSEntities.LEVIATHAN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, LeviathanEntity::surfaceWaterSpawnRulesAndNotNearLeviathan);
        SpawnPlacements.register(MTSEntities.HIPPOCAMPUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, HippocampusEntity::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(MTSEntities.KRAKEN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, KrakenEntity::surfaceWaterSpawnRulesDeepAndNotNearKraken);
    }
}
