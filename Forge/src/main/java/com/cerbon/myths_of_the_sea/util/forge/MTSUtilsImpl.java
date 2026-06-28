package com.cerbon.myths_of_the_sea.util.forge;

import net.minecraftforge.fml.ModList;

@SuppressWarnings("unused")
public class MTSUtilsImpl {

    public static boolean isModLoaded(String modId){return ModList.get().isLoaded(modId);}
}
