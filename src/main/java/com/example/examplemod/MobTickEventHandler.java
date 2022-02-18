package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobTickEventHandler {
    @SubscribeEvent
    public static void mobTickChanges(LivingEvent.LivingUpdateEvent event){
        LivingEntity mob=event.getEntityLiving();
        if(mob instanceof Animal&&!event.getEntityLiving().level.isClientSide&&mob.isAlive()){
            int score=BiomeScores.getScoreEntity((Mob) mob);
            int[] growthRate=new int[]{0,0,0,2,4};
            int[] undoGrowth=new int[]{1,3,0,0,0};
            int age=((Animal) mob).getAge();
            int i=0;
            if(age<0){
                if(Helper.chance(undoGrowth,score)){
                    age--;
                }
                else{
                    age=Math.min(age+growthRate[score],0);
                }
            }
            else if(age>0){
                if(Helper.chance(undoGrowth,score)){
                    age++;
                }
                else{
                    age=Math.max(age-growthRate[score],0);
                }
            }
            ((Animal) mob).setAge(age);
            if(mob instanceof Chicken){
                int[] eggRate=new int[]{0,0,1,2,4};
                int[] undoEggGrowth=new int[]{1,3,0,0,0};
                if(Helper.chance(undoEggGrowth,score)){
                    ((Chicken) mob).eggTime++;
                }
                else{
                    ((Chicken) mob).eggTime=((Chicken) mob).eggTime+eggRate[score];
                }
            }
//            if(mob instanceof Sheep){
//                int[] grassEatRate=new int[]{0,0,1,2,4};
//                int[] undoGrassEatGrowth=new int[]{1,3,0,0,0};
//                if(Helper.chance(grassEatRate,score)){
//                    ((Chicken) mob).eggTime++;
//                }
//                else{
//                    ((Chicken) mob).eggTime=((Chicken) mob).eggTime+eggRate[score];
//                }
//            }
        }
    }
}
