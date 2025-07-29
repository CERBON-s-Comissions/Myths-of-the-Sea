package com.cerbon.myths_of_the_sea.entity.custom.leviathan.goal;

import com.cerbon.myths_of_the_sea.entity.custom.util.ExtraReachNearestAttackGoal;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.LeviathanEntity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class LeviathanNearestAttackGoal<T extends LivingEntity> extends ExtraReachNearestAttackGoal<T> {

    private final LeviathanEntity leviathan;

    public LeviathanNearestAttackGoal(LeviathanEntity mob, Class<T> targetType, int randomInterval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetType, randomInterval, mustSee, mustReach, targetPredicate);
        this.leviathan=mob;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.leviathan.hasAttackedOnWater;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.leviathan.hasAttackedOnWater;
    }
}
