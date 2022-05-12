package com.example.examplemod;

import com.example.examplemod.features.AOEConfiguredFeatures;
import com.example.examplemod.features.CaveVinesFeature;
import com.example.examplemod.features.SporeBlossomDecorator;
import com.example.examplemod.mixins.TreeDecoratedAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class TreeDecoratorRegistry {

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<TreeDecoratorType<?>> event) {
        IForgeRegistry registry=event.getRegistry();

        CaveVinesFeature caveVines=new CaveVinesFeature();
        CaveVinesFeature.CODEC= Codec.unit(() -> caveVines);
        CaveVinesFeature.type=(TreeDecoratorType<CaveVinesFeature>) TreeDecoratedAccessor.createType(CaveVinesFeature.CODEC);

        SporeBlossomDecorator sporeBlossomDecorator=new SporeBlossomDecorator();
        SporeBlossomDecorator.CODEC= Codec.unit(() -> sporeBlossomDecorator);
        SporeBlossomDecorator.type=(TreeDecoratorType<SporeBlossomDecorator>) TreeDecoratedAccessor.createType(SporeBlossomDecorator.CODEC);

        System.out.println("tree decorator ran");

        register("cave_vines",registry, CaveVinesFeature.type);
        register("spore_blossom",registry, SporeBlossomDecorator.type);
    }
    public static void register(String name,IForgeRegistry registry, TreeDecoratorType decoratorType){
        decoratorType.setRegistryName(new ResourceLocation("aoemod", name));
        registry.register(decoratorType);
    }

}