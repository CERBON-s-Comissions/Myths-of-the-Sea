package com.cerbon.myths_of_the_sea.forge.entity;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntity;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntityRenderer;
import com.cerbon.myths_of_the_sea.entity.custom.bake_kujira.BakeKujiraEntity;
import com.cerbon.myths_of_the_sea.entity.custom.bake_kujira.BakeKujiraEntityRenderer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

public class MTSEntitiesForge {

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MTSEntities.ABAIA.get(), AbaiaEntityRenderer::new);
        event.registerEntityRenderer(MTSEntities.BAKE_KUJIRA.get(), BakeKujiraEntityRenderer::new);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(MTSEntities.ABAIA.get(), AbaiaEntity.createAttributes());
        event.put(MTSEntities.BAKE_KUJIRA.get(), BakeKujiraEntity.createAttributes());
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(MTSEntities.ABAIA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, AbaiaEntity::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MTSEntities.BAKE_KUJIRA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, BakeKujiraEntity::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
