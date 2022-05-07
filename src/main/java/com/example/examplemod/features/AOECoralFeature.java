package com.example.examplemod.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public abstract class AOECoralFeature extends Feature<NoneFeatureConfiguration> {

    public BlockState coral;

    protected abstract boolean placeFeature(LevelAccessor pLevel, Random pRandom, BlockPos pPos);

    public AOECoralFeature(Codec<NoneFeatureConfiguration> codec, Block coralBlock) {
        super(codec);
        coral=coralBlock.defaultBlockState();
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        Random random = pContext.random();
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        return this.placeFeature(worldgenlevel, random, blockpos);
    }

    protected boolean placeCoralBlock(LevelAccessor pLevel, Random pRandom, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        BlockState blockstate = pLevel.getBlockState(pPos);
        if ((blockstate.is(Blocks.WATER) || blockstate.is(BlockTags.CORALS)) && pLevel.getBlockState(blockpos).is(Blocks.WATER)) {
            pLevel.setBlock(pPos, coral, 3);
            float chance=pRandom.nextFloat();
            if (chance < 0.7F) {
                pLevel.setBlock(blockpos, BlockTags.CORALS.getRandomElement(pRandom).defaultBlockState(), 2);
            } else if (chance < 0.95F) {
                pLevel.setBlock(blockpos, Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.PICKLES, 4), 2);
            }else{
                pLevel.setBlock(blockpos,Blocks.WET_SPONGE.defaultBlockState(),2);
            }

            for(Direction direction : Direction.Plane.HORIZONTAL) {
                if (pRandom.nextFloat() < 0.5F) {
                    BlockPos blockpos1 = pPos.relative(direction);
                    if (pLevel.getBlockState(blockpos1).is(Blocks.WATER)) {
                        BlockState blockstate1 = BlockTags.WALL_CORALS.getRandomElement(pRandom).defaultBlockState();
                        if (blockstate1.hasProperty(BaseCoralWallFanBlock.FACING)) {
                            blockstate1 = blockstate1.setValue(BaseCoralWallFanBlock.FACING, direction);
                        }

                        pLevel.setBlock(blockpos1, blockstate1, 2);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
