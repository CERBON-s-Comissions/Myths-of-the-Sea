package com.cerbon.myths_of_the_sea.entity.custom.bake_kujira;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.myths_of_the_sea.MTSConfig;
import com.cerbon.myths_of_the_sea.entity.custom.abaia.goal.AbaiaMeleeAttackGoal;
import com.cerbon.myths_of_the_sea.entity.custom.util.BreachingWaterBoundPathNavigation;
import com.cerbon.myths_of_the_sea.util.GeoControllersUtil;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BakeKujiraEntity extends WaterAnimal implements GeoEntity, MultipartAwareEntity, Enemy {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    private final BakeKujiraHitboxes hitboxManager = new BakeKujiraHitboxes(this);

    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
    private static final int ATTACK_ANIM_TIME = 21;

    public BakeKujiraEntity(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.xpReward=20;
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public int getExperienceReward() {
        return this.xpReward;
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

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        boolean raining = level.getLevelData().isRaining();
        final long time = level.getLevelData().getDayTime() % 24000; boolean night = time >= 13000 && time < 23000;

        double willCancell = MTSConfig.bakeKujiraNormalSpawnProbability();
        if (raining) willCancell = MTSConfig.bakeKujiraRainSpawnProbability();
        else if (night) willCancell = MTSConfig.bakeKujiraNightSpawnProbability();

        //Random cancellation chance
        if (random.nextIntBetweenInclusive(0, 100) > willCancell) return false;

        int i = level.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    protected void registerGoals() {
//Unnecessary, doesn't breathe air like the dolphin
//        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new AbaiaMeleeAttackGoal(this, 2.0F, true, MTSSounds.BAKE_KUJIRA_ATTACK.get(), 15, 8));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new BreachingWaterBoundPathNavigation(this, level);
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

//        if(this.level() instanceof ServerLevel serverLevel)
//            MTSUtils.breakShip(this, serverLevel, 5);
        //Is an undead fish, I think it makes more sense if it doesn't breathe
        this.setAirSupply(this.getMaxAirSupply());


        //Make it jump out of the water like a fish
        if (this.onGround() && !this.isInWater() && !this.isDeadOrDying()) {
            this.setDeltaMovement(
                    this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5, (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)
            );
            this.setYRot(this.random.nextFloat() * 360.0F);
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
    }

    @Override
    protected void tickDeath() {
        CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(super::tickDeath, 40));
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

    public SoundEvent getFlopSound(){
        return MTSSounds.BAKE_KUJIRA_FLOP.get();
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
        controllers.add(new AnimationController<>(this, "Attack", 5, (state -> {
            if (this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
                state.getController().forceAnimationReset();
                state.getController().setAnimation(ATTACK_ANIM);

                CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(
                        () -> this.swinging = false,
                        ATTACK_ANIM_TIME
                ));
            }
            return PlayState.CONTINUE;
        })));

        controllers.add(new AnimationController<>(this, "Movement", 10, (state) -> GeoControllersUtil.commonMoveController(state, this)));

        controllers.add(new AnimationController<>(this, "Die", 0, state -> GeoControllersUtil.commonWaterAnimalDie(state, this)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return new ItemStack(MTSItems.BAKE_KUJIRA_SPAWN_EGG.get());
    }
}
