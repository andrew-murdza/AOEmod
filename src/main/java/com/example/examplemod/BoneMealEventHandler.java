package com.example.examplemod;

//import com.example.examplemod.features.AOEConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BoneMealEventHandler {

    static RandomCollection<BiFunction<Level,BlockPos,Boolean>> mossPlants=new RandomCollection<>();
    private static BiFunction<Level,BlockPos,Boolean> placeBlock(BlockState state){
        return (world,pos)->world.setBlockAndUpdate(pos,state);
    }
    private static BiFunction<Level,BlockPos,Boolean> placeDoubleBlock(Block block){
        return (world,pos)->{DoublePlantBlock.placeAt(world, block.defaultBlockState(), pos, 2);return true;};
    }
    private static BiFunction<Level,BlockPos,Boolean> placeBlock(Block block){
        return placeBlock(block.defaultBlockState());
    }
    static {
        mossPlants.add(1,placeBlock(Blocks.RED_MUSHROOM));
        mossPlants.add(1,placeBlock(Blocks.BROWN_MUSHROOM));
        Block[] flowers=new Block[]{Blocks.POPPY,Blocks.DANDELION,Blocks.CORNFLOWER,Blocks.BLUE_ORCHID,
        Blocks.AZURE_BLUET,Blocks.WHITE_TULIP,Blocks.PINK_TULIP,Blocks.ORANGE_TULIP,Blocks.RED_TULIP,
                Blocks.OXEYE_DAISY,Blocks.LILY_OF_THE_VALLEY,Blocks.ALLIUM};
        for(Block flower: flowers){
            mossPlants.add(1,placeBlock(flower));
        }
        mossPlants.add(100,placeBlock(Blocks.GRASS));
        mossPlants.add(20,placeBlock(Blocks.FERN));
        mossPlants.add(0.3,placeBlock(Blocks.DEAD_BUSH));
        mossPlants.add(1,BoneMealEventHandler::createDripLeaf);
        mossPlants.add(6,placeBlock(Blocks.AZALEA));
        mossPlants.add(2,placeBlock(Blocks.FLOWERING_AZALEA));
        mossPlants.add(20,placeDoubleBlock(Blocks.LARGE_FERN));
        mossPlants.add(20,placeDoubleBlock(Blocks.TALL_GRASS));
        mossPlants.add(1,placeDoubleBlock(Blocks.SUNFLOWER));
        mossPlants.add(1,placeDoubleBlock(Blocks.LILAC));
        mossPlants.add(1,placeDoubleBlock(Blocks.ROSE_BUSH));
        mossPlants.add(1,placeDoubleBlock(Blocks.PEONY));
    }

    @SubscribeEvent
    public static void boneMealEvent(BonemealEvent event){
        Block block=event.getBlock().getBlock();
        Level world=event.getWorld();
        BlockPos pos=event.getPos();
        if(Helper.cancelBlock(block,BiomeScores.getScoreBlock(world,pos,block))){
            event.setResult(Event.Result.DENY);
            event.setCanceled(true);
            return;
        }
        if(!(world instanceof ServerLevel)){
            return;
        }
        Block aboveState=world.getBlockState(pos.above()).getBlock();
        boolean belowWater=aboveState==Blocks.WATER||aboveState==Blocks.LAVA;
        boolean flag=true;
        if(block==Blocks.ROOTED_DIRT){
            growRootedDirt(world,world.random,pos);
        }

        else if(block== Blocks.LILY_PAD||block==Blocks.SPORE_BLOSSOM){
            Block.popResource(world, pos, new ItemStack(block));
        }
        else if(block==Blocks.CLAY&&!belowWater&&score(Blocks.SMALL_DRIPLEAF,event)>3){
            growClay(world,pos);
        }
        else if(!belowWater&&(block==Blocks.SAND||block==Blocks.RED_SAND)&&score(Blocks.CACTUS,event)>2){
            growSand(world,pos);
        }
        else if(!belowWater&&(block==Blocks.MYCELIUM)&&score(Blocks.RED_MUSHROOM,event)>2){
            growMycelium(world,pos);
        }
        else if(block==Blocks.MOSS_BLOCK&&score(block,event)>3){
            Function<BlockPos,Boolean> func=(pos1)->mossPlants.next(world.random).apply(world,pos1);
            placeBoneMeal(world,pos,blockState->blockState.is(Blocks.MOSS_BLOCK),128,func);
        }
        else if(BlockTags.FLOWERS.contains(block)){
            boolean flag1=false;
            int score=score(Blocks.ALLIUM,event);
            flag1=flag1||(block==Blocks.ALLIUM||block==Blocks.BLUE_ORCHID)&&score>3;
            flag1=flag1||(block==Blocks.POPPY||block==Blocks.DANDELION)&&score>0;
            flag1=flag1||Helper.isBlock(block,new Block[]{Blocks.ORANGE_TULIP,Blocks.RED_TULIP,Blocks.PINK_TULIP,
                    Blocks.WHITE_TULIP, Blocks.AZURE_BLUET,Blocks.OXEYE_DAISY,Blocks.CORNFLOWER,Blocks.LILY_OF_THE_VALLEY})&&score>1;
            if(flag1){
                growFlowers(world,pos);
            }
            flag=flag1;
        }
        else if((block==Blocks.SUGAR_CANE||block==Blocks.CACTUS)&&score(block,event)>0){
            flag=growSugarcaneCactus(world,world.random,pos,block);
        }
        else if(block==Blocks.NETHER_WART&&score(block,event)>2){
            flag=growNetherWart(pos,world);
        }
        else if(BlockTags.CORALS.contains(block)){
//            int type=world.random.nextInt(3);
//            ConfiguredFeature feature= AOEConfiguredFeatures.coralClawFeatures.get(block).configured(FeatureConfiguration.NONE);
//            if(type==1){
//                feature= AOEConfiguredFeatures.coralMushroomFeatures.get(block).configured(FeatureConfiguration.NONE);
//            }
//            else if(type==2){
//                feature= AOEConfiguredFeatures.coralTreeFeatures.get(block).configured(FeatureConfiguration.NONE);
//            } feature.place((WorldGenLevel) world,((ServerLevel)world).getChunkSource().getGenerator(), world.random,pos);
        }
        else if(block==Blocks.GRASS_BLOCK&&score(block,event)>0){
            ((GrassBlock)block).performBonemeal((ServerLevel) world,world.random,pos,Blocks.GRASS.defaultBlockState());//pState not used
        }
        else{
            flag=false;
        }
        if(flag){
            event.setResult(Event.Result.ALLOW);
        }
    }
    private static boolean growNetherWart(BlockPos pos, Level world) {
        boolean flag=false;
        for(int i=0;i<2;i++){
            if(world.getBlockState(pos).getValue(BlockStateProperties.AGE_3)<3){
                world.setBlockAndUpdate(pos,world.getBlockState(pos).cycle(BlockStateProperties.AGE_3));
                flag=true;
            }
        }
        return flag;
    }

    private static boolean growSugarcaneCactus(Level world, Random random, BlockPos pos, Block block) {
        if(world.getBlockState(pos.below()).is(block)){
            if(world.getBlockState(pos.below().below()).is(block)){
                return false;
            }
            else{
                return growSugarcaneCactus1(world, pos.above(), block);
            }
        }
        else{
            boolean flag=growSugarcaneCactus1(world, pos.above(), block);
            flag=growSugarcaneCactus1(world, pos.above().above(), block)||flag;
            return flag;
        }
    }
    private static boolean growSugarcaneCactus1(Level world, BlockPos pos, Block block){
        if(world.isEmptyBlock(pos)){
            world.setBlockAndUpdate(pos, block.defaultBlockState());
            BlockState blockState = block.defaultBlockState();// Not needed: .setValue(AGE, 0);
            world.setBlockAndUpdate(pos, blockState);
            //blockState.updateNeighbourShapes().neighborUpdate(world, pos, block, pos, false);
            return true;
        }
        return false;
    }

    private static void growClay(Level world, BlockPos pos) {
        BlockState dripLeaf=Blocks.SMALL_DRIPLEAF.defaultBlockState();
        BlockState DRIPLEAF_EAST = dripLeaf.setValue(SmallDripleafBlock.FACING, Direction.EAST);
        BlockState DRIPLEAF_WEST = dripLeaf.setValue(SmallDripleafBlock.FACING, Direction.WEST);
        BlockState DRIPLEAF_NORTH = dripLeaf.setValue(SmallDripleafBlock.FACING, Direction.NORTH);
        BlockState DRIPLEAF_SOUTH = dripLeaf.setValue(SmallDripleafBlock.FACING, Direction.SOUTH);
        BlockState[] bottomParts=new BlockState[]{DRIPLEAF_EAST,DRIPLEAF_WEST,DRIPLEAF_NORTH,DRIPLEAF_SOUTH};
        Random random=world.random;
        Function<BlockPos,Boolean> func;
        func=(pos1)->createDripLeaf(world,pos1);;//TallPlantBlock.placeAt
        placeBoneMeal(world,pos,blockState -> blockState.is(Blocks.CLAY),40,func,true);
    }
    private static boolean createDripLeaf(Level world, BlockPos pos){
        Direction direction=Helper.select(Helper.horizontalDirections());
        if(Helper.nextInt(6)==0){
            BlockState dripLeafStem=Blocks.BIG_DRIPLEAF_STEM.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,direction);
            BlockState dripLeaf=Blocks.BIG_DRIPLEAF.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,direction);
            world.setBlockAndUpdate(pos,dripLeafStem);
            world.setBlockAndUpdate(pos.above(),dripLeaf);
        }
        else if(Helper.nextInt(6)==0){
            BlockState dripLeaf=Blocks.BIG_DRIPLEAF.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,direction);
            world.setBlockAndUpdate(pos,dripLeaf);
        }
        else{
            BlockState dripLeaf=Blocks.SMALL_DRIPLEAF.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,direction);
            world.setBlockAndUpdate(pos,dripLeaf);
            world.setBlockAndUpdate(pos.above(),dripLeaf.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF,  DoubleBlockHalf.UPPER));
        }
        return true;
    }
    private static void growSand(Level world, BlockPos pos){
        BlockState state=Blocks.DEAD_BUSH.defaultBlockState();
        Function<BlockPos,Boolean> func=(pos1)->world.setBlockAndUpdate(pos1,state);
        placeBoneMeal(world,pos,blockState -> blockState.is(BlockTags.SAND),10,func);
    }
    private static void growMycelium(Level world, BlockPos pos){
        BlockState brown=Blocks.BROWN_MUSHROOM.defaultBlockState();
        BlockState red=Blocks.RED_MUSHROOM.defaultBlockState();
        Random random=world.random;
        Function<BlockPos,Boolean> func=(pos1)->world.setBlockAndUpdate(pos1,random.nextBoolean()?brown:red);
        placeBoneMeal(world,pos,blockState -> blockState.is(Blocks.MYCELIUM),10,func);
    }
    private static void growFlowers(Level world, BlockPos pos){
        BlockState state=world.getBlockState(pos);
        Function<BlockPos,Boolean> func=(pos1)->world.setBlockAndUpdate(pos1,state);
        placeBoneMeal(world,pos.below(),blockState -> blockState.is(BlockTags.DIRT),10,func);
    }

    private static void placeBoneMeal(Level world, BlockPos pos, Function<BlockState,Boolean> check, int tries, Function<BlockPos,Boolean> run){
        placeBoneMeal(world,pos,check,tries,run,false);
    }

    private static void placeBoneMeal(Level world, BlockPos pos, Function<BlockState,Boolean> check, int tries, Function<BlockPos,Boolean> run, boolean twoBlocks){
        if (world instanceof ServerLevel) {
            BlockPos blockPos=pos.above();
            Random random= world.random;
            label48:
            for(int i = 0; i < tries; ++i) {
                BlockPos blockPos2 = blockPos;
                for(int j = 0; j < i / 4; ++j) {
                    blockPos2 = blockPos2.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                    if (!check.apply(world.getBlockState(blockPos2.below())) || world.getBlockState(blockPos2).isFaceSturdy(world, blockPos2, Direction.UP)) {
                        continue label48;
                    }
                }
                if(world.isEmptyBlock(blockPos2)&&(!twoBlocks||world.isEmptyBlock(blockPos2.above()))){
                    run.apply(blockPos2);
                }
            }
        }
    }
    private static int score(Block block, BonemealEvent event){
        return BiomeScores.getScoreBlock(event.getWorld(),event.getPos(),block);
    }
    private static void growRootedDirt(Level world, Random random, BlockPos pos){
        if (world instanceof ServerLevel) {
            BlockPos blockPos=pos.below();
            label48:
            for(int i = 0; i < 20; ++i) {
                BlockPos blockPos2 = blockPos;
                for(int j = 0; j < i / 4; ++j) {
                    blockPos2 = blockPos2.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                    if (!world.getBlockState(blockPos2.above()).is(Blocks.ROOTED_DIRT) || world.getBlockState(blockPos2).isFaceSturdy(world, blockPos2,Direction.UP)) {//UP Might be a problem
                        continue label48;
                    }
                }
                if(world.isEmptyBlock(blockPos2)){
                    world.setBlockAndUpdate(blockPos2,Blocks.HANGING_ROOTS.defaultBlockState());
                }
            }
        }
    }
}
