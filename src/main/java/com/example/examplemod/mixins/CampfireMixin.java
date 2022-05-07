package com.example.examplemod.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.CampfireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CampfireBlock.class)
public class CampfireMixin {
    @Redirect(method = "entityInside",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;fireImmune()Z"))
    public boolean canSurvive(Entity instance) {
        return true;
    }
}
