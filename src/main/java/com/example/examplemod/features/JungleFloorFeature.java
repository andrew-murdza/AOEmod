package com.example.examplemod.features;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.*;

public class JungleFloorFeature {

    public static Block[] flowers;
    public static List<Integer> ZeroTo11;
    public static List<Integer> ZeroTo1;

    public static void setup(){
        flowers=new Block[]{Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM,
                Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP,
                Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY};
        ZeroTo11= Arrays.asList(new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11});
        ZeroTo1=Arrays.asList(new Integer[]{0,1});
    }

    public static void generate(int x, int y, int z, WorldGenLevel level, ChunkGenerator chunkGenerator){
        Random random=level.getRandom();

        int xQuadrantFlower1=random.nextInt(2);
        int zQuadrantFlower1=random.nextInt(2);
        int flower1Index=random.nextInt(flowers.length);
        int flower2Index=random.nextInt(flowers.length);
        while(flower2Index==flower1Index){
            flower2Index=random.nextInt(flowers.length);
        }
        int xQuadrantFlower2=random.nextInt(2);
        int zQuadrantFlower2=random.nextInt(2);
        while(xQuadrantFlower1==xQuadrantFlower2&&zQuadrantFlower1==zQuadrantFlower2){
            xQuadrantFlower2=random.nextInt(2);
            zQuadrantFlower2=random.nextInt(2);
        }
        int[] numFlowers=new int[]{random.nextInt(3)+4};
        int numFlowers2=random.nextInt(3)+4;

    }
    private static <E> List<E> shuffle(List<E> list, int n){
        Collections.shuffle(list);
        return list.subList(0,n);
    }
}
