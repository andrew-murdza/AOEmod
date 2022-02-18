package com.example.examplemod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CactusBlock.class)
public class CactusMixin {
    @Redirect(method = "canSurvive",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;canSustainPlant(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraftforge/common/IPlantable;)Z"))
    public boolean canSurvive(BlockState instance, BlockGetter blockGetter, BlockPos blockPos, Direction direction, IPlantable iPlantable) {
        return instance.is(Blocks.CACTUS)||instance.is(BlockTags.DIRT)||instance.is(BlockTags.SAND);
    }
}
