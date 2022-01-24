package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Random;

public class Helper {
    public static Random random=new Random();
    private static void dropItemStack(Level world, BlockPos pos, ItemStack stack){
        double vx=world.random.nextDouble() * 0.2D - 0.1D;
        double vy=0.2D;
        double vz=world.random.nextDouble() * 0.2D - 0.1D;
        ItemEntity dropItem=new ItemEntity(world,pos.getX(),pos.getY(),pos.getZ(),stack,vx,vy,vz);
        world.addFreshEntity(dropItem);
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
    enum DropType{
        ALL,
        RANDOM,
        CHANCE
    }
    public void githubChange(){

    }
}
