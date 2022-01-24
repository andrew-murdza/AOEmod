package com.example.examplemod;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FarmlandTrampleEventHandler {
    @SubscribeEvent
    public static void cancelTrample(BlockEvent.FarmlandTrampleEvent event){
        event.setCanceled(true);
    }
}
