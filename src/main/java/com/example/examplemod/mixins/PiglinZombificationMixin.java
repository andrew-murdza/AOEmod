package com.example.examplemod.mixins;

import com.example.examplemod.Helper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractPiglin.class)
public class PiglinZombificationMixin extends Monster {

    protected PiglinZombificationMixin(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Redirect(method = "isConverting",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/dimension/DimensionType;piglinSafe()Z"))
    private boolean isPiglinSafe(DimensionType instance){
        return !Helper.isBiome(level,Helper.pos(this),Biomes.WARPED_FOREST);
    }
}
