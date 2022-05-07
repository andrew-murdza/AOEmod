package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeColorsMixin {
    @Shadow Biome.BiomeCategory biomeCategory;
    @Inject(method = "getGrassColor",at=@At("RETURN"),cancellable = true)
    private void getDefaultGrassColor(CallbackInfoReturnable<Integer> info){
        //info.setReturnValue(convolve(info.getReturnValue(), BiomeScores.isTropical(biomeCategory)));
    }
    @Inject(method = "getFoliageColor",at=@At("RETURN"),cancellable = true)
    private void getFoliageColor(CallbackInfoReturnable<Integer> info){
        //info.setReturnValue(convolve(info.getReturnValue(),BiomeScores.isTropical(biomeCategory)));
    }
    private int convolve(int color,boolean tropical){
        double[] colorMatrix=new double[] {
                0.89, 0.00, 0.00,
                0.00, 1.11, 0.00,
                0.00, 0.00, 0.89
        };
        if(tropical){
            colorMatrix[0]=0.3;
            colorMatrix[4]=1;
            colorMatrix[8]=0.3;
        }
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        int outR = clamp((int) ((double) r * colorMatrix[0] + (double) g * colorMatrix[1] + (double) b * colorMatrix[2]));
        int outG = clamp((int) ((double) r * colorMatrix[3] + (double) g * colorMatrix[4] + (double) b * colorMatrix[5]));
        int outB = clamp((int) ((double) r * colorMatrix[6] + (double) g * colorMatrix[7] + (double) b * colorMatrix[8]));

        return 0xFF000000 | (((outR & 0xFF) << 16) + ((outG & 0xFF) << 8) + (outB & 0xFF));
    }
    private int clamp(int val) {
        return Math.min(0xFF, Math.max(0, val));
    }
}
