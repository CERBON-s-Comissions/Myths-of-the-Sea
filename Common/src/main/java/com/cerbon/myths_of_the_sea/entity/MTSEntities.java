package com.cerbon.myths_of_the_sea.entity;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.AbaiaEntity;
import com.cerbon.myths_of_the_sea.entity.custom.bake_kujira.BakeKujiraEntity;
import com.cerbon.myths_of_the_sea.entity.custom.bunyip.BunyipEntity;
import com.cerbon.myths_of_the_sea.entity.custom.hippocampus.HippocampusEntity;
import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.LeviathanEntity;
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
                    .sized(4f, 2.0f)
                    .build("abaia")
    );

    public static final RegistryEntry<EntityType<BakeKujiraEntity>> BAKE_KUJIRA = ENTITY_TYPES.register(
            "bake_kujira",
            () -> EntityType.Builder.of(BakeKujiraEntity::new, MobCategory.WATER_CREATURE)
                    .sized(4.0f, 2.0f)
                    .build("bake_kujira")
    );

    public static final RegistryEntry<EntityType<BunyipEntity>> BUNYIP = ENTITY_TYPES.register(
            "bunyip",
            () -> EntityType.Builder.of(BunyipEntity::new, MobCategory.MONSTER)
                    .sized(2.0f, 2.0f)
                    .build("bunyip")
    );

    public static final RegistryEntry<EntityType<LeviathanEntity>> LEVIATHAN = ENTITY_TYPES.register(
            "leviathan",
            () -> EntityType.Builder.of(LeviathanEntity::new, MobCategory.MONSTER)
                    .sized(22.0f, 2.5f)
                    .clientTrackingRange(12)
                    .build("leviathan")
    );

    public static final RegistryEntry<EntityType<HippocampusEntity>> HIPPOCAMPUS = ENTITY_TYPES.register(
            "hippocampus",
            () -> EntityType.Builder.of(HippocampusEntity::new, MobCategory.WATER_CREATURE)
                    .sized(2.0f, 2.0f)
                    .build("hippocampus")
    );

    public static final RegistryEntry<EntityType<KrakenEntity>> KRAKEN = ENTITY_TYPES.register(
            "kraken",
            () -> EntityType.Builder.of(KrakenEntity::new, MobCategory.MONSTER)
                    .sized(8.0f, 8.0f)
                    .clientTrackingRange(10)
                    .build("kraken")
    );

    public static void register() {
        ENTITY_TYPES.register();
    }
}
