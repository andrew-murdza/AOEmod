package com.example.examplemod.mixins;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TreeDecoratorType.class)
public abstract class ToStringMixin implements IForgeRegistryEntry<TreeDecoratorType<?>> {
    @Override
    public String toString() {
        return this.getRegistryName().toString()+super.toString();
    }
}
