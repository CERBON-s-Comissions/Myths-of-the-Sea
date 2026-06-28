package com.cerbon.myths_of_the_sea;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class MTSConfig {

    @ExpectPlatform
    public static int bakeKujiraNormalSpawnProbability() { throw new AssertionError(); }
    @ExpectPlatform
    public static int bakeKujiraNightSpawnProbability() { throw new AssertionError(); }
    @ExpectPlatform
    public static int bakeKujiraRainSpawnProbability() { throw new AssertionError(); }

    @ExpectPlatform
    public static int leviathanNormalSpawnProbability() { throw new AssertionError();}
    @ExpectPlatform
    public static int leviathanNightSpawnProbability() { throw new AssertionError();}
    @ExpectPlatform
    public static int leviathanRainSpawnProbability() { throw new AssertionError();}
    @ExpectPlatform
    public static int leviathanThunderSpawnProbability() { throw new AssertionError();}

    @ExpectPlatform
    public static int leviathanNormalSpawnSeparationRadius() { throw new AssertionError();}
    @ExpectPlatform
    public static int leviathanNightSpawnSeparationRadius() { throw new AssertionError();}
    @ExpectPlatform
    public static int leviathanRainSpawnSeparationRadius() { throw new AssertionError();}
    @ExpectPlatform
    public static int leviathanThunderSpawnSeparationRadius() { throw new AssertionError();}

    @ExpectPlatform
    public static int krakenNormalSpawnProbability() { throw new AssertionError(); }
    @ExpectPlatform
    public static int krakenRainSpawnProbability() { throw new AssertionError(); }
    @ExpectPlatform
    public static int krakenThunderSpawnProbability() { throw new AssertionError(); }

}
