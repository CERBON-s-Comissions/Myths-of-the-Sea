package com.cerbon.myths_of_the_sea.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class MTSConfigForge {

    public final ForgeConfigSpec.IntValue bakeKujiraNormalSpawnProbability;
    public final ForgeConfigSpec.IntValue bakeKujiraNightSpawnProbability;
    public final ForgeConfigSpec.IntValue bakeKujiraRainSpawnProbability;

    public final ForgeConfigSpec.IntValue leviathanNormalSpawnProbability;
    public final ForgeConfigSpec.IntValue leviathanNightSpawnProbability;
    public final ForgeConfigSpec.IntValue leviathanRainSpawnProbability;
    public final ForgeConfigSpec.IntValue leviathanThunderSpawnProbability;

    public final ForgeConfigSpec.IntValue leviathanNormalSpawnSeparationRadius;
    public final ForgeConfigSpec.IntValue leviathanNightSpawnSeparationRadius;
    public final ForgeConfigSpec.IntValue leviathanRainSpawnSeparationRadius;
    public final ForgeConfigSpec.IntValue leviathanThunderSpawnSeparationRadius;

    public final ForgeConfigSpec.IntValue krakenNormalSpawnProbability;
    public final ForgeConfigSpec.IntValue krakenRainSpawnProbability;
    public final ForgeConfigSpec.IntValue krakenThunderSpawnProbability;

    public MTSConfigForge(ForgeConfigSpec.Builder builder){
        //Mobs
        builder.comment("Mob Settings").push("mob");
        {
            // --- Spawn probabilities ---
            builder.push("bake_kujira");
            {
                //Normal  -> 35% to spawn
                //Night   -> 65% to spawn
                //Rain    -> 85% to spawn
                builder.comment("Modify probability spawn depending on climate");
                bakeKujiraNormalSpawnProbability  = builder.defineInRange("bakeKujiraNormalSpawnProbability",  35, 0, Integer.MAX_VALUE);
                bakeKujiraNightSpawnProbability   = builder.defineInRange("bakeKujiraNightSpawnProbability",   65, 0, Integer.MAX_VALUE);
                bakeKujiraRainSpawnProbability    = builder.defineInRange("bakeKujiraRainSpawnProbability",    85, 0, Integer.MAX_VALUE);
            }
            builder.pop();

            builder.push("leviathan");
            {
                //Normal  -> 7% to spawn
                //Night   -> 15% to spawn
                //Rain    -> 35% to spawn
                //Thunder -> 50% to spawn
                builder.comment("Modify probability spawn depending on climate");
                leviathanNormalSpawnProbability  = builder.defineInRange("leviathanNormalSpawnProbability",   7, 0, Integer.MAX_VALUE);
                leviathanNightSpawnProbability   = builder.defineInRange("leviathanNightSpawnProbability",   15, 0, Integer.MAX_VALUE);
                leviathanRainSpawnProbability    = builder.defineInRange("leviathanRainSpawnProbability",    35, 0, Integer.MAX_VALUE);
                leviathanThunderSpawnProbability = builder.defineInRange("leviathanThunderSpawnProbability", 50, 0, Integer.MAX_VALUE);

                //Normal  -> 120 radius
                //Night   -> 120 radius
                //Rain    -> 80 radius
                //Thunder -> 60 radius
                builder.comment("Modify how close to each other can leviathans spawn");
                leviathanNormalSpawnSeparationRadius  = builder.defineInRange("leviathanNormalSpawnSeparationRadius",  120, 0, Integer.MAX_VALUE);
                leviathanNightSpawnSeparationRadius   = builder.defineInRange("leviathanNightSpawnSeparationRadius",   120, 0, Integer.MAX_VALUE);
                leviathanRainSpawnSeparationRadius    = builder.defineInRange("leviathanRainSpawnSeparationRadius",    80,  0, Integer.MAX_VALUE);
                leviathanThunderSpawnSeparationRadius = builder.defineInRange("leviathanThunderSpawnSeparationRadius", 60,  0, Integer.MAX_VALUE);
            }
            builder.pop();

            builder.push("kraken");
            {
                //Normal  -> 3% to spawn
                //Rain    -> 10% to spawn
                //Thunder -> 15% to spawn
                builder.comment("Modify probability spawn depending on climate");
                krakenNormalSpawnProbability  = builder.defineInRange("krakenNormalSpawnProbability",   3, 0, Integer.MAX_VALUE);
                krakenRainSpawnProbability   = builder.defineInRange("krakenRainSpawnProbability",   10, 0, Integer.MAX_VALUE);
                krakenThunderSpawnProbability    = builder.defineInRange("krakenThunderSpawnProbability",    15, 0, Integer.MAX_VALUE);
            }
            builder.pop();
        }
    }
}
