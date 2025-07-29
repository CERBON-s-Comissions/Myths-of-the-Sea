package com.cerbon.myths_of_the_sea.entity.custom.hippocampus.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;

public class TemptGoalNoMounted extends TemptGoal {
    public TemptGoalNoMounted(PathfinderMob mob, double speedModifier, Ingredient items, boolean canScare) {
        super(mob, speedModifier, items, canScare);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.mob.isVehicle();
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.mob.isVehicle();
    }
}
