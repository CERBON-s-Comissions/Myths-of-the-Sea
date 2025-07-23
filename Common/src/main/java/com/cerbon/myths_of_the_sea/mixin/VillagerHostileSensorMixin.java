package com.cerbon.myths_of_the_sea.mixin;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.*;

@Mixin(VillagerHostilesSensor.class)
public class VillagerHostileSensorMixin {

    @Unique
    private static final float mtg$distanceDanger = 12.0f;
    @Unique
    private static final float mtg$distanceExtremeDanger = 64.0f;

    @Shadow
    @Final
    @Mutable
    private static ImmutableMap<Object, Object> ACCEPTABLE_DISTANCE_FROM_HOSTILES;

    static {
        ACCEPTABLE_DISTANCE_FROM_HOSTILES = ImmutableMap.builder()
                .putAll(ACCEPTABLE_DISTANCE_FROM_HOSTILES.entrySet())

                .put(MTSEntities.BUNYIP.get(), mtg$distanceDanger)
                .put(MTSEntities.LEVIATHAN.get(), mtg$distanceExtremeDanger)
                .put(MTSEntities.KRAKEN.get(), mtg$distanceExtremeDanger)

                .build();
    }

}
