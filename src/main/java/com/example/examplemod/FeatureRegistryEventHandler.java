package com.example.examplemod;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistryEventHandler {

    public static Feature RANDOM_SELECTOR;

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        RANDOM_SELECTOR=new RandomSelectionFeature(RandomFeatureConfiguration.CODEC);
        RANDOM_SELECTOR.setRegistryName("aoemod:randomselector");
        event.getRegistry().register(RANDOM_SELECTOR);
        System.out.println("this ran");
    }

}
