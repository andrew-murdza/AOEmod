package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(TurtleEggBlock.class)
public class TurtleEggMixin {

    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/TurtleEggBlock;shouldUpdateHatchLevel(Lnet/minecraft/world/level/Level;)Z"))
    private boolean shouldHatchProgress(TurtleEggBlock instance, Level pLevel, BlockState pState, ServerLevel level, BlockPos pPos, Random pRandom){
        int score= BiomeScores.getScoreBlock(pLevel, pPos, Blocks.TURTLE_EGG);
        return score>=4&& Helper.nextInt(5)==0;
    }

    //Prevent Turtles from laying eggs in low score biomes
    @Redirect(method = "isSand",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/Tag;)Z"))
    private static boolean isSand1(BlockState state, Tag tag, BlockGetter pReader, BlockPos pPos){
        return state.is(Blocks.GRASS_BLOCK)||state.is(Blocks.MOSS_BLOCK)||state.is(BlockTags.SAND);
    }

    @Redirect(method = "stepOn",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/TurtleEggBlock;destroyEgg(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;I)V"))
    private void protectEgg(TurtleEggBlock instance, Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, int pChance){}

}