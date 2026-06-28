package com.cerbon.myths_of_the_sea.worldgen.spawn;

import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.LeviathanEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LeviathanSpawner implements CustomSpawner {

    private int cooldown;

    @Override
    public int tick(@NotNull ServerLevel level, boolean spawnEnemies, boolean spawnFriendlies) {
        //If there's a player
        final int i = level.players().size();
        if (i < 1) {
            return 0;
        }

        final RandomSource random = level.getRandom();
        --this.cooldown;
        if (this.cooldown > 0) {
            return 0;
        }

        this.cooldown += 2 + random.nextInt(2);

        int numberOfSpawns = 0;

        final Player playerEntity = level.players().get(random.nextInt(i));
        //If the player isn't in spectator mode
        if (playerEntity.isSpectator()) {
            return 0;
        }

        //From 24-48 blocks away the player
        final int spawnX = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        //From 24-88 blocks away the player
        int spawnY = (24 + random.nextInt(64)) * (random.nextBoolean() ? -1 : 1);

        //To avoid too many invalid numbers when a player it's very deep
        if (spawnY < -60) {
            //This makes any value under -60 to a range of (-60 to 4)
            spawnY = -60 + random.nextInt(64);
        }

        //From 24-48 blocks away the player
        final int spawnZ = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        final BlockPos.MutableBlockPos mutable = playerEntity.blockPosition().mutable().move(spawnX, spawnY, spawnZ);

        if (level.isThundering() && level.isRainingAt(mutable)) {

            if(random.nextBoolean())
                if(spawnLeviathan(level, mutable, random))
                    numberOfSpawns++;


            return numberOfSpawns;
        } else if (level.isRaining() && level.isRainingAt(mutable)) {

            if(random.nextIntBetweenInclusive(0, 10)==1)
                if(spawnLeviathan(level, mutable, random))
                    numberOfSpawns++;

            return numberOfSpawns;
        } else if (level.isNight()) {

            if(random.nextIntBetweenInclusive(0, 50)==1)
                if(spawnLeviathan(level, mutable, random))
                    numberOfSpawns++;

            return numberOfSpawns;
        }


        return 0;
    }

    private boolean spawnLeviathan(@NotNull final ServerLevel level, final BlockPos pos, final RandomSource random) {
        final Holder<Biome> biome = level.getBiome(pos);
        final LeviathanEntity leviathanEntity = MTSEntities.LEVIATHAN.get().create(level);

        if (biome.is(Biomes.COLD_OCEAN) || biome.is(Biomes.DEEP_COLD_OCEAN)) {
            System.out.println("Check: "+LeviathanEntity.surfaceWaterSpawnRulesAndNotNearLeviathan(MTSEntities.LEVIATHAN.get(), level, MobSpawnType.EVENT, pos, random));
            System.out.println("Ok: "+NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.IN_WATER, level, level.getSharedSpawnPos(), MTSEntities.LEVIATHAN.get()));
            if (LeviathanEntity.surfaceWaterSpawnRulesAndNotNearLeviathan(MTSEntities.LEVIATHAN.get(), level, MobSpawnType.EVENT, pos, random)
                    && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.IN_WATER, level, level.getSharedSpawnPos(), MTSEntities.LEVIATHAN.get())) {

                //Spawn the leviathan
                if (leviathanEntity != null) {
                    leviathanEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
                    level.addFreshEntity(leviathanEntity);
                    System.out.println("Leviathan spawned at: "+pos.getX()+" "+pos.getY()+" "+pos.getZ());
                    return true;
                }
            }
        }

        return false;
    }

}