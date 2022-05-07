package com.example.examplemod.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AOECoralTreeFeature extends AOECoralFeature {
    public AOECoralTreeFeature(Codec<NoneFeatureConfiguration> codec, Block coralBlock) {
        super(codec, coralBlock);
    }

    @Override
    protected boolean placeFeature(LevelAccessor pLevel, Random pRandom, BlockPos pPos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();
        int i = pRandom.nextInt(3) + 1;

        for(int j = 0; j < i; ++j) {
            if (!this.placeCoralBlock(pLevel, pRandom, blockpos$mutableblockpos)) {
                return true;
            }

            blockpos$mutableblockpos.move(Direction.UP);
        }

        BlockPos blockpos = blockpos$mutableblockpos.immutable();
        int k = pRandom.nextInt(3) + 2;
        List<Direction> list = Lists.newArrayList(Direction.Plane.HORIZONTAL);
        Collections.shuffle(list, pRandom);

        for(Direction direction : list.subList(0, k)) {
            blockpos$mutableblockpos.set(blockpos);
            blockpos$mutableblockpos.move(direction);
            int l = pRandom.nextInt(5) + 2;
            int i1 = 0;

            for(int j1 = 0; j1 < l && this.placeCoralBlock(pLevel, pRandom, blockpos$mutableblockpos); ++j1) {
                ++i1;
                blockpos$mutableblockpos.move(Direction.UP);
                if (j1 == 0 || i1 >= 2 && pRandom.nextFloat() < 0.25F) {
                    blockpos$mutableblockpos.move(direction);
                    i1 = 0;
                }
            }
        }

        return true;
    }
}
