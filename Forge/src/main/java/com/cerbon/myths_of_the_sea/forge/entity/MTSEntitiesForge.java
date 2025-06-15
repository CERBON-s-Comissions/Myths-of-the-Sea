package com.cerbon.myths_of_the_sea.forge.entity;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntity;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class MTSEntitiesForge {

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MTSEntities.ABAIA.get(), AbaiaEntityRenderer::new);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(MTSEntities.ABAIA.get(), AbaiaEntity.createAttributes());
    }
}
