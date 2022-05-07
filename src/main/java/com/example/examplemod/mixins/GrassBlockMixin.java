package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
//import com.example.examplemod.features.AOEConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends SpreadableBlockMixin {

    @Redirect(method = "performBonemeal",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I",ordinal = 4))
    private int nextInt(Random random, int n, ServerLevel level, Random random1, BlockPos pos, BlockState state){
        int score= BiomeScores.getScoreBlock(level,pos,(Block)(Object)this);
        int[] tallGrassChances=new int[]{10,10,6,4,3};
        return random.nextInt(tallGrassChances[score]);
    }

    @ModifyConstant(method="performBonemeal",constant = @Constant(intValue = 128))
    private int injected(int constant,ServerLevel level, Random random1, BlockPos pos, BlockState state){
        int score= BiomeScores.getScoreBlock(level,pos,(Block)(Object)this);
        int[] tallGrassChances=new int[]{0,60,128,128,128};
        return tallGrassChances[score];
    }
    @Redirect(method = "performBonemeal",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",ordinal = 1))
    private boolean isOf(BlockState instance, Block block){
        return instance.is(block)||instance.is(Blocks.FERN);
    }
    @Redirect(method = "performBonemeal",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/data/worldgen/placement/VegetationPlacements;GRASS_BONEMEAL:Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;",opcode = Opcodes.GETSTATIC))
    private PlacedFeature getGrass(ServerLevel level, Random random, BlockPos pos, BlockState state){
        Biome.BiomeCategory category=level.getBiome(pos).getBiomeCategory();
//        if(BiomeScores.isTropical(category)){
//            return AOEConfiguredFeatures.tropicalGrass;
//        }
//        else if(BiomeScores.isMountains(category)){
//            return AOEConfiguredFeatures.mountainsGrass;
//        }
        return VegetationPlacements.GRASS_BONEMEAL;
    }
}
