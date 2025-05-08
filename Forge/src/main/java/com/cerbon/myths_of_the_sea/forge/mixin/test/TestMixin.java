package com.cerbon.myths_of_the_sea.forge.mixin.test;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Class used to test if forge only mixins are being applied
@Mixin(Minecraft.class)
public abstract class TestMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void sendMessageIfWorking(GameConfig gameConfig, CallbackInfo ci) {
        MythsOfTheSea.LOGGER.info("Forge only mixins are working for {}!", MythsOfTheSea.MOD_NAME);
    }
}