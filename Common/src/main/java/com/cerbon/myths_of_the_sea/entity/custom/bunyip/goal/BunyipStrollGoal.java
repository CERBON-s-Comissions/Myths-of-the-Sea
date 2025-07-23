package com.cerbon.myths_of_the_sea.entity.custom.bunyip.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class BunyipStrollGoal extends RandomStrollGoal {


    public BunyipStrollGoal(PathfinderMob mob, double speedModifier, int interval) {
        super(mob, speedModifier, interval);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !mob.isInWater();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !mob.isInWater();
    }
}
