package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(MushroomBlock.class)
public class MushroomMixin {
    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target ="Ljava/util/Random;nextInt(I)I",ordinal = 0))
    private int nextInt(Random random, int n, BlockState state, ServerLevel world, BlockPos pos, Random random1){
        int score= BiomeScores.getScoreBlock(world,pos,(Block) (Object)this);
        return Helper.nextInt(new int[]{25,25,15,10,5},score);
    }
    @ModifyConstant(method = "randomTick",constant = @Constant(intValue = 5))
    private int injected(int value,BlockState state, ServerLevel world, BlockPos pos, Random random1) {
        int[] maxAmounts=new int[]{5,5,7,9,12};
        int score=BiomeScores.getScoreBlock(world,pos,(Block)(Object)this);
        return maxAmounts[score];
    }
    @Redirect(method = "canSurvive",
            at = @At(value = "INVOKE",
                    target ="Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/Tag;)Z"))
    private boolean canPlaceAt(BlockState instance, Tag tag){
        return instance.is(tag)||instance.is(Blocks.MOSS_BLOCK);
    }
}
