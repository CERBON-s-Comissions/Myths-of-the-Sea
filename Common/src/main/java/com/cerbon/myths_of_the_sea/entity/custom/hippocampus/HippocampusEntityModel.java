package com.cerbon.myths_of_the_sea.entity.custom.hippocampus;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class HippocampusEntityModel extends DefaultedEntityGeoModel<HippocampusEntity> {

    public HippocampusEntityModel() {
        super(new ResourceLocation(MythsOfTheSea.MOD_ID, "hippocampus"));
    }

}
