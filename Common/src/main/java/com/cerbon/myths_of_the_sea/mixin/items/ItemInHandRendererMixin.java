package com.cerbon.myths_of_the_sea.mixin.items;

import com.cerbon.myths_of_the_sea.util.MTSUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    //This makes the animation on first person

    @Shadow @Final private Minecraft minecraft;

    @Shadow private float oMainHandHeight;

    @Shadow private float mainHandHeight;

    @ModifyVariable(method = "renderArmWithItem", at = @At(value = "HEAD"), ordinal = 2, argsOnly = true)
    private float mtg$modifySwingsForDualwield(float swing, @Local(argsOnly = true) AbstractClientPlayer player, @Local(argsOnly = true, ordinal = 0) float partialTicks){
        if(this.minecraft.player==null)
            return swing;

        if(MTSUtils.hasDualwieldWeapon(this.minecraft.player)){
            return player.getAttackAnim(partialTicks);
        }

        return swing;
    }

    //To sync attack speed
    @ModifyVariable(method = "renderHandsWithItems", at = @At(value = "STORE", ordinal = 0), ordinal = 6)
    private float mtg$modifyHeightRightForDualwield(float height, @Local InteractionHand interactionHand, @Local(argsOnly = true) float partialTicks){
        if(this.minecraft.player==null)
            return height;

        if(MTSUtils.hasDualwieldWeapon(this.minecraft.player)){
            return 1.0F - Mth.lerp(partialTicks, this.oMainHandHeight, this.mainHandHeight);
        }

        return height;
    }

    @ModifyVariable(method = "renderHandsWithItems", at = @At(value = "STORE", ordinal = 1), ordinal = 6)
    private float mtg$modifyHeightLeftForDualwield(float height, @Local InteractionHand interactionHand, @Local(argsOnly = true) float partialTicks){
        if(this.minecraft.player==null)
            return height;

        if(MTSUtils.hasDualwieldWeapon(this.minecraft.player)){
            return 1.0F - Mth.lerp(partialTicks, this.oMainHandHeight, this.mainHandHeight);
        }

        return height;
    }
}
