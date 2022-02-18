package com.example.examplemod;

import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobGriefingEventHandler {
    @SubscribeEvent
    public static void mobGriefing(EntityMobGriefingEvent event){
        event.setResult(event.getEntity() instanceof Villager? Event.Result.DEFAULT: Event.Result.DENY);
    }
}
