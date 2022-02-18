package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(BuddingAmethystBlock.class)
public class BuddingAmethystMixin {

    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I"))
    public int nextInt(Random random, int n, BlockState state, ServerLevel world, BlockPos pos, Random random1) {
        int[] chances=new int[]{0,0,0,0,2};
        int score= BiomeScores.getScoreBlock(world,pos,(Block)(Object)this);
        return Helper.nextInt(chances,score);
    }
}