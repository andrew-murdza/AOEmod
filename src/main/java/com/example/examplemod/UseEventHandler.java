package com.example.examplemod;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UseEventHandler {

    private static final Map<DyeColor, Item> woolMap = new HashMap<>();
    static {
        woolMap.put(DyeColor.WHITE, Items.WHITE_WOOL);
        woolMap.put(DyeColor.ORANGE, Items.ORANGE_WOOL);
        woolMap.put(DyeColor.MAGENTA, Items.MAGENTA_WOOL);
        woolMap.put(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_WOOL);
        woolMap.put(DyeColor.YELLOW, Items.YELLOW_WOOL);
        woolMap.put(DyeColor.LIME, Items.LIME_WOOL);
        woolMap.put(DyeColor.PINK, Items.PINK_WOOL);
        woolMap.put(DyeColor.GRAY, Items.GRAY_WOOL);
        woolMap.put(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_WOOL);
        woolMap.put(DyeColor.CYAN, Items.CYAN_WOOL);
        woolMap.put(DyeColor.PURPLE, Items.PURPLE_WOOL);
        woolMap.put(DyeColor.BLUE, Items.BLUE_WOOL);
        woolMap.put(DyeColor.BROWN, Items.BROWN_WOOL);
        woolMap.put(DyeColor.GREEN, Items.GREEN_WOOL);
        woolMap.put(DyeColor.RED, Items.RED_WOOL);
        woolMap.put(DyeColor.BLACK, Items.BLACK_WOOL);
    }

    @SubscribeEvent
    public static void extraOrCancelBreeding(PlayerInteractEvent.EntityInteract event){
        Helper.cancelMobs(event.getTarget(),event);
    }
    @SubscribeEvent
    public static void extraOrCancelBreeding(PlayerInteractEvent.EntityInteractSpecific event){
        Player player=event.getPlayer();
        ItemStack stack=event.getItemStack();
        Entity entity=event.getTarget();
        if(Helper.cancelMobs(entity,event)){
            System.out.println("this ran");
            return;
        }
        if(entity instanceof MushroomCow){
            useFood(entity,player,stack,new Item[]{Items.BROWN_MUSHROOM,Items.RED_MUSHROOM});
        }
        else if(entity instanceof Rabbit){
            useFood(entity,player,stack,p-> ItemTags.SMALL_FLOWERS.contains(p)&&p!=Items.WITHER_ROSE);
        }
        else if(entity instanceof Horse || entity instanceof Donkey){
            useFood(entity,player,stack,Items.HAY_BLOCK);
        }
        else if(entity instanceof Ocelot||entity instanceof Cat){
            useFood(entity,player,stack,new Item[]{Items.COD,Items.TROPICAL_FISH,Items.SALMON});
        }
        else if(entity instanceof Parrot){
            useFood(entity,player,stack,Items.APPLE);
        }
        else if(entity instanceof PolarBear){
            useFood(entity,player,stack,new Item[]{Items.BEEF,Items.MUTTON,Items.COD,Items.SALMON});
        }
    }

    private static boolean cancel(Entity entity, int cutoff, PlayerInteractEvent.EntityInteractSpecific event) {
        if(BiomeScores.getScoreEntity((Mob)entity)<cutoff){
            event.setCanceled(true);
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void extraShearDrops(PlayerInteractEvent.EntityInteractSpecific event){
        Entity entity=event.getTarget();
        if(entity instanceof Animal&&event.getItemStack().is(Items.SHEARS)&&!event.getWorld().isClientSide()){
            int score=BiomeScores.getScoreEntity((Mob) entity);
            if(entity instanceof MushroomCow){
                event.setCanceled(true);
//                MushroomCow.MushroomType type=((MushroomCow) entity).getMushroomType();
//                Item mushroom=type== MushroomCow.MushroomType.RED?Items.RED_MUSHROOM:Items.BROWN_MUSHROOM;
//                Helper.dropItemScore(event.getWorld(),event.getPos(),mushroom,new int[]{0,0,0,1,2}, Helper.DropType.ALL,score);
            }
            else if(entity instanceof Sheep){
                Item wool=woolMap.get(((Sheep) entity).getColor());
                Helper.dropItemScore(event.getWorld(),event.getPos(),wool,new int[]{0,0,0,3,2}, Helper.DropType.CHANCE,score);
                Helper.dropItemScore(event.getWorld(),event.getPos(),wool,new int[]{0,0,0,0,1}, Helper.DropType.ALL,score);
            }
        }
    }
    @SubscribeEvent
    public static void hoeOnMossPodzol(PlayerInteractEvent.RightClickBlock event){
        Level level=event.getWorld();
        Player player=event.getPlayer();
        BlockPos pos=event.getPos();
        BlockState blockState=level.getBlockState(pos);
        if(event.getItemStack().getItem() instanceof HoeItem){
            if(blockState.is(Blocks.MOSS_BLOCK)|| blockState.is(Blocks.PODZOL)){
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    level.setBlock(pos,Blocks.FARMLAND.defaultBlockState(),11);
                    if (player != null) {
                        event.getItemStack().hurtAndBreak(1, player, (p_150845_) -> {
                            p_150845_.broadcastBreakEvent(event.getHand());
                        });
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void berriesFromUse(PlayerInteractEvent.RightClickBlock event){
        Level level=event.getWorld();
        BlockPos pos=event.getPos();
        BlockState state=level.getBlockState(pos);
        Block block=state.getBlock();
        int score=BiomeScores.getScoreBlock(level,pos,block);
        if(Helper.cancelBlock(block,score)){
            if (Helper.contains(block,Blocks.CAVE_VINES,Blocks.CAVE_VINES_PLANT))
            event.setCanceled(true);
            System.out.println("this ran");
        }
        else if(block instanceof CaveVines&&CaveVines.hasGlowBerries(state)){
            if(Helper.cancelBlock(block,BiomeScores.getScoreBlock(level,pos,block))){
                event.setCanceled(true);
            }
            else{
                Helper.dropItemScore(level,pos,Items.GLOW_BERRIES,new int[]{0,0,0,0,1}, Helper.DropType.CHANCE,score);
            }
        }
        else if(state.is(Blocks.SWEET_BERRY_BUSH)){
            int age=state.getValue(BlockStateProperties.AGE_3);
            if(score<4){
                event.setCanceled(true);
                return;
            }
            if(age==2&&!event.getItemStack().is(Items.BONE_MEAL)){
                Helper.dropItemScore(level,pos,Items.SWEET_BERRIES,new int[]{0,0,0,0,3}, Helper.DropType.RANDOM,score);
            }
            else if(age==3){
                Helper.dropItemScore(level,pos,Items.SWEET_BERRIES,new int[]{0,0,0,0,3}, Helper.DropType.RANDOM,score);
                Helper.dropItemScore(level,pos,Items.SWEET_BERRIES,new int[]{0,0,0,0,3}, Helper.DropType.ALL,score);
            }
        }
    }
    private static void useItem(Player player, ItemStack stack){
        if(!player.getAbilities().instabuild){
            stack.shrink(1);
        }
    }

    private static void useFood(Entity entity, Player player, ItemStack stack, Item[] items){
        useFood(entity,player,stack,p-> Arrays.asList(items).contains(p));
    }
    private static void useFood(Entity entity, Player player, ItemStack stack, Item item){
        useFood(entity,player,stack,p-> p==item);
    }

    private static boolean useFood(Entity entity, Player player, ItemStack stack, Function<Item,Boolean> func){
        if(entity instanceof Animal&&func.apply(stack.getItem())){
            Animal animal=(Animal) entity;
            if(!animal.level.isClientSide&&animal.getAge()==0&&animal.canFallInLove()){
                if(!(animal instanceof TamableAnimal)||((TamableAnimal)animal).isTame()){
                    if(!(animal instanceof AbstractHorse)||((AbstractHorse)animal).isTamed()){
                        useItem(player,stack);
                        animal.setInLove(player);
                        animal.gameEvent(GameEvent.MOB_INTERACT,animal.eyeBlockPosition());
                        return true;
                    }
                }
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
