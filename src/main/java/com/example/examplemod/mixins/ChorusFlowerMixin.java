package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(ChorusFlowerBlock.class)
public class ChorusFlowerMixin {

    @ModifyArg(method = "randomTick", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/block/ChorusFlowerBlock;placeGrownFlower(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;I)V",ordinal = 1),index = 2)
    public int grow(Level world, BlockPos pos, int age){
        float[] chances=new float[]{0,0,0F,0,0.1F};
        int score= BiomeScores.getScoreBlock(world,pos, Blocks.CHORUS_FLOWER);
        return Helper.nextFloat(chances,score)==0?age-1:age;
    }
    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I",ordinal = 1))
    private int nextInt(Random random, int n, BlockState state, ServerLevel world, BlockPos pos, Random random1){
        int[] chances=new int[]{0,0,0,5,6};
        int score= BiomeScores.getScoreBlock(world,pos,Blocks.CHORUS_FLOWER);
        return random.nextInt(chances[score]);
    }
    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"))
    private Comparable getAge(BlockState instance, Property property, BlockState state, ServerLevel world, BlockPos pos, Random random1){
        int score= BiomeScores.getScoreBlock(world,pos,Blocks.CHORUS_FLOWER);
        return Helper.cancelBlock(Blocks.CHORUS_FLOWER,score)?5:instance.getValue(property);
    }
}
