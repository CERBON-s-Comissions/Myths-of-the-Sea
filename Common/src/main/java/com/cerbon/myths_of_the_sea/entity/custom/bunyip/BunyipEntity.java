package com.cerbon.myths_of_the_sea.entity.custom.bunyip;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.myths_of_the_sea.entity.custom.bunyip.goal.BunyipAttackGoal;
import com.cerbon.myths_of_the_sea.entity.custom.bunyip.goal.BunyipStrollGoal;
import com.cerbon.myths_of_the_sea.entity.custom.bunyip.goal.BunyipSwimAroundGoal;
import com.cerbon.myths_of_the_sea.entity.custom.util.WaterOrLandLookControl;
import com.cerbon.myths_of_the_sea.entity.custom.util.WaterOrLandMoveControl;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BunyipEntity extends PathfinderMob implements GeoEntity, Enemy {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation MOVE_ANIM = RawAnimation.begin().thenLoop("move");
    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation DIE_ANIM = RawAnimation.begin().thenPlayAndHold("death");
    private static final int ATTACK_ANIM_TIME = 21;

    //Water
    private static final RawAnimation IDLE_ANIM_WATER = RawAnimation.begin().thenLoop("idle_swim");
    private static final RawAnimation MOVE_ANIM_WATER = RawAnimation.begin().thenLoop("swim");
    private static final RawAnimation ATTACK_ANIM_WATER = RawAnimation.begin().then("attack_swim", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation DIE_ANIM_WATER = RawAnimation.begin().thenPlayAndHold("die_swim");

    public BunyipEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);

        this.setPathfindingMalus(BlockPathTypes.WATER, 1.0F);
        this.setMaxUpStep(1.0F);
        this.moveControl = new WaterOrLandMoveControl(this);
        this.lookControl = new WaterOrLandLookControl(this, 90);
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.21F)
                .add(Attributes.FOLLOW_RANGE, 48F)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BunyipAttackGoal(this, 1.2F, false));

        this.goalSelector.addGoal(1, new BunyipSwimAroundGoal(this, 0.75, 60));
        this.goalSelector.addGoal(2, new BunyipStrollGoal(this, 1.0, 10));

        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }


    //Navigation
    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new BunyipBodyRotationControl(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            //Scale speed so it becomes way faster in water
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        }
        else super.travel(travelVector);
    }


    //Sounds
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MTSSounds.BUNYIP_IDLE.get();
    }

    protected SoundEvent getStepSound() {
        return MTSSounds.BUNYIP_MOVEMENT.get();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        if (this.level() instanceof ServerLevel serverLevel)
            SoundUtils.playSound(serverLevel, this.position(), MTSSounds.BUNYIP_ATTACK.get(), SoundSource.HOSTILE, 3F, 6D);

        return super.doHurtTarget(target);
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return MTSSounds.BUNYIP_DAMAGE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MTSSounds.BUNYIP_DEATH.get();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Attack", 5, (state) -> {
            if (this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {

                state.getController().forceAnimationReset();
                state.getController().setAnimation(this.isInWater()? ATTACK_ANIM_WATER : ATTACK_ANIM);

                CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(
                        () -> this.swinging = false,
                        ATTACK_ANIM_TIME
                ));
            }
            return PlayState.CONTINUE;
        }));

        controllers.add(new AnimationController<>(this, "Movement", 10, (state) -> {
            if(this.isInWater()){
                if (this.swinging)
                    return PlayState.STOP;

                if (!state.isMoving()){
                    return state.setAndContinue(IDLE_ANIM_WATER);
                }

                else if (state.isMoving())
                    return state.setAndContinue(MOVE_ANIM_WATER);

                return PlayState.STOP;
            } else {
                if (this.swinging)
                    return PlayState.STOP;

                if (!state.isMoving())
                    return state.setAndContinue(IDLE_ANIM);

                else if (state.isMoving())
                    return state.setAndContinue(MOVE_ANIM);

                return PlayState.STOP;
            }
        }));

        controllers.add(new AnimationController<>(this, "Die", 0, (state) -> {
            if (state.getAnimatable().isDeadOrDying()) {
                if (this.isInWater())
                    return state.setAndContinue(DIE_ANIM_WATER);

                return state.setAndContinue(DIE_ANIM);
            }

            return PlayState.STOP;
        }));
    }


    //Misc
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    @Override
    protected void tickDeath() {
        CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(super::tickDeath, 40));
    }

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return false;
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return new ItemStack(MTSItems.BUNYIP_SPAWN_EGG.get());
    }
}
