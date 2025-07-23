package com.cerbon.myths_of_the_sea.entity.custom.kraken;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class KrakenEntityModel extends DefaultedEntityGeoModel<KrakenEntity> {

    public KrakenEntityModel() {
        super(new ResourceLocation(MythsOfTheSea.MOD_ID, "kraken"));
    }

    @Override
    public void setCustomAnimations(KrakenEntity animatable, long instanceId, AnimationState<KrakenEntity> animationState) {


        CoreGeoBone tentacleRightA_1= getAnimationProcessor().getBone("tentacleRightA_1");
        CoreGeoBone tentacleRightA_2= getAnimationProcessor().getBone("tentacleRightA_2");
        CoreGeoBone tentacleRightA_3= getAnimationProcessor().getBone("tentacleRightA_3");
        CoreGeoBone tentacleRightA_4= getAnimationProcessor().getBone("tentacleRightA_4");
        CoreGeoBone tentacleRightA_5= getAnimationProcessor().getBone("tentacleRightA_5");

//        CoreGeoBone tentacleLeftA_1= getAnimationProcessor().getBone("tentacleLeftA_1");
//        CoreGeoBone tentacleLeftA_2= getAnimationProcessor().getBone("tentacleLeftA_2");
//        CoreGeoBone tentacleLeftA_3= getAnimationProcessor().getBone("tentacleLeftA_3");
//        CoreGeoBone tentacleLeftA_4= getAnimationProcessor().getBone("tentacleLeftA_4");
//        CoreGeoBone tentacleLeftA_5= getAnimationProcessor().getBone("tentacleLeftA_5");

        if(animatable.isVehicle()){

            tentacleRightA_1.updateRotation((float) Math.toRadians(-12f), (float) Math.toRadians(-25f), (float) Math.toRadians(35f));

            tentacleRightA_2.updateRotation((float) Math.toRadians(5f),   (float) Math.toRadians(0f), (float) Math.toRadians(30f));

            tentacleRightA_3.updateRotation(
                    (float) Math.toRadians(animationState.isMoving()? -115: 15f),
                    (float) Math.toRadians(animationState.isMoving()? -70:-32f),
                    (float) Math.toRadians(animationState.isMoving()? 235f: 65f));

            tentacleRightA_4.updateRotation((float) Math.toRadians(90f), (float) Math.toRadians(87f), (float) Math.toRadians(70f));
            tentacleRightA_5.updateRotation((float) Math.toRadians(0f), (float) Math.toRadians(147f), (float) Math.toRadians(0f));
        }
    }
}
