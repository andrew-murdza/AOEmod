package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(SpreadingSnowyDirtBlock.class)
public abstract class SpreadableBlockMixin {

    @ModifyConstant(method = "randomTick", constant = @Constant(intValue = 4))
    public int randomTick1(int constant, BlockState state, ServerLevel world, BlockPos pos, Random random){
        int score= BiomeScores.getScoreBlock(world,pos,(Block) (Object)this);
        int[] counts=new int[]{0,2,4,6,8};
        return counts[score];
    }

    @Redirect(method = "canBeGrass",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/lighting/LayerLightEngine;getLightBlockInto(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;I)I"))
    private static int stayAlive(BlockGetter world, BlockState state1, BlockPos pos1, BlockState state2, BlockPos pos2, Direction direction, int blockLight) {
        boolean b=state2.is(BlockTags.LOGS_THAT_BURN)|| state2.is(Blocks.MUSHROOM_STEM);
        return b?0: LayerLightEngine.getLightBlockInto(world, state1, pos1, state2, pos2, direction, blockLight);
    }

    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target ="Lnet/minecraft/server/level/ServerLevel;getMaxLocalRawBrightness(Lnet/minecraft/core/BlockPos;)I"))
    protected int getLight(ServerLevel instance, BlockPos blockPos){
        return instance.getMaxLocalRawBrightness(blockPos);
    }
}
