package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.Helper;
import com.example.examplemod.features.JungleTreesFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.BirchTreeGrower;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(BirchTreeGrower.class)
public abstract class BirchSaplingMixin extends AbstractTreeGrowerMixin {


    @Override
    public ConfiguredFeature<?, ?> hi(AbstractTreeGrower instance, Random random, boolean b, ServerLevel pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRandom){
        int score= BiomeScores.getScoreBlock(pLevel,pPos, Blocks.OAK_SAPLING);
        if(score>=4){
            return JungleTreesFeature.BIRCH_TREE;
        }
        return b ? TreeFeatures.BIRCH_BEES_005 : TreeFeatures.BIRCH;
    }

}
