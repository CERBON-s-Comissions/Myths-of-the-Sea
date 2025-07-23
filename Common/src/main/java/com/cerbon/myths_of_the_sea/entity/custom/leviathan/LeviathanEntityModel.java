package com.cerbon.myths_of_the_sea.entity.custom.leviathan;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class LeviathanEntityModel extends DefaultedEntityGeoModel<LeviathanEntity> {
    public LeviathanEntityModel() {
        super(new ResourceLocation(MythsOfTheSea.MOD_ID, "leviathan"));
    }

    @Override
    public void setCustomAnimations(LeviathanEntity leviathan, long instanceId, AnimationState<LeviathanEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        CoreGeoBone fullBody = getAnimationProcessor().getBone("fullBody");
        CoreGeoBone serpentBody = getAnimationProcessor().getBone("serpentBody");


        CoreGeoBone[] body = {
                getAnimationProcessor().getBone("body1"),
                getAnimationProcessor().getBone("body2"),
                getAnimationProcessor().getBone("body3"),
                getAnimationProcessor().getBone("body4"),
                getAnimationProcessor().getBone("body5"),
                getAnimationProcessor().getBone("body6"),
                getAnimationProcessor().getBone("body7"),
                getAnimationProcessor().getBone("body8"),
        };


        //To being able to play the death animation correctly
        if(leviathan.getHealth()<=0){
            return;
        }

        body[1].setRotY((float) Math.toRadians(leviathan.getYRot2()));
        body[2].setRotY((float) Math.toRadians(leviathan.getYRot3()));
        body[3].setRotY((float) Math.toRadians(leviathan.getYRot4()));
        body[4].setRotY((float) Math.toRadians(leviathan.getYRot5()));
        body[5].setRotY((float) Math.toRadians(leviathan.getYRot6()));
        body[6].setRotY((float) Math.toRadians(leviathan.getYRot7()));
        body[7].setRotY((float) Math.toRadians(leviathan.getYRot8()));

//        serpentBody.setRotX((float) Math.toRadians(0));
//        body[0].setRotX((float) Math.toRadians(0));
//        body[1].setRotX((float) Math.toRadians(0));
//        body[2].setRotX((float) Math.toRadians(0));
//        body[3].setRotX((float) Math.toRadians(0));
//        body[4].setRotX((float) Math.toRadians(0));
//        body[5].setRotX((float) Math.toRadians(0));
//        body[6].setRotX((float) Math.toRadians(0));
//        body[7].setRotX((float) Math.toRadians(0));
    }
}
