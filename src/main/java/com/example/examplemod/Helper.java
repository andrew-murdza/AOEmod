package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Helper {
    public static Random random=new Random();

    public static List<Block> smallFlowers;
    public static List<Block> tallFlowers;
    public static List<Block> coralPlants;
    public static List<Block> coralFans;
    public static List<Block> coralPlantsAndFans;
    static {
        smallFlowers=Arrays.asList(Blocks.POPPY,Blocks.DANDELION,Blocks.AZURE_BLUET,Blocks.CORNFLOWER,
                Blocks.OXEYE_DAISY,Blocks.ALLIUM,Blocks.BLUE_ORCHID,Blocks.LILY_OF_THE_VALLEY,Blocks.RED_TULIP,
                Blocks.WHITE_TULIP,Blocks.PINK_TULIP,Blocks.ORANGE_TULIP);

        tallFlowers=Arrays.asList(Blocks.SUNFLOWER,Blocks.ROSE_BUSH,Blocks.PEONY,Blocks.LILAC);

        coralPlants=Arrays.asList(Blocks.FIRE_CORAL,Blocks.HORN_CORAL,Blocks.BUBBLE_CORAL,
                Blocks.BRAIN_CORAL,Blocks.TUBE_CORAL);
        coralFans=Arrays.asList(Blocks.FIRE_CORAL_FAN,Blocks.HORN_CORAL_FAN,Blocks.BUBBLE_CORAL_FAN,
                Blocks.BRAIN_CORAL_FAN,Blocks.TUBE_CORAL_FAN);
        coralPlantsAndFans=new ArrayList<>(coralPlants);
        coralPlantsAndFans.addAll(coralFans);
    }

    private static void dropItemStack(Level world, BlockPos pos, ItemStack stack){
        double vx=world.random.nextDouble() * 0.2D - 0.1D;
        double vy=0.2D;
        double vz=world.random.nextDouble() * 0.2D - 0.1D;
        ItemEntity dropItem=new ItemEntity(world,pos.getX(),pos.getY(),pos.getZ(),stack,vx,vy,vz);
        world.addFreshEntity(dropItem);
    }

    public static boolean cancelMobs(Entity entity, Event event){
        if(!(entity instanceof Mob)){
            return false;
        }
        boolean b=false;
        int score=BiomeScores.getScoreEntity((Mob)entity);
        b=b||Helper.isClass(entity,new Class[]{Bee.class, MushroomCow.class, PolarBear.class})&&score<3;
        b=b||Helper.isClass(entity,new Class[]{Strider.class, Ocelot.class, Panda.class, Hoglin.class,Parrot.class, Axolotl.class,Turtle.class})&&score<4;
        b=b||Helper.isClass(entity,new Class[]{Horse.class, Donkey.class, Llama.class, Goat.class,Wolf.class,Cat.class})&&score<2;
        if(b){
            event.setCanceled(true);
        }
        return b;
    }

    public static boolean cancelBlock(Block block, int score) {
        boolean flag=false;
        flag=flag||isBlock(block,new Block[]{Blocks.BAMBOO,Blocks.BAMBOO_SAPLING,Blocks.SWEET_BERRY_BUSH,Blocks.CAVE_VINES,Blocks.CAVE_VINES_PLANT,
                Blocks.COCOA,Blocks.BIG_DRIPLEAF_STEM,Blocks.BIG_DRIPLEAF,Blocks.MOSS_BLOCK,Blocks.AZALEA,Blocks.AZALEA_LEAVES,
                Blocks.FLOWERING_AZALEA,Blocks.FLOWERING_AZALEA_LEAVES,Blocks.SWEET_BERRY_BUSH,Blocks.ROSE_BUSH,Blocks.PEONY,
        Blocks.LILAC,Blocks.ROOTED_DIRT,Blocks.SEA_PICKLE})&&score<4;
        flag=flag||isBlock(block,new Block[]{Blocks.WEEPING_VINES,Blocks.WEEPING_VINES_PLANT,Blocks.TWISTING_VINES,
                Blocks.TWISTING_VINES_PLANT,Blocks.NETHER_WART,Blocks.CRIMSON_NYLIUM,Blocks.WARPED_NYLIUM,Blocks.CRIMSON_FUNGUS,
                Blocks.WARPED_FUNGUS,Blocks.NETHERRACK,Blocks.LARGE_FERN,Blocks.FERN})&&score<3;
        flag=flag||isBlock(block,new Block[]{Blocks.KELP,Blocks.CACTUS,Blocks.SUNFLOWER,Blocks.TALL_GRASS})&&score<2;
        flag=flag||score<1;
        return flag;
    }

    public static <T> boolean contains(T object, T... objects){
        return Arrays.asList(objects).contains(object);
    }

    public static boolean isBiome(Level world, BlockPos pos, ResourceKey<Biome> biome){
        return world.getBiome(pos).getRegistryName().equals(biome.location());
    }

    public static boolean isClass(Object object,Class[] classes){
        return Arrays.asList(classes).contains(object.getClass());
    }
    public static boolean isBlock(Block block, Block[] blocks){
        return Arrays.asList(blocks).contains(block);
    }

    public static <T> T getFromScore(T[] values, int score){
        return values[score];
    }

    public static BlockPos pos(Mob mob){
        return new BlockPos(mob.getX(),mob.getY(),mob.getZ());
    }
    public static int nextInt(int i){
        return i>0?random.nextInt(i):1;
    }
    public static int nextInt(int[] chances,int score){
        return nextInt(chances[score]);
    }

    public static <T> T select(T... objects){
        return objects[random.nextInt(objects.length)];
    }

    public static void dropItem(Level world, BlockPos pos, Item item, int i, DropType type){
        int count=0;
        switch (type){
            case CHANCE -> count=i>0&&world.random.nextInt(i)==0?1:0;
            case ALL -> count=i;
            case RANDOM -> count=i>0?world.random.nextInt(i+1):0;
        }
        if(count>0){
            dropItemStack(world,pos,new ItemStack(item,count));
        }
    }
    public static Direction[] horizontalDirections(){
        return new Direction[]{Direction.EAST,Direction.SOUTH,Direction.NORTH,Direction.WEST};
    }
    public static void dropItemScore(Level world, BlockPos pos, Item item, int[] counts, DropType type, int score){
        dropItem(world,pos,item,counts[score],type);
    }
    public static boolean chance(int[] chances,int score){
        return chances[score]>0&&random.nextInt(chances[score])==0;
    }
    public static boolean isMaxAge(BlockState state, Property<Integer> property){
        return state.getValue(property)>=property.getAllValues().count()-1;
    }
    public static void cycleAge(BlockState state,Property<Integer> property){
        if(!isMaxAge(state,property)){
            state.setValue(property,state.getValue(property)+1);
        }
    }

    public static float nextFloat(float[] chances, int score) {
        return random.nextFloat(chances[score]);
    }

    enum DropType{
        ALL,
        RANDOM,
        CHANCE
    }
}
