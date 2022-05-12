package com.example.examplemod.features;

import com.example.examplemod.Helper;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TropicalForestBiomeTemplate extends BiomeTemplate {

    public TropicalForestBiomeTemplate() {
        super(Biome.BiomeCategory.JUNGLE, Biome.Precipitation.RAIN, 1.0f, 1.0f, 4445678, 270131, 12638463);
    }

    @Override
    public void buildMobs(MobSpawnSettings.Builder builder) {
        //Fish
        addSpawn(EntityType.AXOLOTL,20,4,6, MobCategory.AXOLOTLS,builder);
        addSpawn(EntityType.GLOW_SQUID,10,4,6,MobCategory.UNDERGROUND_WATER_CREATURE,builder);
        addSpawn(EntityType.SQUID,10,4,6,MobCategory.WATER_CREATURE,builder);
        addSpawn(EntityType.TROPICAL_FISH,24,8,8,MobCategory.WATER_AMBIENT,builder);
        addSpawn(EntityType.PUFFERFISH,8,8,8,MobCategory.WATER_AMBIENT,builder);
        addSpawn(EntityType.COD,8,8,8,MobCategory.WATER_AMBIENT,builder);
        addSpawn(EntityType.SALMON,8,8,8,MobCategory.WATER_AMBIENT,builder);
        //Tropical Animals
        addSpawn(EntityType.OCELOT,5,1,3,MobCategory.CREATURE,builder);
        addSpawn(EntityType.PARROT,40,1,2,MobCategory.CREATURE,builder);
        addSpawn(EntityType.PANDA,20,1,2,MobCategory.CREATURE,builder);
        addSpawn(EntityType.POLAR_BEAR,10,1,2,MobCategory.CREATURE,builder);
        //Special Animals
        addWolfFox(builder);
        //Rideables
        addSpawn(EntityType.HORSE,10,4,4,MobCategory.CREATURE,builder);
        addSpawn(EntityType.LLAMA,10,4,4,MobCategory.CREATURE,builder);
        addSpawn(EntityType.DONKEY,5,4,4,MobCategory.CREATURE,builder);
        //Livestock
        addLivestock(builder);
        //Mooshrooms
        addSpawn(EntityType.MOOSHROOM,10,4,4,MobCategory.CREATURE,builder);
        //Bats
        addBats(builder);
        //Hostile Mobs
        addSpawn(EntityType.SLIME,400,4,4,MobCategory.MONSTER,builder);
        addSpawn(EntityType.ZOMBIE,50,4,4,MobCategory.MONSTER,builder);
        addSpawn(EntityType.DROWNED,100,4,4,MobCategory.MONSTER,builder);
        addSpawn(EntityType.SKELETON,100,4,4,MobCategory.MONSTER,builder);
        addSpawn(EntityType.SPIDER,100,4,4,MobCategory.MONSTER,builder);
        addSpawn(EntityType.CAVE_SPIDER,200,4,4,MobCategory.MONSTER,builder);
    }

    @Override
    public void buildSprings(BiomeGenerationSettings.Builder builder) {

    }

    @Override
    public void addOres(BiomeGenerationSettings.Builder builder) {

    }

    @Override
    public void buildFeatures(BiomeGenerationSettings.Builder builder){

        //Trees
        addTree(JungleTreesFeature.DARK_OAK_TREE_PLACED,0.1F);
        addTree(JungleTreesFeature.LARGE_OAK_TREE_PLACED,0.35F);
        addTree(JungleTreesFeature.ACACIA_TREE_PLACED,0.04F);
        addTree(JungleTreesFeature.BIRCH_TREE_PLACED,0.04F);
        addTree(JungleTreesFeature.JUNGLE_TREE_PLACED,0.15F);
        addTree(JungleTreesFeature.GIANT_JUNGLE_TREE_PLACED,0.2F);
        addTree(JungleTreesFeature.GIANT_SPRUCE_TREE_PLACED,0.05F);
        addTree(JungleTreesFeature.GIANT_PINE_TREE_PLACED,0.05F);
        addTree(JungleTreesFeature.SPRUCE_TREE_PLACED,0.02F);
        buildTrees(builder,60);

        //Grass
        addGrass(AOEConfiguredFeatures.simpleBlockPlacers.get(Blocks.GRASS),0.44F);
        addGrass(AOEConfiguredFeatures.simpleBlockPlacers.get(Blocks.FERN),0.16F);
        addGrass(AOEConfiguredFeatures.simpleBlockPlacers.get(Blocks.LARGE_FERN),0.20F);
        addGrass(AOEConfiguredFeatures.simpleBlockPlacers.get(Blocks.TALL_GRASS),0.10F);
        addGrass(AOEConfiguredFeatures.simpleBlockPlacers.get(Blocks.GLOW_LICHEN),0.10F);
        buildGrass(builder,2);//need to edit chance

        //Water Plants
        for(Block block: Helper.coralPlantsAndFans){
            addWaterPlant(AOEConfiguredFeatures.simpleBlockPlacers.get(block),0.025F);
        }
        addWaterPlant(AOEConfiguredFeatures.simpleBlockPlacers.get(Blocks.SEA_PICKLE),0.10F);
        addWaterPlant(AOEConfiguredFeatures.simpleBlockPlacers.get(Blocks.TALL_SEAGRASS),0.15F);
        addWaterPlant(AOEConfiguredFeatures.simpleBlockPlacers.get(Blocks.SEAGRASS),0.45F);
        addWaterPlant(AquaticFeatures.KELP,0.05F);
        buildWaterPlants(builder,2);//need to edit chance

        builder.addFeature(vegStep, VegetationPlacements.PATCH_SUGAR_CANE_DESERT);

        //Missing Custom Features
        //Small flowers
        //Tall flowers
        //Mushrooms
        //Dripleaves
        //Dead Bush
        //Cactus
        //Sweet Berries
        //(Flowering) Azalea
        super.buildFeatures(builder);
    }
}
