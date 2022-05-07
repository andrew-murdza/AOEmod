package com.example.examplemod.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.CoralFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AOECoralClawFeature extends AOECoralFeature {

    public AOECoralClawFeature(Codec<NoneFeatureConfiguration> codec, Block coralBlock) {
        super(codec, coralBlock);
    }

    protected boolean placeFeature(LevelAccessor p_65424_, Random p_65425_, BlockPos p_65426_) {
        if (!this.placeCoralBlock(p_65424_, p_65425_, p_65426_)) {
            return false;
        } else {
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(p_65425_);
            int i = p_65425_.nextInt(2) + 2;
            List<Direction> list = Lists.newArrayList(direction, direction.getClockWise(), direction.getCounterClockWise());
            Collections.shuffle(list, p_65425_);

            for(Direction direction1 : list.subList(0, i)) {
                BlockPos.MutableBlockPos blockpos$mutableblockpos = p_65426_.mutable();
                int j = p_65425_.nextInt(2) + 1;
                blockpos$mutableblockpos.move(direction1);
                int k;
                Direction direction2;
                if (direction1 == direction) {
                    direction2 = direction;
                    k = p_65425_.nextInt(3) + 2;
                } else {
                    blockpos$mutableblockpos.move(Direction.UP);
                    Direction[] adirection = new Direction[]{direction1, Direction.UP};
                    direction2 = Util.getRandom(adirection, p_65425_);
                    k = p_65425_.nextInt(3) + 3;
                }

                for(int l = 0; l < j && this.placeCoralBlock(p_65424_, p_65425_, blockpos$mutableblockpos); ++l) {
                    blockpos$mutableblockpos.move(direction2);
                }

                blockpos$mutableblockpos.move(direction2.getOpposite());
                blockpos$mutableblockpos.move(Direction.UP);

                for(int i1 = 0; i1 < k; ++i1) {
                    blockpos$mutableblockpos.move(direction);
                    if (!this.placeCoralBlock(p_65424_, p_65425_, blockpos$mutableblockpos)) {
                        break;
                    }

                    if (p_65425_.nextFloat() < 0.25F) {
                        blockpos$mutableblockpos.move(Direction.UP);
                    }
                }
            }

            return true;
        }
    }
}
