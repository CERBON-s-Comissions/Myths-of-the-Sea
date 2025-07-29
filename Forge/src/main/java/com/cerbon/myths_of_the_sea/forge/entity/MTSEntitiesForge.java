package com.cerbon.myths_of_the_sea.forge.entity;

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
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

public class MTSEntitiesForge {

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MTSEntities.ABAIA.get(), AbaiaEntityRenderer::new);
        event.registerEntityRenderer(MTSEntities.BAKE_KUJIRA.get(), BakeKujiraEntityRenderer::new);
        event.registerEntityRenderer(MTSEntities.BUNYIP.get(), BunyipEntityRenderer::new);
        event.registerEntityRenderer(MTSEntities.LEVIATHAN.get(), LeviathanEntityRenderer::new);
        event.registerEntityRenderer(MTSEntities.HIPPOCAMPUS.get(), HippocampusEntityRenderer::new);
        event.registerEntityRenderer(MTSEntities.KRAKEN.get(), KrakenEntityRenderer::new);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(MTSEntities.ABAIA.get(), AbaiaEntity.createAttributes());
        event.put(MTSEntities.BAKE_KUJIRA.get(), BakeKujiraEntity.createAttributes());
        event.put(MTSEntities.BUNYIP.get(), BunyipEntity.createAttributes());
        event.put(MTSEntities.LEVIATHAN.get(), LeviathanEntity.createAttributes());
        event.put(MTSEntities.HIPPOCAMPUS.get(), HippocampusEntity.createAttributes());
        event.put(MTSEntities.KRAKEN.get(), KrakenEntity.createAttributes());
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(MTSEntities.ABAIA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, AbaiaEntity::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MTSEntities.BAKE_KUJIRA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, BakeKujiraEntity::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MTSEntities.BUNYIP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BunyipEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(MTSEntities.LEVIATHAN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, LeviathanEntity::surfaceWaterSpawnRulesAndNotNearLeviathan, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MTSEntities.HIPPOCAMPUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, HippocampusEntity::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MTSEntities.KRAKEN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, KrakenEntity::surfaceWaterSpawnRulesDeepAndNotNearKraken, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
