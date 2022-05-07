package com.example.examplemod.mixins;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
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

@Mixin(AbstractTreeGrower.class)
public abstract class AbstractTreeGrowerMixin {
    @Shadow @Nullable protected abstract ConfiguredFeature<?, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive);

    @Redirect(method = "growTree",at=@At(value = "INVOKE",target = "Lnet/minecraft/world/level/block/grower/AbstractTreeGrower;getConfiguredFeature(Ljava/util/Random;Z)Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;"))
    public ConfiguredFeature<?, ?> hi(AbstractTreeGrower instance, Random random, boolean b, ServerLevel pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRandom){
        return this.getConfiguredFeature(random,b);//used this instead of instance should be fine
    }

}
