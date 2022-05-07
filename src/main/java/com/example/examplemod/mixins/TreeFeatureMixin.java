package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;
import java.util.function.BiConsumer;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {

    @Redirect(method = "doPlace",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/feature/trunkplacers/TrunkPlacer;getTreeHeight(Ljava/util/Random;)I"))
    public int getHeight(TrunkPlacer instance, Random pRandom, WorldGenLevel world, Random pRandom1, BlockPos pos, BiConsumer<BlockPos, BlockState> pTrunkBlockSetter, BiConsumer<BlockPos, BlockState> pFoliageBlockSetter, TreeConfiguration pConfig) {
        int score = BiomeScores.getScoreBlock1(world, pos, Blocks.OAK_SAPLING);
        int[] shifts = new int[]{0, 0, 1, 3, 4};
        int scale = 1;
        int[] deltas=new int[]{0,0,1,2,2};
        int height = instance.getTreeHeight(pRandom);
        if (instance instanceof FancyTrunkPlacer) {
            scale = 3;
        }
//        else if(instance instanceof BendingTrunkPlacer){//Azalea
//
//        }
//        else if(instance instanceof DarkOakTrunkPlacer){
//
//        }
        else if (instance instanceof MegaJungleTrunkPlacer) {
            scale = 2;
        } else if (instance instanceof GiantTrunkPlacer) {//Giant Spruce or Giant Jungle
            scale = 2;
        }
//        else if(instance instanceof StraightTrunkPlacer){//Birch, Jungle, Oak, Spruce
//
//        }
        else if (instance instanceof ForkingTrunkPlacer) {//Acacia
            scale = 0;
        }
        if(instance instanceof  StraightTrunkPlacer&&pConfig.foliageProvider.getState(pRandom,pos).getBlock()==Blocks.SPRUCE_LEAVES){
            height=height+deltas[score];
        }
        return height + shifts[score] * scale;
    }
}
