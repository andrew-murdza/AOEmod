package com.example.examplemod;


import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockDropsEventHandler {

//    @SubscribeEvent
//    public static void cropDrops(BlockEvent.BreakEvent event){
//        Level world=(Level)event.getWorld();
//        if(!world.isClientSide&&(event.getPlayer()==null||!event.getPlayer().isCreative())) {
//            if(world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)){
//                BlockPos pos=event.getPos();
//                BlockState state=event.getState();
//                int score=BiomeScores.getScoreBlock(world,pos,state.getBlock());
//                if(state.is(Blocks.WHEAT)&& Helper.isMaxAge(state,BlockStateProperties.AGE_7)){
//                    Helper.dropItemScore(world,pos,Items.WHEAT,new int[]{0,0,5,3,1},Helper.DropType.CHANCE,score);
//                    Helper.dropItemScore(world,pos,Items.WHEAT_SEEDS,new int[]{0,0,1,2,3},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.BEETROOTS)&&Helper.isMaxAge(state,BlockStateProperties.AGE_5)){
//                    Helper.dropItemScore(world,pos,Items.BEETROOT,new int[]{0,0,5,3,1},Helper.DropType.CHANCE,score);
//                    Helper.dropItemScore(world,pos,Items.BEETROOT_SEEDS,new int[]{0,0,1,2,3},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.CARROTS)&&Helper.isMaxAge(state,BlockStateProperties.AGE_7)){
//                    Helper.dropItemScore(world,pos,Items.CARROT,new int[]{0,0,2,5,8},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.POTATOES)&&Helper.isMaxAge(state,BlockStateProperties.AGE_7)){
//                    Helper.dropItemScore(world,pos,Items.POTATO,new int[]{0,0,2,5,8},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.COCOA)&&Helper.isMaxAge(state,BlockStateProperties.AGE_2)){
//                    Helper.dropItemScore(world,pos,Items.COCOA_BEANS,new int[]{0,0,1,3,6},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.SWEET_BERRY_BUSH)&&state.getValue(BlockStateProperties.AGE_3)==2){
//                    Helper.dropItemScore(world,pos,Items.SWEET_BERRIES,new int[]{0,0,1,2,3},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.SWEET_BERRY_BUSH)&&Helper.isMaxAge(state,BlockStateProperties.AGE_3)){
//                    Helper.dropItemScore(world,pos,Items.SWEET_BERRIES,new int[]{0,0,1,3,5},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.NETHER_WART)&&Helper.isMaxAge(state,BlockStateProperties.AGE_3)){
//                    Helper.dropItemScore(world,pos,Items.NETHER_WART,new int[]{0,0,1,3,5},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.ACACIA_LEAVES)){
//                    saplingDrop(world,pos,Items.ACACIA_SAPLING,score);
//                }
//                else if(state.is(Blocks.BIRCH_LEAVES)){
//                    saplingDrop(world,pos,Items.BIRCH_SAPLING,score);
//                }
//                else if(state.is(Blocks.DARK_OAK_LEAVES)){
//                    saplingDrop(world,pos,Items.DARK_OAK_SAPLING,score);
//                }
//                else if(state.is(Blocks.JUNGLE_LEAVES)){
//                    saplingDrop(world,pos,Items.JUNGLE_SAPLING,score);
//                }
//                else if(state.is(Blocks.OAK_LEAVES)){
//                    saplingDrop(world,pos,Items.OAK_SAPLING,score);
//                }
//                else if(state.is(Blocks.SPRUCE_LEAVES)){
//                    saplingDrop(world,pos,Items.SPRUCE_SAPLING,score);
//                }
//                else if(state.is(Blocks.AZALEA_LEAVES)){
//                    saplingDrop(world,pos,Items.AZALEA,score);
//                }
//                else if(state.is(Blocks.FLOWERING_AZALEA_LEAVES)){
//                    saplingDrop(world,pos,Items.FLOWERING_AZALEA,score);
//                }
//                else if(state.is(Blocks.GRASS_BLOCK)||state.is(Blocks.FERN)||state.is(Blocks.TALL_GRASS)||state.is(Blocks.LARGE_FERN)){
//                    Helper.dropItemScore(world,pos,Items.WHEAT_SEEDS,new int[]{0,0,20,10,5},Helper.DropType.CHANCE,score);
//                    Helper.dropItemScore(world,pos,Items.BEETROOT_SEEDS,new int[]{0,0,20,10,5},Helper.DropType.CHANCE,score);
//                    Helper.dropItemScore(world,pos,Items.PUMPKIN_SEEDS,new int[]{0,0,60,30,15},Helper.DropType.CHANCE,score);
//                    Helper.dropItemScore(world,pos,Items.MELON_SEEDS,new int[]{0,0,60,30,15},Helper.DropType.CHANCE,score);
//                }
//                else if(state.is(Blocks.DEAD_BUSH)){
//                    Helper.dropItemScore(world,pos,Items.STICK,new int[]{0,0,1,2,3},Helper.DropType.RANDOM,score);
//                }
//                else if(state.is(Blocks.AMETHYST_CLUSTER)){
//                    Helper.dropItemScore(world,pos,Items.AMETHYST_SHARD,new int[]{0,0,1,2,3},Helper.DropType.ALL,score);
//                }
//                else if(state.is(BlockTags.COAL_ORES)){
//                    oreDrops(world,pos,Items.COAL,score);
//                }
//                else if(state.is(BlockTags.COPPER_ORES)){
//                    oreDrops(world,pos,Items.RAW_COPPER,score);
//                }
//                else if(state.is(BlockTags.IRON_ORES)){
//                    oreDrops(world,pos,Items.RAW_IRON,score);
//                }
//                else if(state.is(BlockTags.GOLD_ORES)){
//                    oreDrops(world,pos,Items.RAW_GOLD,score);
//                }
//                else if(state.is(BlockTags.LAPIS_ORES)){
//                    oreDrops(world,pos,Items.LAPIS_LAZULI,score);
//                }
//                else if(state.is(BlockTags.REDSTONE_ORES)){
//                    oreDrops(world,pos,Items.REDSTONE,score);
//                }
//                else if(state.is(BlockTags.DIAMOND_ORES)){
//                    oreDrops(world,pos,Items.DIAMOND,score);
//                }
//                else if(state.is(BlockTags.EMERALD_ORES)){
//                    oreDrops(world,pos,Items.EMERALD,score);
//                }
//                else if(state.is(Blocks.NETHER_QUARTZ_ORE)){
//                    oreDrops(world,pos,Items.QUARTZ,score);
//                }
//                else if(state.is(Blocks.NETHER_GOLD_ORE)){
//                    Helper.dropItemScore(world,pos,Items.GOLD_NUGGET,new int[]{0,0,2,4,8},Helper.DropType.ALL,score);
//                }
//                //No Ancient Debris
//            }
//        }
//    }
//    private static void saplingDrop(Level world, BlockPos pos, Item sapling, int score){
//        if(sapling==Items.JUNGLE_SAPLING){
//            Helper.dropItem(world,pos,sapling,40,Helper.DropType.CHANCE);
//        }
//        Helper.dropItemScore(world,pos,sapling,new int[]{0,0,200,40,12}, Helper.DropType.CHANCE,score);
//        Helper.dropItemScore(world,pos,Items.STICK,new int[]{0,0,200,40,12}, Helper.DropType.CHANCE,score);
//        if(sapling==Items.OAK_SAPLING||sapling==Items.DARK_OAK_SAPLING){
//            Helper.dropItemScore(world,pos,Items.APPLE,new int[]{0,0,1000,300,100}, Helper.DropType.CHANCE,score);
//        }
//    }
//    private static void oreDrops(Level world, BlockPos pos, Item ore, int score){
//        int[] oreChances=new int[]{0,0,4,2,1};
//        Helper.dropItemScore(world,pos,ore,oreChances, Helper.DropType.CHANCE,score);
//    }

}
