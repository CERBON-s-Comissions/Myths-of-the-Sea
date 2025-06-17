package com.cerbon.myths_of_the_sea.entity.custom.bake_kujira;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.goal.AbaiaMeleeAttackGoal;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

public class BakeKujiraEntity extends WaterAnimal implements GeoEntity, MultipartAwareEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    private final BakeKujiraHitboxes hitboxManager = new BakeKujiraHitboxes(this);

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation MOVE_ANIM = RawAnimation.begin().thenLoop("move");
    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation DIE_ANIM = RawAnimation.begin().thenPlayAndHold("die");
    private static final int ATTACK_ANIM_TIME = 21;

    public BakeKujiraEntity(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F)
                .add(Attributes.FOLLOW_RANGE, 48F)
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
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new BakeKujiraBodyRotationControl(this);
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
    public boolean doHurtTarget(@NotNull Entity target) {
        if (this.level() instanceof ServerLevel serverLevel)
            SoundUtils.playSound(serverLevel, this.position(), MTSSounds.BAKE_KUJIRA_ATTACK.get(), SoundSource.HOSTILE, 3F, 6D);

        return super.doHurtTarget(target);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MTSSounds.BAKE_KUJIRA_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        return MTSSounds.BAKE_KUJIRA_MOVEMENT.get();
    }

    @Override
    protected void playSwimSound(float volume) {
        float f = (float) this.getDeltaMovement().horizontalDistance();

        if (f >= 0.01F)
            super.playSwimSound(Mth.lerp(Mth.clamp(f, 0.0F, 0.5F), 0.0F, 1.2F));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return MTSSounds.BAKE_KUJIRA_DAMAGE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MTSSounds.BAKE_KUJIRA_DEATH.get();
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

    protected <E extends BakeKujiraEntity> PlayState movementController(final AnimationState<E> event) {
        if (this.swinging)
            return PlayState.STOP;

        if (!event.isMoving())
            return event.setAndContinue(IDLE_ANIM);

        else if (event.isMoving())
            return event.setAndContinue(MOVE_ANIM);

        return PlayState.STOP;
    }

    protected <E extends BakeKujiraEntity> PlayState dieController(final AnimationState<E> event) {
        if (event.getAnimatable().isDeadOrDying())
            return event.setAndContinue(DIE_ANIM);

        return PlayState.STOP;
    }

    protected <E extends BakeKujiraEntity> PlayState attackController(final AnimationState<E> event) {
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
