package com.cerbon.myths_of_the_sea.entity.custom.bunyip;

import com.cerbon.myths_of_the_sea.entity.custom.util.BreachingWaterBoundPathNavigation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class AmphibiousPathNavigation extends BreachingWaterBoundPathNavigation {

    public AmphibiousPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new AmphibiousNodeEvaluator(false);
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected boolean canMoveDirectly(@NotNull Vec3 posVec31, @NotNull Vec3 posVec32) {
        return isClearForMovementBetween(this.mob, posVec31, posVec32, true);
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }
}
