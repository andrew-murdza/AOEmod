package com.example.examplemod.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BiomeTemplate {
    public Biome.BiomeCategory category;
    public Biome.Precipitation precipitation;
    public float temperature;
    public float downfall;
    public int waterColor;
    public int waterFogColor;
    public int fogColor;
    protected List<WeightedPlacedFeature> treeEntries=new ArrayList<>();
    protected GenerationStep.Decoration vegStep= GenerationStep.Decoration.VEGETAL_DECORATION;
    private static List<WeightedPlacedFeature> waterEntries=new ArrayList<>();
    private static List<WeightedPlacedFeature> grassEntries=new ArrayList<>();
    private static List<WeightedPlacedFeature> caveGrassEntries=new ArrayList<>();
    public BiomeTemplate(Biome.BiomeCategory category,Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, int fogColor){
        this.category=category;
        this.precipitation=precipitation;
        this.temperature=temperature;
        this.downfall=downfall;
        this.waterColor=waterColor;
        this.waterFogColor=waterFogColor;
        this.fogColor=fogColor;
    }

    public Biome createBiome(){
        MobSpawnSettings.Builder builder= new MobSpawnSettings.Builder();
        buildMobs(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
        buildFeatures(builder2);
        return createBiome1(builder,builder2);
    }
    protected void addWaterPlant(ConfiguredFeature feature, float chance){
        waterEntries.add(new WeightedPlacedFeature(feature.placed(), chance));
    }
    protected void addGrass(ConfiguredFeature feature, float chance){
        grassEntries.add(new WeightedPlacedFeature(feature.placed(), chance));
    }
    protected void addCaveGrass(ConfiguredFeature feature, float chance){
        caveGrassEntries.add(new WeightedPlacedFeature(feature.placed(), chance));
    }

    public abstract void buildMobs(MobSpawnSettings.Builder builder);

    public void addSpawn(EntityType type, int weight, int minSize, int maxSize, MobCategory group, MobSpawnSettings.Builder builder){
        builder.addSpawn(group, new MobSpawnSettings.SpawnerData(type, weight, minSize, maxSize));
    }

    protected void addBats(MobSpawnSettings.Builder builder){
        addSpawn(EntityType.BAT,10,8,8,MobCategory.AMBIENT,builder);
    }
    public void addLivestock(MobSpawnSettings.Builder builder){
        EntityType[] animals=new EntityType[]{EntityType.CHICKEN,EntityType.COW,EntityType.PIG,EntityType.SHEEP};
        for(EntityType entityType: animals){
            addSpawn(entityType,10,4,4,MobCategory.CREATURE,builder);
        }
        addSpawn(EntityType.RABBIT,20,8,8,MobCategory.CREATURE,builder);
    }
    public void addWolfFox(MobSpawnSettings.Builder builder){
        addSpawn(EntityType.WOLF,5,2,4,MobCategory.CREATURE,builder);
        addSpawn(EntityType.FOX,5,2,4,MobCategory.CREATURE,builder);
    }
    public void addRichRiverFish(MobSpawnSettings.Builder builder){
        addSpawn(EntityType.COD,15,3,6,MobCategory.WATER_AMBIENT,builder);
        addSpawn(EntityType.SALMON,15,1,5,MobCategory.WATER_AMBIENT,builder);
        addSpawn(EntityType.SQUID,8,1,4,MobCategory.WATER_CREATURE,builder);
    }
    public void addRiverFish(MobSpawnSettings.Builder builder){
        addSpawn(EntityType.COD,15,3,6,MobCategory.WATER_AMBIENT,builder);
        addSpawn(EntityType.SQUID,8,1,4,MobCategory.WATER_CREATURE,builder);
    }

    private Biome createBiome1(MobSpawnSettings.Builder spawnSettings, BiomeGenerationSettings.Builder genSettings) {
        Biome.BiomeBuilder builder1=new Biome.BiomeBuilder();
        builder1.precipitation(precipitation);
        builder1.biomeCategory(category);
        builder1.temperature(temperature);
        builder1.downfall(downfall);
        builder1.specialEffects(makeEffects().build());
        builder1.mobSpawnSettings(spawnSettings.build());
        builder1.generationSettings(genSettings.build());
        return builder1.build();
    }
    private BiomeSpecialEffects.Builder makeEffects(){
        BiomeSpecialEffects.Builder effects=new BiomeSpecialEffects.Builder();
        effects.waterColor(waterColor);
        effects.waterFogColor(waterFogColor);
        effects.skyColor(getSkyColor(temperature));
        effects.fogColor(fogColor);//12638463
        effects.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
        return effects;
    }
    public int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    protected void addTree(PlacedFeature tree, float chance){
        treeEntries.add(new WeightedPlacedFeature(tree,chance));
    }
    public void buildTrees(BiomeGenerationSettings.Builder builder, int count){
        if(count<=0){
            return;
        }
        ConfiguredFeature feature = AOEConfiguredFeatures.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(treeEntries,treeEntries.get(0).feature.get()));
        List<PlacementModifier> modifiers = VegetationPlacements.treePlacement(PlacementUtils.countExtra(count, 0.1F, 1));
        builder.addFeature(vegStep,feature.placed(modifiers));
    }
    public void buildPlants(List<WeightedPlacedFeature> features, BiomeGenerationSettings.Builder builder, int chance, boolean cave, boolean water){
        if(chance<=0){
            return;
        }
        PlacedFeature defaultFeature=features.get(features.size()-1).feature.get();
        RandomFeatureConfiguration config=new RandomFeatureConfiguration(features,defaultFeature);
        ConfiguredFeature feature=AOEConfiguredFeatures.RANDOM_SELECTOR.configured(config);
        Block fluid=cave?Blocks.CAVE_AIR:Blocks.AIR;
        fluid=water?Blocks.WATER:fluid;
        BlockPredicate pred1=BlockPredicate.hasSturdyFace(new BlockPos(0, -1, 0), Direction.UP);
        BlockPredicate pred2=BlockPredicate.matchesBlock(fluid,BlockPos.ZERO);
        BlockPredicate pred3=BlockPredicate.matchesBlock(fluid,new BlockPos(0, 1, 0));
        PlacementModifier[] modifiers=new PlacementModifier[4];//[inWater?4:5];
        modifiers[0]=BlockPredicateFilter.forPredicate(BlockPredicate.allOf(pred1,pred2,pred3));
        modifiers[1]=RarityFilter.onAverageOnceEvery(chance);
        modifiers[2]=BiomeFilter.biome();
        modifiers[3]=PlacementUtils.HEIGHTMAP_TOP_SOLID;
        if(water){
            modifiers[3]=CarvingMaskPlacement.forStep(GenerationStep.Carving.LIQUID);
        }
        if(cave){
            modifiers[3]=CarvingMaskPlacement.forStep(GenerationStep.Carving.AIR);
        }
        builder.addFeature(vegStep,feature.placed(modifiers));
    }
    public void buildWaterPlants(BiomeGenerationSettings.Builder builder, int chance){
        buildPlants(waterEntries, builder,chance,false,true);
    }
    public void buildGrass(BiomeGenerationSettings.Builder builder, int chance){
        buildPlants(grassEntries,builder,chance,false,false);
    }
    public void buildGrassCave(BiomeGenerationSettings.Builder builder, int chance){
        buildPlants(caveGrassEntries, builder,chance,false,true);
    }

    public abstract void buildSprings(BiomeGenerationSettings.Builder builder);
    public abstract void addOres(BiomeGenerationSettings.Builder builder);

    public void buildFeatures(BiomeGenerationSettings.Builder builder){
        buildSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }
}
