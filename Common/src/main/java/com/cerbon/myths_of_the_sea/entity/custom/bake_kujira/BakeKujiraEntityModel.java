package com.cerbon.myths_of_the_sea.entity.custom.bake_kujira;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BakeKujiraEntityModel extends DefaultedEntityGeoModel<BakeKujiraEntity> {

    public BakeKujiraEntityModel() {
        super(new ResourceLocation(MythsOfTheSea.MOD_ID, "bake_kujira"));
    }
}
