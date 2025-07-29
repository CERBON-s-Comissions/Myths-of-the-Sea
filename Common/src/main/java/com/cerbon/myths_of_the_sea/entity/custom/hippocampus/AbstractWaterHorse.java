package com.cerbon.myths_of_the_sea.entity.custom.hippocampus;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;

public class AbstractWaterHorse extends AbstractHorse {
    protected AbstractWaterHorse(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 120;
    }

    @Override
    public int getExperienceReward() {
        return 1 + this.level().random.nextInt(3);
    }

    protected void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(this.getMaxAirSupply());
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(i);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }
    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return true;
    }

    public static boolean checkSurfaceWaterAnimalSpawnRules(
            EntityType<? extends AbstractWaterHorse> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
    ) {
        int i = level.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }
}
