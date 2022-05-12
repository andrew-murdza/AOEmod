package com.example.examplemod.features;

import com.example.examplemod.Helper;
import com.example.examplemod.RandomSelectionFeature;
import com.example.examplemod.features.AOECoralClawFeature;
import com.example.examplemod.features.AOECoralMushroomFeature;
import com.example.examplemod.features.AOECoralTreeFeature;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.*;
import java.util.function.Supplier;

public class AOEConfiguredFeatures {
    public static Map<Block,Feature> coralTreeFeatures=new HashMap<>();
    public static Map<Block,Feature> coralMushroomFeatures=new HashMap<>();
    public static Map<Block,Feature> coralClawFeatures=new HashMap<>();
    public static List<Feature> coralFeatures;
    public static ConfiguredFeature giantCorals;
    public static PlacedFeature giantCoralPlants;
    public static PlacedFeature tropicalGrass;
    public static PlacedFeature mountainsGrass;
    public static HashMap<Block,ConfiguredFeature> simpleBlockPlacers;
    public static ConfiguredFeature[] netherWartFeatures;
    public static ConfiguredFeature[] sugarCaneFeatures;
    public static ConfiguredFeature[] cactusFeatures;

    public static Feature RANDOM_SELECTOR=new RandomSelectionFeature(RandomFeatureConfiguration.CODEC);

    public static void update() {
        List<Block> simpleBlocks= Arrays.asList(Blocks.RED_MUSHROOM,Blocks.BROWN_MUSHROOM,Blocks.CRIMSON_FUNGUS,Blocks.WARPED_FUNGUS,
                Blocks.CRIMSON_ROOTS,Blocks.WARPED_ROOTS,Blocks.NETHER_SPROUTS,Blocks.DEAD_BUSH,Blocks.FERN,
                Blocks.LARGE_FERN,Blocks.GRASS,Blocks.TALL_GRASS,Blocks.SEAGRASS,Blocks.TALL_SEAGRASS,
                Blocks.FLOWERING_AZALEA,Blocks.AZALEA,Blocks.WITHER_ROSE,Blocks.NETHER_SPROUTS);
        simpleBlocks.addAll(Helper.smallFlowers);
        simpleBlocks.addAll(Helper.coralPlantsAndFans);
        for(Block block: simpleBlocks){
            addBlockPlacer(block);
        }
        addBlockPlacer(Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3,3));
        addBlockPlacer(Blocks.SEA_PICKLE.defaultBlockState().setValue(BlockStateProperties.PICKLES,4));
        addBlockPlacer(Blocks.GLOW_LICHEN.defaultBlockState().setValue(BlockStateProperties.DOWN,true));
        for(int i=0;i<4;i++){
            netherWartFeatures[i]=createSimpleBlock(Blocks.NETHER_WART.defaultBlockState().setValue(BlockStateProperties.AGE_3,i));
        }
        for(int i=0;i<3;i++){
            sugarCaneFeatures[i]=createSimpleBlockColumn(Blocks.SUGAR_CANE,i);
            sugarCaneFeatures[i]=createSimpleBlockColumn(Blocks.CACTUS,i);
        }



        coralTreeFeatures.put(Blocks.TUBE_CORAL,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.TUBE_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.TUBE_CORAL_FAN,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.TUBE_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.HORN_CORAL,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.HORN_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.HORN_CORAL_FAN,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.HORN_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.FIRE_CORAL,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.FIRE_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.FIRE_CORAL_FAN,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.FIRE_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.BUBBLE_CORAL,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.BUBBLE_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.BUBBLE_CORAL_FAN,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.BUBBLE_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.BRAIN_CORAL,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.BRAIN_CORAL_BLOCK));
        coralTreeFeatures.put(Blocks.BRAIN_CORAL_FAN,new AOECoralTreeFeature(NoneFeatureConfiguration.CODEC,Blocks.BRAIN_CORAL_BLOCK));

        coralMushroomFeatures.put(Blocks.TUBE_CORAL,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.TUBE_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.TUBE_CORAL_FAN,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.TUBE_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.HORN_CORAL,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.HORN_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.HORN_CORAL_FAN,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.HORN_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.FIRE_CORAL,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.FIRE_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.FIRE_CORAL_FAN,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.FIRE_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.BUBBLE_CORAL,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.BUBBLE_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.BUBBLE_CORAL_FAN,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.BUBBLE_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.BRAIN_CORAL,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.BRAIN_CORAL_BLOCK));
        coralMushroomFeatures.put(Blocks.BRAIN_CORAL_FAN,new AOECoralMushroomFeature(NoneFeatureConfiguration.CODEC,Blocks.BRAIN_CORAL_BLOCK));

        coralClawFeatures.put(Blocks.TUBE_CORAL,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.TUBE_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.TUBE_CORAL_FAN,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.TUBE_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.HORN_CORAL,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.HORN_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.HORN_CORAL_FAN,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.HORN_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.FIRE_CORAL,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.FIRE_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.FIRE_CORAL_FAN,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.FIRE_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.BUBBLE_CORAL,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.BUBBLE_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.BUBBLE_CORAL_FAN,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.BUBBLE_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.BRAIN_CORAL,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.BRAIN_CORAL_BLOCK));
        coralClawFeatures.put(Blocks.BRAIN_CORAL_FAN,new AOECoralClawFeature(NoneFeatureConfiguration.CODEC,Blocks.BRAIN_CORAL_BLOCK));

        coralFeatures=new ArrayList<>(coralClawFeatures.values());
        coralFeatures.addAll(coralMushroomFeatures.values());
        coralFeatures.addAll(coralTreeFeatures.values());

        tropicalGrass=createWeightedGrass(4,1);
        mountainsGrass=createWeightedGrass(1,3);
    }

    public static void addBlockPlacer(Block block){
        addBlockPlacer(block.defaultBlockState());
    }
    public static void addBlockPlacer(BlockState state){
        simpleBlockPlacers.put(state.getBlock(),createSimpleBlock(state));
    }
    public static ConfiguredFeature createSimpleBlock(BlockState state){
        return Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(state)));
    }
    public static ConfiguredFeature createSimpleBlockColumn(Block block,int height){
        return Feature.BLOCK_COLUMN.configured(BlockColumnConfiguration.simple(ConstantInt.of(height),
                BlockStateProvider.simple(block)));
    }


    public static void updateFeatures(){
        update();
        List<Supplier<PlacedFeature>> features=new ArrayList<>();
        for(Feature feature: coralFeatures){
            features.add(()->feature.configured(FeatureConfiguration.NONE).placed());
        }
        giantCorals=RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(features));
        giantCoralPlants=giantCorals.placed(CountPlacement.of(2), PlacementUtils.HEIGHTMAP_TOP_SOLID);//,InSquarePlacement.spread()
    }
    public static PlacedFeature createWeightedGrass(int grassWeight, int fernWeight){
        SimpleWeightedRandomList.Builder<BlockState> states=new SimpleWeightedRandomList.Builder<>();
        states.add(Blocks.GRASS.defaultBlockState(),grassWeight);
        states.add(Blocks.FERN.defaultBlockState(),fernWeight);
        BlockStateProvider provider= new WeightedStateProvider(states.build());
        return Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(provider)).onlyWhenEmpty();
    }
}
