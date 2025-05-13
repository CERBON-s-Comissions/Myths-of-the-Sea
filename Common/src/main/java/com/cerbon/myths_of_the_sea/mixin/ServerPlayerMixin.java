package com.cerbon.myths_of_the_sea.mixin;

import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.platform.Services;
import com.cerbon.myths_of_the_sea.util.mixin.IServerPlayerMixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements IServerPlayerMixin {
    @Unique private int mts$leviathanHeartsEaten = 0;
    @Unique private boolean mts$appliedRangeAttribute = false;

    @Unique private final String mts$leviathanHeartsEatenTag = "LeviathanHeartsEaten";

    @Unique private static final UUID TENTACLE_BLOCK_REACH_UUID = UUID.fromString("d6f76a9d-c416-4435-8d80-5564d3509cd6");
    @Unique private static final UUID TENTACLE_ENTITY_REACH_UUID = UUID.fromString("f9bf8abf-0ef1-4d5e-989c-984ba28fc63d");

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void mts$saveAdditional(CompoundTag tag, CallbackInfo ci) {
        tag.putInt(mts$leviathanHeartsEatenTag, mts$leviathanHeartsEaten);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void mts$readAdditional(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(mts$leviathanHeartsEatenTag))
            mts$leviathanHeartsEaten = tag.getInt(mts$leviathanHeartsEatenTag);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void mts$tick(CallbackInfo ci) {
        //Kraken tentacle ability
        if (self().level().getGameTime() % 10 != 0) return;

        AttributeInstance blockReach = self().getAttribute(Services.PLATFORM.blockReachAttribute());
        AttributeInstance entityReach = self().getAttribute(Services.PLATFORM.entityReachAttribute());
        if (blockReach == null || entityReach == null) return;

        boolean holdingTentacle = self().getInventory().getSelected().is(MTSItems.KRAKEN_TENTACLE.get()) || Services.PLATFORM.getItemFromCuriosSlot(self(), MTSItems.KRAKEN_TENTACLE.get()) != ItemStack.EMPTY;

        if (holdingTentacle && !mts$appliedRangeAttribute) {
            blockReach.addTransientModifier(new AttributeModifier(
                    TENTACLE_BLOCK_REACH_UUID,
                    "kraken_tentacle_block_reach",
                    3.0D,
                    AttributeModifier.Operation.ADDITION)
            );
            entityReach.addTransientModifier(new AttributeModifier(
                    TENTACLE_ENTITY_REACH_UUID,
                    "kraken_tentacle_entity_reach",
                    3.0D,
                    AttributeModifier.Operation.ADDITION
            ));
            mts$appliedRangeAttribute = true;
        }

        if (!holdingTentacle && mts$appliedRangeAttribute) {
            blockReach.removeModifier(TENTACLE_BLOCK_REACH_UUID);
            entityReach.removeModifier(TENTACLE_ENTITY_REACH_UUID);
            mts$appliedRangeAttribute = false;
        }
    }

    @Unique
    private ServerPlayer self() {
        return (ServerPlayer) (Object) this;
    }

    @Override
    public int mts$getLeviathanHeartsEaten() {
        return mts$leviathanHeartsEaten;
    }

    @Override
    public void mts$increaseLeviathanHeartsEaten() {
        mts$leviathanHeartsEaten++;
    }
}
