package com.example.examplemod.mixins;

import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.HugeBrownMushroomFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(AbstractHugeMushroomFeature.class)
public class HugeMushroomMixin {
    @Redirect(method = "getTreeHeight",
            at = @At(value = "INVOKE",
                    target ="Ljava/util/Random;nextInt(I)I",ordinal = 0))
    private int getHeight(Random random, int n){
        return Math.max(random.nextInt(3), 1);
    }
}
