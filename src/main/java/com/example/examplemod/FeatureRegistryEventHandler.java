package com.example.examplemod;

import com.example.examplemod.features.AOEConfiguredFeatures;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistryEventHandler {

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry registry=event.getRegistry();

        register("randomselector",registry,AOEConfiguredFeatures.RANDOM_SELECTOR);

        registerGroup("coral_claw",registry,AOEConfiguredFeatures.coralClawFeatures.values());
        registerGroup("coral_mushroom",registry,AOEConfiguredFeatures.coralMushroomFeatures.values());
        registerGroup("coral_tree",registry,AOEConfiguredFeatures.coralTreeFeatures.values());
        AOEConfiguredFeatures.updateFeatures();
    }
    public static void register(String name,IForgeRegistry registry, Feature feature){
        feature.setRegistryName("aoemod:"+name);
        registry.register(feature);
    }
    public static void registerGroup(String name, IForgeRegistry registry, Collection<Feature> features){
        int i=0;
        for(Feature feature:features){
            register(name+i,registry,feature);
            i++;
        }
    }

}
