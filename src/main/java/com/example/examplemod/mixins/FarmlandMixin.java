package com.example.examplemod.mixins;

import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FarmBlock.class)
public class FarmlandMixin {
    @Redirect(method = "canSurvive",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/material/Material;isSolid()Z"))
    private boolean isSolid(Material material){
        return material.isSolid()&&!(material==Material.VEGETABLE);
    }

}
