package com.cerbon.myths_of_the_sea.platform;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.platform.custom.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {
    public static IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        MythsOfTheSea.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
