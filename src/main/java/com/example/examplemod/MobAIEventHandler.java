package com.example.examplemod;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobAIEventHandler {

    @SubscribeEvent
    public static void AIChanges(EntityJoinWorldEvent event){
        try{
            Entity entity=event.getEntity();

            if(!event.getWorld().isClientSide&&entity instanceof PathfinderMob) {
                PathfinderMob mob = (PathfinderMob) entity;
                if(mob.getTags().contains("aoe.checkedAI")){
                    return;
                }
                mob.addTag("aoe.checkedAI");

                if (entity instanceof Wolf) {
                    removeAI(mob, NonTameRandomTargetGoal.class);
                } else if (entity instanceof Fox) {
                    removeAI(mob, Fox.FoxEatBerriesGoal.class, AvoidEntityGoal.class, NearestAttackableTargetGoal.class);
                } else if (entity instanceof Ocelot) {
                    removeAI(mob, NearestAttackableTargetGoal.class);//,Ocelot.OcelotTemptGoal.class);
                    addTempt(mob, Items.SALMON, Items.COD, Items.TROPICAL_FISH);
                }
                //Goat Ramming requires brain
                //Axotol hunting passive fish uses brain
                //Piglin brute not attacking players with gold armor requires brain
                else if (entity instanceof Rabbit) {
                    removeAI(mob,Class.forName("net.minecraft.world.entity.animal.Rabbit$RabbitAvoidEntityGoal"));
                    removeAI(mob,Class.forName("net.minecraft.world.entity.animal.Rabbit$RaidGardenGoal"));
                    List<Item> flowers = new ArrayList<>(ItemTags.SMALL_FLOWERS.getValues());
                    flowers.remove(Items.WITHER_ROSE);
                    addTempt(mob, flowers.toArray(new Item[0]));
                } else if (entity instanceof Horse || entity instanceof AbstractChestedHorse) {
                    addTempt(mob, Items.HAY_BLOCK);
                } else if (entity instanceof Cat) {
                    removeAI(mob, NonTameRandomTargetGoal.class);
                    addTempt(mob, Items.SALMON, Items.COD, Items.TROPICAL_FISH);
                } else if (entity instanceof PolarBear) {
                    //breeding needed
                    removeAI(mob, NearestAttackableTargetGoal.class);
                    removeAI(mob,Class.forName("net.minecraft.world.entity.animal.PolarBear$PolarBearAttackPlayersGoal"));
                    addBreeding(mob);
                    addTempt(mob, Items.MUTTON, Items.BEEF, Items.COD, Items.SALMON);
                } else if (entity instanceof Parrot) {
                    addTempt(1, mob, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.APPLE);
                    addBreeding(0, mob);
                }
                //parrot breeding
                else if (entity instanceof MushroomCow) {
                    addTempt(mob, Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
                } else if (entity instanceof AbstractSkeleton || entity instanceof Zombie) {
                    NearestAttackableTargetGoal<Turtle> babyTurtleAttack = new NearestAttackableTargetGoal<>(mob, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR);
                    removeAI(p -> p.equals(babyTurtleAttack), mob);
                } else if (entity instanceof Zombie) {
                    removeAI(mob, Class.forName("net.minecraft.world.entity.monster.Zombie$ZombieAttackTurtleEggGoal"));
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void addBreeding(PathfinderMob mob) {
        addBreeding(2,mob);
    }

    private static void addBreeding(int priority,PathfinderMob mob) {
        mob.goalSelector.addGoal(priority, new BreedGoal((Animal) mob, 1.0D));
    }

    private static void removeAI(Predicate<Goal> shouldRemove, PathfinderMob mob){
        removeAI(shouldRemove,mob.goalSelector);
        removeAI(shouldRemove,mob.targetSelector);
    }

    private static void addTempt(PathfinderMob mob, Item... items){
        addTempt(3,mob,items);
    }
    private static void addTempt(int priority,PathfinderMob mob, Item... items){
        mob.goalSelector.addGoal(priority, new TemptGoal(mob, 1.0D, Ingredient.of(items), false));
    }


    private static void removeAI(PathfinderMob mob,Class... classes){
        for(Class class1:classes){
            removeAI(p->p.getClass()==class1,mob);
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
