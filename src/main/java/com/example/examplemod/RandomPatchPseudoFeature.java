package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

import java.util.Random;

public class RandomPatchPseudoFeature {

    public static void generate(int xzSpread, int count, int maxTries, int x, int y, int z, WorldGenLevel level, ChunkGenerator chunkGenerator, ConfiguredFeature feature){
        int i = 0;
        int tries=0;
        Random random=level.getRandom();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int j = xzSpread+1;
        BlockPos center=new BlockPos(x,y,z);

        while(i<count&&tries<maxTries){
            int x1=random.nextInt(j) - random.nextInt(j);
            int z1=random.nextInt(j) - random.nextInt(j);
            int y1=getY(x1+x,y,z1+z);
            blockpos$mutableblockpos.setWithOffset(center,x1,y1,z1);
            if(feature.place(level,chunkGenerator,random, blockpos$mutableblockpos)){
                i++;
            }
            tries++;
        }
    }
    private static int getY(int x, int y, int z){
        return 0;
    }

}
