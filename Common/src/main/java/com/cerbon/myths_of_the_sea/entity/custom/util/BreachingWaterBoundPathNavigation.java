package com.cerbon.myths_of_the_sea.entity.custom.util;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BreachingWaterBoundPathNavigation extends WaterBoundPathNavigation {
    private boolean allowBreaching;

    public BreachingWaterBoundPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        //This way don't get stuck too much
        this.allowBreaching = true;
        this.nodeEvaluator = new SwimNodeEvaluator(this.allowBreaching);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected boolean canMoveDirectly(@NotNull Vec3 currentPos, @NotNull Vec3 nextMove) {
        return isClearForMovementBetween(this.mob, currentPos, nextMove, false);
    }

    @Override
    protected boolean canUpdatePath() {
        return this.allowBreaching || this.isInLiquid();
    }
}
