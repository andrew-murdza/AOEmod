package com.example.examplemod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CaveVinesBlock.class)
public class CaveVinePlantMixin extends GrowingPlantBlockMixin {
    @Override
    protected boolean canSurvive(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return super.canSurvive(state,world,pos,direction)||state.is(BlockTags.LEAVES);
    }
}
