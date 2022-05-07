package com.example.examplemod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.BiConsumer;

@Mixin(SpruceFoliagePlacer.class)
public abstract class SpruceFoliageMixin extends FoliagePlacer {
    public SpruceFoliageMixin(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

//    @ModifyVariable(method = "createFoliage", at = @At("HEAD"), ordinal = 6)
//    protected int injected(int value) {
//        return value+5;
//    }
    @Inject(method="createFoliage",at=@At("HEAD"),cancellable = true)
    protected void createFoliage(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, Random pRandom, TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliagePlacer.FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset,CallbackInfo info) {
        BlockPos blockpos = pAttachment.pos();
        int i = pRandom.nextInt(2);
        int j = 1;
        int k = 0;

        for(int l = pOffset; l >= -pFoliageHeight+1; --l) {
            this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, blockpos, i, l, pAttachment.doubleTrunk());
            if (i >= j) {
                i = k;
                k = 1;
                j = Math.min(j + 1, pFoliageRadius + pAttachment.radiusOffset());
            } else {
                ++i;
            }
        }
        info.cancel();
    }
}