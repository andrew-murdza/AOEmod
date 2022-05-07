package com.example.examplemod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.BambooFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BambooFeature.class)
public class BambooFeatureMixin {
    @Redirect(method = "place",at=@At(value = "INVOKE",target = "Lnet/minecraft/core/BlockPos;getY()I"))
    public int getY(BlockPos instance){
        ((BlockPos.MutableBlockPos)instance).move(Direction.DOWN,1);
        return instance.getY();
    }

}