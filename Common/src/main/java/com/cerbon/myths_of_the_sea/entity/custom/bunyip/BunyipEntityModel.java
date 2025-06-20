package com.cerbon.myths_of_the_sea.entity.custom.bunyip;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BunyipEntityModel extends DefaultedEntityGeoModel<BunyipEntity> {

    public BunyipEntityModel() {
        super(new ResourceLocation(MythsOfTheSea.MOD_ID, "bunyip"));
    }
}
