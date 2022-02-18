package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneMixin {
    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Random;nextFloat()F",ordinal = 0))
    private float nextFloat(Random random, BlockState state, ServerLevel world, BlockPos pos){
        int score= BiomeScores.getScoreBlock(world,pos,(Block) (Object)this);
        if(score<3){
            return 1;
        }
        else if(score==4){
            return 0.1F*random.nextFloat();
        }
        return 0.025F*random.nextFloat();
    }
    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Random;nextFloat()F",ordinal = 1))
    private float nextFloat1(Random random, BlockState state, ServerLevel world, BlockPos pos){
        int score= BiomeScores.getScoreBlock(world,pos,(Block) (Object)this);
        float[] multipliers=new float[]{1F,1F,0.25F,0.05F,0.01F};
        return random.nextFloat()*multipliers[score];
    }
}
