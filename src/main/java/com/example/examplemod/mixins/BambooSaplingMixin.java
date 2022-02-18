package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(BambooSaplingBlock.class)
public class BambooSaplingMixin {

    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I"))
    private int nextInt(Random random, int n, BlockState state, ServerLevel world, BlockPos pos){
        int score= BiomeScores.getScoreBlock(world,pos,(Block) (Object)this);
        return Helper.cancelBlock((Block) (Object)this,score)?1: random.nextInt(n);
    }
}