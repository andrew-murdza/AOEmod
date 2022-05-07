package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.util.TriConsumer;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockGrowthEventHandler {

    public static HashMap<Block,int[]> map=new HashMap<>();
    static{
        map.put(Blocks.BAMBOO,new int[]{3,3,3,2,1});
    }

    @SubscribeEvent
    public static void blockGrowthPre(BlockEvent.CropGrowEvent.Pre event){
        BlockState state=event.getState();
        LevelAccessor world = event.getWorld();
        BlockPos pos=event.getPos();
        int score=BiomeScores.getScoreBlock(world,pos,state.getBlock());
        if(state.is(Blocks.BAMBOO_SAPLING)||state.is(Blocks.BAMBOO)){
            growthCutoffFull(event,4,score);
        }
        if(state.is(Blocks.WHEAT)||state.is(Blocks.POTATOES)||state.is(Blocks.CARROTS)||state.is(Blocks.PUMPKIN_STEM)||state.is(Blocks.MELON_STEM)){
            BlockState farmland=world.getBlockState(pos.below());
            if(farmland.is(Blocks.FARMLAND)|| state.getValue(FarmBlock.MOISTURE)==FarmBlock.MAX_MOISTURE){
                checkGrowth(event,new int[]{0,6,3,2,1},score);
            }
            else{
                checkGrowth(event,new int[]{0,12,6,4,2},score);
            }
        }
        if(state.is(Blocks.CACTUS)){
            growthCutoff(event,2,score);
        }
        if(state.is(Blocks.SUGAR_CANE)){
            growthCutoff(event,1,score);
        }
        if(state.is(Blocks.COCOA)||state.is(Blocks.SWEET_BERRY_BUSH)){
            growthCutoffFull(event,4,score);
        }
        if(state.is(Blocks.CHORUS_FLOWER)){
            growthCutoff(event,3,score);
        }
        if(state.is(Blocks.NETHER_WART)){
            growthCutoffFull(event,4,score);
        }
        BlockState aboveBlock=world.getBlockState(pos.above());
        BlockState belowBlock=world.getBlockState(pos.below());
        int aboveScore=BiomeScores.getScoreBlock(world,pos,aboveBlock.getBlock());
        int belowScore=BiomeScores.getScoreBlock(world,pos,belowBlock.getBlock());
        if(aboveBlock.is(Blocks.WEEPING_VINES)){
            checkGrowth(event,new int[]{0,0,10,4,2},aboveScore);
        }
        if (belowBlock.is(Blocks.TWISTING_VINES)) {
            checkGrowth(event,new int[]{0,0,10,4,2},belowScore);
        }
        if(aboveBlock.is(Blocks.CAVE_VINES)){
            growthCutoffFull(event,4,aboveScore);
        }
        if(belowBlock.is(Blocks.KELP)){
            checkGrowth(event,new int[]{0,30,10,4,2},belowScore);
        }
    }

    @SubscribeEvent
    public static void blockGrowthPost(BlockEvent.CropGrowEvent.Post event){
        BlockState state=event.getState();
        LevelAccessor world = event.getWorld();
        BlockPos pos=event.getPos();
        int score=BiomeScores.getScoreBlock(world,pos,state.getBlock());
        if(Helper.cancelBlock(state.getBlock(),score)){
            event.setResult(Event.Result.DENY);
        }
        if(state.is(Blocks.CACTUS)||state.is(Blocks.SUGAR_CANE)){
            addGrowth(event,new int[]{0,0,3,2,1},new int[]{0,0,1,1,1},score,p->Helper.cycleAge(p,BlockStateProperties.AGE_15));
        }
        else if(state.is(Blocks.CAVE_VINES)){
            world.setBlock(pos.above(),Blocks.CAVE_VINES_PLANT.defaultBlockState().setValue(CaveVines.BERRIES,true),3);
            world.setBlock(pos,Blocks.CAVE_VINES.defaultBlockState().setValue(CaveVines.BERRIES,true),3);
        }
    }

    private static void checkGrowth(BlockEvent.CropGrowEvent event, int[] chances, int score){
        event.setResult(Helper.chance(chances,score)? Event.Result.ALLOW: Event.Result.DENY);
        System.out.println(event.getResult());
    }
    private static void growthCutoff(BlockEvent.CropGrowEvent event, int cutoff, int score){
        event.setResult(cutoff>score? Event.Result.DENY: Event.Result.DEFAULT);
    }
    private static void growthCutoffFull(BlockEvent.CropGrowEvent event, int cutoff, int score){
        event.setResult(cutoff>score? Event.Result.DENY: Event.Result.ALLOW);
    }
    private static void addGrowth(BlockEvent.CropGrowEvent event, int[] chances, int[] counts, int score, TriConsumer<BlockState,BlockPos,ServerLevel> func){
        if(Helper.chance(chances,score)){
            for(int i=0;i<counts[score];i++){
                func.accept(event.getState(),event.getPos(), (ServerLevel) event.getWorld());
            }
        }
    }
    private static void addGrowth(BlockEvent.CropGrowEvent event, int[] chances, int[] counts, int score, Consumer<BlockState> func){
        if(Helper.chance(chances,score)){
            for(int i=0;i<counts[score];i++){
                func.accept(event.getState());
            }
        }
        event.setResult(Event.Result.DENY);
    }
}
