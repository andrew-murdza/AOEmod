package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.Helper;
import com.example.examplemod.features.AOEConfiguredFeatures;
import com.example.examplemod.features.JungleTreesFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(OakTreeGrower.class)
public abstract class OakSaplingMixin extends AbstractTreeGrowerMixin {

    @Override
    public ConfiguredFeature<?, ?> hi(AbstractTreeGrower instance, Random random, boolean b, ServerLevel pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRandom){
        int score= BiomeScores.getScoreBlock(pLevel,pPos, Blocks.OAK_SAPLING);
        if (Helper.chance(new int[]{0,0,10,5,2},score)) {
            if(score>=4){
                return JungleTreesFeature.LARGE_OAK_TREE;
            }
            return b ? TreeFeatures.FANCY_OAK_BEES_005 : TreeFeatures.FANCY_OAK;
        } else {
            if(score>=4){
                return JungleTreesFeature.OAK_TREE;
            }
            return b ? TreeFeatures.OAK_BEES_005 : TreeFeatures.OAK;
        }
    }

//    @Redirect(method = "generate",
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/block/sapling/SaplingGenerator;getTreeFeature(Ljava/util/Random;Z)Lnet/minecraft/world/gen/feature/ConfiguredFeature;"))
//
//    @Nullable
//    protected ConfiguredFeature<?, ?> getTreeFeature(SaplingGenerator instance, Random random, boolean b, ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random1){
//        int score=BiomeScores.getScoreBlock(world,pos, Blocks.OAK_SAPLING);
//        int[] chances=new int[]{10,10,5,3,2};
//        if(random.nextInt(chances[score]) == 0){
//            return b? TreeConfiguredFeatures.FANCY_OAK_BEES_005:TreeConfiguredFeatures.FANCY_OAK;
//        }
//        return b?TreeConfiguredFeatures.OAK_BEES_005 : TreeConfiguredFeatures.OAK;
//    }
}
