package com.example.examplemod.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class AOECoralMushroomFeature extends AOECoralFeature {

    public AOECoralMushroomFeature(Codec<NoneFeatureConfiguration> codec, Block coralBlock) {
        super(codec, coralBlock);
    }

    @Override
    protected boolean placeFeature(LevelAccessor pLevel, Random pRandom, BlockPos pPos) {
        int i = pRandom.nextInt(3) + 3;
        int j = pRandom.nextInt(3) + 3;
        int k = pRandom.nextInt(3) + 3;
        int l = pRandom.nextInt(3) + 1;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        for(int i1 = 0; i1 <= j; ++i1) {
            for(int j1 = 0; j1 <= i; ++j1) {
                for(int k1 = 0; k1 <= k; ++k1) {
                    blockpos$mutableblockpos.set(i1 + pPos.getX(), j1 + pPos.getY(), k1 + pPos.getZ());
                    blockpos$mutableblockpos.move(Direction.DOWN, l);
                    if ((i1 != 0 && i1 != j || j1 != 0 && j1 != i) && (k1 != 0 && k1 != k || j1 != 0 && j1 != i) && (i1 != 0 && i1 != j || k1 != 0 && k1 != k) && (i1 == 0 || i1 == j || j1 == 0 || j1 == i || k1 == 0 || k1 == k) && !(pRandom.nextFloat() < 0.1F) && !this.placeCoralBlock(pLevel, pRandom, blockpos$mutableblockpos)) {
                    }
                }
            }
        }

        return true;
    }
}
