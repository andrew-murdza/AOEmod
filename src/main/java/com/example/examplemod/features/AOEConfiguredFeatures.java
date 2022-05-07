package com.example.examplemod.features;

import com.example.examplemod.RandomSelectionFeature;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static Feature RANDOM_SELECTOR=new RandomSelectionFeature(RandomFeatureConfiguration.CODEC);

    static {
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

    public static void updateFeatures(){
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
