package com.example.examplemod;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UseEventHandler {
    @SubscribeEvent
    public static void extraBreeding(PlayerInteractEvent.EntityInteract event){
        Player player=event.getPlayer();
        Entity entity=event.getTarget();
        if(entity instanceof MushroomCow){
            useFood(entity,player,new Item[]{Items.BROWN_MUSHROOM,Items.RED_MUSHROOM});
        }
        if(entity instanceof Rabbit){
            useFood(entity,player,p-> ItemTags.SMALL_FLOWERS.contains(p)&&p!=Items.WITHER_ROSE);
        }
        if(entity instanceof Horse || entity instanceof Donkey){
            useFood(entity,player,Items.HAY_BLOCK);
        }
        if(entity instanceof Ocelot){
            useFood(entity,player,new Item[]{Items.COD,Items.TROPICAL_FISH,Items.SALMON});
        }
        if(entity instanceof Parrot){
            useFood(entity,player,Items.APPLE);
        }
        if(entity instanceof PolarBear){
            useFood(entity,player,new Item[]{Items.BEEF,Items.MUTTON});
        }
    }
    private static void useItem(Player player, ItemStack stack){
        if(!player.getAbilities().instabuild){
            stack.shrink(1);
        }
    }

    private static void useFood(Entity entity, Player player, Item[] items){
        useFood(entity,player,p-> Arrays.asList(items).contains(p));
    }
    private static void useFood(Entity entity, Player player, Item item){
        useFood(entity,player,p-> p==item);
    }

    private static void useFood(Entity entity, Player player, Function<Item,Boolean> func){
        if(!useFoodStack(entity, player, player.getMainHandItem(), func)){
            useFoodStack(entity, player, player.getOffhandItem(), func);
        }
    }

    private static boolean useFoodStack(Entity entity, Player player, ItemStack stack, Function<Item,Boolean> func){
        if(entity instanceof Animal&&func.apply(stack.getItem())){
            Animal animal=(Animal) entity;
            if(!animal.level.isClientSide&&animal.getAge()==0&&animal.canFallInLove()){
                useItem(player,stack);
                animal.setInLove(player);
                animal.gameEvent(GameEvent.MOB_INTERACT,animal.eyeBlockPosition());
                return true;
            }
            if(animal.isBaby()){
                useItem(player,stack);
                animal.ageUp((int)((float)(-animal.getAge() / 20) * 0.1F), true);
                animal.gameEvent(GameEvent.MOB_INTERACT,animal.eyeBlockPosition());
                return true;
            }
        }
        return false;
    }
}
