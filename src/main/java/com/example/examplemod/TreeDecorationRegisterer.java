package com.example.examplemod;

import com.example.examplemod.features.CaveVinesFeature;
import com.example.examplemod.features.SporeBlossomDecorator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class TreeDecorationRegisterer {
    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<TreeDecoratorType<?>> event) {
        IForgeRegistry<TreeDecoratorType<?>> registry = event.getRegistry();
        register("tree_cave_vines",registry, CaveVinesFeature.type);
        register("tree_spore_blossom",registry, SporeBlossomDecorator.type);
    }
    public static void register(String name, IForgeRegistry registry, TreeDecoratorType feature){
        feature.setRegistryName("aoemod:"+name);
        registry.register(feature);
    }
}
