package com.cerbon.myths_of_the_sea.util.fabric;

import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
public class MTSUtilsImpl {

    public static boolean isModLoaded(String modId){return FabricLoader.getInstance().isModLoaded(modId);}
}
