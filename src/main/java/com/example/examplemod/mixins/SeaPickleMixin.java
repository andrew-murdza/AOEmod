package com.example.examplemod.mixins;

import com.example.examplemod.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SeaPickleBlock.class)
public class SeaPickleMixin {
    @Redirect(method = "performBonemeal",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/Tag;)Z"))
    private boolean getBlockState(BlockState instance, Tag tag){
        return instance.is(BlockTags.CORAL_BLOCKS)||instance.is(Blocks.MOSS_BLOCK)||instance.is(Blocks.SPONGE);
    }
}
