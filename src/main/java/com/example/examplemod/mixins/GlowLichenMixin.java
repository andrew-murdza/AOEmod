package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(GlowLichenBlock.class)
public class GlowLichenMixin {

    @Inject(method = "performBonemeal",cancellable = true,at=@At("HEAD"))
    public void grow(ServerLevel world, Random random, BlockPos pos, BlockState state, CallbackInfo info) {
        GlowLichenBlock block=(GlowLichenBlock) (Object) this;
        int score= BiomeScores.getScoreBlock(world,pos,block);
        int[] tries=new int[]{0,0,1,2,4};
        for(int i=0;i<tries[score];i++){
            block.spreadFromRandomFaceTowardRandomDirection(state, world, pos, random);
        }
    }
}