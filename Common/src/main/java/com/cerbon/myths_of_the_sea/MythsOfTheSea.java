package com.cerbon.myths_of_the_sea;

import com.cerbon.myths_of_the_sea.creative_tab.MTSCreativeModeTabs;
import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.potion.MTSPotions;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class MythsOfTheSea {
	public static final String MOD_ID = "myths_of_the_sea";
	public static final String MOD_NAME = "MythsOfTheSea";

	public static final Logger LOGGER = LogUtils.getLogger();

	public static void init() {
		MTSItems.register();
		MTSCreativeModeTabs.register();

		MTSEntities.register();

		MTSPotions.register();
		MTSSounds.register();
	}
}
