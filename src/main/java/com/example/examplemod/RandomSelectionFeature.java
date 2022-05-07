package com.example.examplemod;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;

import java.util.List;
import java.util.Random;

public class RandomSelectionFeature extends Feature<RandomFeatureConfiguration> {
    public RandomSelectionFeature(Codec<RandomFeatureConfiguration> p_66619_) {
        super(p_66619_);
    }

    public boolean place(FeaturePlaceContext<RandomFeatureConfiguration> config) {
        RandomFeatureConfiguration randomFeatureConfig = config.config();
        Random random = config.random();
        WorldGenLevel level = config.level();
        ChunkGenerator chunkGenerator = config.chunkGenerator();
        BlockPos blockPos = config.origin();
        List<WeightedPlacedFeature> entries=randomFeatureConfig.features;
        float[] cutoffs=cutoffs(entries);
        float a=random.nextFloat();
        for(int i=0;i<randomFeatureConfig.features.size();i++){
            if(a>=cutoffs[i]&&a<cutoffs[i+1]){
                return entries.get(i).place(level, chunkGenerator, random, blockPos);
            }
        }
        return randomFeatureConfig.defaultFeature.get().place(level, chunkGenerator, random, blockPos);
    }
    private float[] cutoffs(List<WeightedPlacedFeature> entries){
        float sum=0;
        for(WeightedPlacedFeature entry:entries){
            sum=sum+entry.chance;
        }
        int size=entries.size()+1;
        float[] cutoffs=new float[size];
        float sum1=0;
        cutoffs[0]=0;
        for(int i=0;i<entries.size();i++){
            sum1=sum1+entries.get(i).chance/(sum<1?1:sum);
            cutoffs[i+1]=sum1;
        }
        return cutoffs;
    }
}
