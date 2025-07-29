package com.cerbon.myths_of_the_sea.entity.custom.hippocampus.goals;

import com.cerbon.myths_of_the_sea.entity.custom.hippocampus.HippocampusEntity;
import net.minecraft.world.item.crafting.Ingredient;

public class TemptGoalTamed extends TemptGoalNoMounted{
    private final HippocampusEntity hippocampus;

    public TemptGoalTamed(HippocampusEntity mob, double speedModifier, Ingredient items, boolean canScare) {
        super(mob, speedModifier, items, canScare);
        this.hippocampus=mob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.hippocampus.isTamed();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.hippocampus.isTamed();
    }
}
