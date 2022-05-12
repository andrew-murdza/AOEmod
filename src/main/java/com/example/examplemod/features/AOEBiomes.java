package com.example.examplemod.features;

import net.minecraft.world.level.biome.Biome;

public class AOEBiomes {

    public static Biome tropicalForest;

    public static void setup(){
        tropicalForest=new TropicalForestBiomeTemplate().createBiome();
    }

}
