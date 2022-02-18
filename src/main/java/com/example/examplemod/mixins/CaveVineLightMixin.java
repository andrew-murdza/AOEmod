package com.example.examplemod.mixins;

import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Blocks.class)
public class CaveVineLightMixin {
    @ModifyArg(method = "<clinit>",at=@At(value = "INVOKE",target = "Lnet/minecraft/world/level/block/CaveVines;emission(I)Ljava/util/function/ToIntFunction;"))
    private static int moreLightCaveVines(int i){
        return 15;
    }
}
