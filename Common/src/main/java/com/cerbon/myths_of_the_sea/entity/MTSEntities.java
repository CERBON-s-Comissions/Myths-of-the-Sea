package com.cerbon.myths_of_the_sea.entity;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntity;
import com.cerbon.myths_of_the_sea.entity.custom.bake_kujira.BakeKujiraEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class MTSEntities {
    public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(
            BuiltInRegistries.ENTITY_TYPE,
            MythsOfTheSea.MOD_ID
    );

    public static final RegistryEntry<EntityType<AbaiaEntity>> ABAIA = ENTITY_TYPES.register(
            "abaia",
            () -> EntityType.Builder.of(AbaiaEntity::new, MobCategory.WATER_CREATURE)
                    .sized(7.5f, 2.0f)
                    .build("abaia")
    );

    public static final RegistryEntry<EntityType<BakeKujiraEntity>> BAKE_KUJIRA = ENTITY_TYPES.register(
            "bake_kujira",
            () -> EntityType.Builder.of(BakeKujiraEntity::new, MobCategory.WATER_CREATURE)
                    .sized(7.0f, 2.0f)
                    .build("bake_kujira")
    );

    public static void register() {
        ENTITY_TYPES.register();
    }
}
