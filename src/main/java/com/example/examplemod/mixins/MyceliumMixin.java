package com.example.examplemod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.MyceliumBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MyceliumBlock.class)
public abstract class MyceliumMixin extends SpreadableBlockMixin {
    @Override
    protected int getLight(ServerLevel instance, BlockPos blockPos){
        return instance.getMaxLightLevel();
    }
}
