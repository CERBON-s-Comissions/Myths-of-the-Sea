package com.cerbon.myths_of_the_sea.entity.custom.bunyip.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;

public class BunyipSwimAroundGoal extends RandomSwimmingGoal {

    public BunyipSwimAroundGoal(PathfinderMob pathAwareEntity, double d, int i) {
        super(pathAwareEntity, d, i);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.mob.isInWater();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && mob.isInWater();
    }
}
