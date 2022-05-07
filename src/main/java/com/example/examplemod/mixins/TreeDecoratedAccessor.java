package com.example.examplemod.mixins;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeDecoratorType.class)
public interface TreeDecoratedAccessor {

    @Invoker("<init>")
    static <P> TreeDecoratorType createType(Codec<P> codec){
        throw new AssertionError();
    }

}
