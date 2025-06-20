package com.cerbon.myths_of_the_sea.fabric.entity;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntity;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntityRenderer;
import com.cerbon.myths_of_the_sea.entity.custom.bake_kujira.BakeKujiraEntity;
import com.cerbon.myths_of_the_sea.entity.custom.bake_kujira.BakeKujiraEntityRenderer;
import com.cerbon.myths_of_the_sea.entity.custom.bunyip.BunyipEntity;
import com.cerbon.myths_of_the_sea.entity.custom.bunyip.BunyipEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class MTSEntitiesFabric {

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(MTSEntities.ABAIA.get(), AbaiaEntityRenderer::new);
        EntityRendererRegistry.register(MTSEntities.BAKE_KUJIRA.get(), BakeKujiraEntityRenderer::new);
        EntityRendererRegistry.register(MTSEntities.BUNYIP.get(), BunyipEntityRenderer::new);
    }

    public static void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(MTSEntities.ABAIA.get(), AbaiaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MTSEntities.BAKE_KUJIRA.get(), BakeKujiraEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MTSEntities.BUNYIP.get(), BunyipEntity.createAttributes());
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(MTSEntities.ABAIA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, AbaiaEntity::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(MTSEntities.BAKE_KUJIRA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, BakeKujiraEntity::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(MTSEntities.BUNYIP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BunyipEntity::checkMobSpawnRules);
    }
}
