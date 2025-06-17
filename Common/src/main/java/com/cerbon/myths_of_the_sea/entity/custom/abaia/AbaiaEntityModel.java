package com.cerbon.myths_of_the_sea.entity.custom.abaia;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class AbaiaEntityModel extends DefaultedEntityGeoModel<AbaiaEntity> {

    public AbaiaEntityModel() {
        super(new ResourceLocation(MythsOfTheSea.MOD_ID, "abaia"));
    }
}
