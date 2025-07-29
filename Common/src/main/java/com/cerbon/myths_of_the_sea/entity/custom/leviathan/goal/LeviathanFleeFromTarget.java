package com.cerbon.myths_of_the_sea.entity.custom.leviathan.goal;

import com.cerbon.myths_of_the_sea.entity.custom.leviathan.LeviathanEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LeviathanFleeFromTarget extends RandomSwimmingGoal {
        private final LeviathanEntity leviathan;
        public LeviathanFleeFromTarget(LeviathanEntity leviathan, double speed, int probability) {
            super(leviathan, speed, probability);
            this.leviathan = leviathan;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && leviathan.hasAttackedOnWater;
        }
        @Override
        public boolean canContinueToUse(){
            return super.canContinueToUse() && leviathan.hasAttackedOnWater;
        }
        @Override
        public void tick(){
            if (leviathan.cooldownSwimmingAttackTicks > 0) {
                --leviathan.cooldownSwimmingAttackTicks;
            } else {
                leviathan.hasAttackedOnWater = false;
            }
        }

        @Override
        @Nullable
        protected Vec3 getPosition() {
            return BehaviorUtils.getRandomSwimmablePos(this.mob, 24, 24);
        }
    }