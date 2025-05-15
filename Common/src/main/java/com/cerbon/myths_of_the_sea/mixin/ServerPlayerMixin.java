package com.cerbon.myths_of_the_sea.mixin;

import com.cerbon.myths_of_the_sea.util.mixin.IServerPlayerMixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements IServerPlayerMixin {
    @Unique private int mts$leviathanHeartsEaten = 0;
    @Unique private final String mts$leviathanHeartsEatenTag = "LeviathanHeartsEaten";

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void mts$saveAdditional(CompoundTag tag, CallbackInfo ci) {
        tag.putInt(mts$leviathanHeartsEatenTag, mts$leviathanHeartsEaten);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void mts$readAdditional(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(mts$leviathanHeartsEatenTag))
            mts$leviathanHeartsEaten = tag.getInt(mts$leviathanHeartsEatenTag);
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
