package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Arrays;
import java.util.List;

//@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpawnConditionsEventHandler {
    public static void updateSpawnConditions(final FMLCommonSetupEvent event) {
        addSpawn(EntityType.AXOLOTL,true,event);
        addSpawn(EntityType.GLOW_SQUID,true,(p,q,r,s,t)->glowSquid(q,s),event);
        addSpawn(EntityType.SLIME,false,Monster::checkMonsterSpawnRules,event);
        addSpawn(EntityType.MOOSHROOM,false,(p,q,r,s,t)->mooshroom(q,s),event);
        addSpawn(EntityType.PARROT,false,(p,q,r,s,t)->parrot(q,s),event);
        addSpawn(EntityType.PILLAGER,false,Monster::checkMonsterSpawnRules,event);
        EntityType[] animals=new EntityType[]{EntityType.CHICKEN,EntityType.DONKEY,EntityType.HORSE,EntityType.COW,
                EntityType.OCELOT,EntityType.PIG,EntityType.SHEEP,EntityType.CAT,EntityType.PANDA,
                EntityType.WOLF,EntityType.MULE,EntityType.TURTLE};
        EntityType[] snowAnimals=new EntityType[]{EntityType.RABBIT,EntityType.POLAR_BEAR,EntityType.FOX,
                EntityType.LLAMA,EntityType.GOAT};
        for(EntityType animal: animals){
            addAnimal(animal,false,event);
        }
        for(EntityType animal: snowAnimals){
            addAnimal(animal,true,event);
        }
    }
    public static void addSpawn(FMLCommonSetupEvent event, SpawnPlacements.Type type, Heightmap.Types heightType ,EntityType entityType, SpawnPlacements.SpawnPredicate func){
        event.enqueueWork(()->{
            try{
                SpawnPlacements.register(entityType,type,heightType,func);
            }
            catch (IllegalStateException exception){

            }
        });
    }
    public static void addSpawn(EntityType entityType, boolean inWater, FMLCommonSetupEvent event){
        SpawnPlacements.Type type = inWater? SpawnPlacements.Type.IN_WATER:SpawnPlacements.Type.ON_GROUND;
        Heightmap.Types motionBlocking = Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;
        addSpawn(event,type,motionBlocking,entityType,(p,q,r,s,t)->true);
    }
    public static void addSpawn(EntityType entityType, boolean inWater, SpawnPlacements.SpawnPredicate func, FMLCommonSetupEvent event){
        SpawnPlacements.Type type = inWater? SpawnPlacements.Type.IN_WATER:SpawnPlacements.Type.ON_GROUND;
        Heightmap.Types motionBlocking = Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;
        addSpawn(event,type,motionBlocking,entityType,(p,q,r,s,t)->true);
    }
    public static boolean glowSquid(ServerLevelAccessor level, BlockPos pos) {
        return BiomeScores.isTropical(level,pos)||pos.getY() <= level.getSeaLevel()-33;
    }
    public static boolean mooshroom(ServerLevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(Blocks.MYCELIUM);
    }
    public static boolean parrot(ServerLevelAccessor level, BlockPos pos) {
        List<Block> blocks= Arrays.asList(new Block[]{Blocks.GRASS_BLOCK,Blocks.PODZOL,Blocks.MOSS_BLOCK});
        blocks.addAll(BlockTags.LOGS_THAT_BURN.getValues());
        blocks.addAll(BlockTags.LEAVES.getValues());
        Block block=level.getBlockState(pos.below()).getBlock();
        return blocks.contains(block)&&level.getRawBrightness(pos,0)>8;
    }
    public static boolean animal(ServerLevelAccessor level, BlockPos pos, boolean isSnow) {
        List<Block> blocks= Arrays.asList(new Block[]{Blocks.GRASS_BLOCK,Blocks.PODZOL,Blocks.MOSS_BLOCK});
        List<Block> snow=Arrays.asList(new Block[]{Blocks.SNOW_BLOCK,Blocks.SNOW,Blocks.ICE,Blocks.BLUE_ICE,Blocks.PACKED_ICE});
        Block block=level.getBlockState(pos.below()).getBlock();
        if(blocks.contains(block)||isSnow&&snow.contains(block)){
            return level.getRawBrightness(pos,0)>8;//(block==Blocks.MOSS_BLOCK?7:8);
        }
        return false;
    }
    public static void addAnimal(EntityType animal,boolean snow,FMLCommonSetupEvent event){
        addSpawn(animal,false,(p,q,r,s,t)->animal(q,s,false),event);
    }
}
