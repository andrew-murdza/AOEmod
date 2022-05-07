package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.features.JungleTreesFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.JungleTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(JungleTreeGrower.class)
public abstract class JungleSaplingMixin extends AbstractMegaTreeGrowerMixin{
    @Override
    public ConfiguredFeature<?, ?> hi(AbstractTreeGrower instance, Random random, boolean b, ServerLevel pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRandom){
        int score= BiomeScores.getScoreBlock(pLevel,pPos, Blocks.JUNGLE_SAPLING);
        return score>=4?JungleTreesFeature.JUNGLE_TREE:TreeFeatures.JUNGLE_TREE;
    }

    @Override
    public ConfiguredFeature<?, ?> hi1(AbstractMegaTreeGrower instance, Random random, ServerLevel pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRandom, int pBranchX, int pBranchY){
        int score= BiomeScores.getScoreBlock(pLevel,pPos, Blocks.DARK_OAK_SAPLING);
        return score>=4? JungleTreesFeature.GIANT_JUNGLE_TREE:TreeFeatures.MEGA_JUNGLE_TREE;
    }
}
