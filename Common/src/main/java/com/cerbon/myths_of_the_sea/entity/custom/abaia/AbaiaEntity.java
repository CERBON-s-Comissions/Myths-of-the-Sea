package com.cerbon.myths_of_the_sea.entity.custom.abaia;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.goal.AbaiaMeleeAttackGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class AbaiaEntity extends WaterAnimal implements GeoEntity, MultipartAwareEntity, NeutralMob {
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(AbaiaEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable private UUID persistentAngerTarget;

    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    private final AbaiaHitboxes hitboxManager = new AbaiaHitboxes(this);

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation MOVE_ANIM = RawAnimation.begin().thenLoop("move");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation DIE_ANIM = RawAnimation.begin().thenPlayAndHold("die");

    public static final int ATTACK_ANIM_TIME = 21;

    public AbaiaEntity(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new AbaiaBodyRotationControl(this);
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new AbaiaMeleeAttackGoal(this, 2.0F, true));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.setAirSupply(this.getMaxAirSupply());
        this.setXRot(0.0F);
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public int getMaxAirSupply() {
        return 4800;
    }

    @Override
    protected int increaseAirSupply(int currentAir) {
        return this.getMaxAirSupply();
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return 0.85F;
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));

            if (this.getTarget() == null)
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));

        }
        else super.travel(travelVector);
    }

    @Override
    public boolean isInWall() {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide)
            this.updatePersistentAnger((ServerLevel) this.level(), true);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isNoAi())
            this.setAirSupply(this.getMaxAirSupply());
    }

    @Override
    protected void tickDeath() {
        CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(super::tickDeath, 40));
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, remainingPersistentAngerTime);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public EntityBounds getBounds() {
        return hitboxManager.getHitbox();
    }

    @Override
    public void onSetPos(double x, double y, double z) {
        if (hitboxManager != null) hitboxManager.updatePosition();
    }

    @Override
    public void setNextDamagedPart(@Nullable String part) {}

    @Override
    public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) {
        return hitboxManager.getHitbox().getBox(bounds);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Attack", 5, this::attackController));
        controllers.add(new AnimationController<>(this, "Movement", 10, this::movementController));
        controllers.add(new AnimationController<>(this, "Die", 0, this::dieController));
    }

    protected <E extends AbaiaEntity> PlayState movementController(final AnimationState<E> event) {
        if (this.swinging)
            return PlayState.STOP;

        if (!event.isMoving())
            return event.setAndContinue(IDLE_ANIM);

        else if (event.isMoving())
            return event.setAndContinue(MOVE_ANIM);

        return PlayState.STOP;
    }

    protected <E extends AbaiaEntity> PlayState dieController(final AnimationState<E> event) {
        if (event.getAnimatable().isDeadOrDying())
            return event.setAndContinue(DIE_ANIM);

        return PlayState.STOP;
    }

    protected <E extends AbaiaEntity> PlayState attackController(final AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(ATTACK_ANIM);

            CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(
                    () -> this.swinging = false,
                    ATTACK_ANIM_TIME
            ));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
