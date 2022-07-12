package com.example.examplemod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public class LeavesLightMixinHelper {

    @Redirect(method = "propagatesSkylightDown",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;isShapeFullBlock(Lnet/minecraft/world/phys/shapes/VoxelShape;)Z"))
    public boolean propagatesSkylightDown(VoxelShape pShape, BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return !Block.isShapeFullBlock(pState.getShape(pLevel, pPos))||pState.getBlock() instanceof LeavesBlock;
    }
}
