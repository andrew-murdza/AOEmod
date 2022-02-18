package com.example.examplemod;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockDamageEventHandler {
    @SubscribeEvent
    public static void cancelBlockDamage(LivingAttackEvent event){
        DamageSource source=event.getSource();
        if(source==DamageSource.SWEET_BERRY_BUSH||source==DamageSource.CACTUS){
            event.setCanceled(true);
        }
    }
}
