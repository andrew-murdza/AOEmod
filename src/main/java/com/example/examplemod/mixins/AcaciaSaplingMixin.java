package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.features.JungleTreesFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.AcaciaTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(AcaciaTreeGrower.class)
public abstract class AcaciaSaplingMixin extends AbstractTreeGrowerMixin {
    @Override
    public ConfiguredFeature<?, ?> hi(AbstractTreeGrower instance, Random random, boolean b, ServerLevel pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRandom){
        int score= BiomeScores.getScoreBlock(pLevel,pPos, Blocks.ACACIA_SAPLING);
        return score>=4? JungleTreesFeature.ACACIA_TREE: TreeFeatures.ACACIA;
    }
}
