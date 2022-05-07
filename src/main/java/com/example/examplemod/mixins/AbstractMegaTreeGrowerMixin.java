package com.example.examplemod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(AbstractMegaTreeGrower.class)
public abstract class AbstractMegaTreeGrowerMixin extends AbstractTreeGrowerMixin {
    @Shadow @Nullable protected abstract ConfiguredFeature<?, ?> getConfiguredMegaFeature(Random pRandom);

    @Override
    public ConfiguredFeature<?, ?> hi(AbstractTreeGrower instance, Random random, boolean b, ServerLevel pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRandom){
        return this.getConfiguredFeature(random,b);//used this instead of instance should be fine
    }

    @Redirect(method = "placeMega",at=@At(value = "INVOKE",target = "Lnet/minecraft/world/level/block/grower/AbstractMegaTreeGrower;getConfiguredMegaFeature(Ljava/util/Random;)Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;"))
    public ConfiguredFeature<?, ?> hi1(AbstractMegaTreeGrower instance, Random random, ServerLevel pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRandom, int pBranchX, int pBranchY){
        return this.getConfiguredMegaFeature(random);
    }
}
