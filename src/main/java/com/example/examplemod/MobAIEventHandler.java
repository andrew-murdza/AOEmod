package com.example.examplemod;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobAIEventHandler {

    @SubscribeEvent
    public static void AIChanges(EntityJoinWorldEvent event){
        Entity entity=event.getEntity();
        if(entity instanceof Wolf){
            removeAI(p->p instanceof NonTameRandomTargetGoal, (Mob) entity);
        }
        if(entity instanceof Fox){
            removeAI(new Class[]{Fox.FoxEatBerriesGoal.class,Fox.FoxEatBerriesGoal.class,AvoidEntityGoal.class},(Mob) entity);
        }
        if(entity instanceof Ocelot){
            removeAI(NearestAttackableTargetGoal.class,(Mob) entity);
        }
        //Goat Ramming
        if(entity instanceof Rabbit){
            removeAI(new Class[]{Rabbit.RabbitAvoidEntityGoal.class,Rabbit.RaidGardenGoal.class},(Mob)entity);
        }
        if(entity instanceof Cat){
            removeAI(p->p instanceof NonTameRandomTargetGoal, (Mob) entity);
        }
    }
    private static void removeAI(Predicate<Goal> shouldRemove, Mob mob){
        removeAI(shouldRemove,mob.goalSelector);
        removeAI(shouldRemove,mob.targetSelector);
    }

    private static void removeAI(Class aiClass, Mob mob){
        removeAI(p->p.getClass()==aiClass,mob);
    }

    private static void removeAI(Class[] classes, Mob mob){
        for(Class class1:classes){
            removeAI(class1,mob);
        }
    }

    private static void removeAI(Predicate<Goal> shouldRemove, GoalSelector goals){
        List<Goal> goalsToRemove=new ArrayList<>();
        for(WrappedGoal wrappedGoal: goals.getAvailableGoals()){
            Goal goal=wrappedGoal.getGoal();
            if(shouldRemove.test(goal)){
                goalsToRemove.add(goal);
            }
        }
        for(Goal goal: goalsToRemove){
            goals.removeGoal(goal);
        }
    }

}
