package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BreedingEventHandler {


    //Animals sometimes don't produce twins
    @SubscribeEvent
    public static void breedingChanges(BabyEntitySpawnEvent event){
        AgeableMob parentA= (AgeableMob) event.getParentA();
        AgeableMob parentB= (AgeableMob) event.getParentB();
        int score=BiomeScores.getScoreEntity(parentA);
        ServerLevel world= (ServerLevel) parentA.getLevel();
        if(Helper.chance(new int[]{0,0,8,3,1},score)){
            AgeableMob secondChild= parentA.getBreedOffspring(world, parentB);
            secondChild.moveTo(parentA.getX(),parentA.getY(),parentA.getZ(),0.0F,0.0F);
            secondChild.setBaby(true);
            world.addFreshEntity(secondChild);
        }
    }

//    @SubscribeEvent
//    public static void breedingChanges(BabyEntitySpawnEvent event){
//        AgeableMob parentA= (AgeableMob) event.getParentA();
//        AgeableMob parentB= (AgeableMob) event.getParentB();
//        int score=BiomeScores.getScoreEntity(parentA);
//        int[] babyAges=new int[]{-24000,-24000,-20000,-16000,-10};//-8000
//        ServerLevel world= (ServerLevel) parentA.getLevel();
//        event.getChild().setAge(babyAges[score]);
//        if(Helper.chance(new int[]{0,0,8,3,1},score)){
//            AgeableMob secondChild= parentA.getBreedOffspring(world, parentB);
//            BlockPos pos=parentA.getOnPos();
//            secondChild.moveTo(pos.getX(),pos.getY(),pos.getZ(),0.0F,0.0F);
//            secondChild.setAge(babyAges[score]);
//            world.addFreshEntity(secondChild);
//        }
//        int[] breedingAges=new int[]{6000,6000,5000,4000,10};//2000
//        parentA.setAge(breedingAges[score]);
//        parentB.setAge(breedingAges[score]);
//    }
}
