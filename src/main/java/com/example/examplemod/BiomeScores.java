package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;

public class BiomeScores {
    public static HashMap<Block, HashMap<Biome.BiomeCategory, Integer>> BIOME_SCORES_MAP=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer> ANIMAL_SCORES_MAP=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer> MUSHROOM_SCORES_MAP=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>cropScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>stemCropScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>plantScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>waterScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>cactusScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>treeScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>mineralScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>mountainScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>desertScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>monsterScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>netherScores=new HashMap<>();
    public static HashMap<Biome.BiomeCategory, Integer>endScores=new HashMap<>();
    static{
        cropScores.put(Biome.BiomeCategory.OCEAN,4);
        cropScores.put(Biome.BiomeCategory.JUNGLE,4);
        cropScores.put(Biome.BiomeCategory.SAVANNA,3);
        cropScores.put(Biome.BiomeCategory.PLAINS,2);
        cropScores.put(Biome.BiomeCategory.MOUNTAIN,1);
        cropScores.put(Biome.BiomeCategory.DESERT,0);
        cropScores.put(Biome.BiomeCategory.MESA,0);
        cropScores.put(Biome.BiomeCategory.NETHER,0);

        monsterScores.put(Biome.BiomeCategory.OCEAN,0);
        monsterScores.put(Biome.BiomeCategory.JUNGLE,0);
        monsterScores.put(Biome.BiomeCategory.SAVANNA,1);
        monsterScores.put(Biome.BiomeCategory.PLAINS,2);
        monsterScores.put(Biome.BiomeCategory.MOUNTAIN,3);
        monsterScores.put(Biome.BiomeCategory.DESERT,4);
        monsterScores.put(Biome.BiomeCategory.MESA,4);
        monsterScores.put(Biome.BiomeCategory.NETHER,4);

        mineralScores.put(Biome.BiomeCategory.OCEAN,0);
        mineralScores.put(Biome.BiomeCategory.JUNGLE,0);
        mineralScores.put(Biome.BiomeCategory.SAVANNA,1);
        mineralScores.put(Biome.BiomeCategory.PLAINS,1);
        mineralScores.put(Biome.BiomeCategory.MOUNTAIN,4);
        mineralScores.put(Biome.BiomeCategory.DESERT,3);//3
        mineralScores.put(Biome.BiomeCategory.MESA,3);
        mineralScores.put(Biome.BiomeCategory.NETHER,3);

        mountainScores.put(Biome.BiomeCategory.OCEAN,0);
        mountainScores.put(Biome.BiomeCategory.JUNGLE,0);
        mountainScores.put(Biome.BiomeCategory.SAVANNA,1);
        mountainScores.put(Biome.BiomeCategory.PLAINS,1);
        mountainScores.put(Biome.BiomeCategory.MOUNTAIN,4);
        mountainScores.put(Biome.BiomeCategory.DESERT,2);
        mountainScores.put(Biome.BiomeCategory.MESA,2);
        mountainScores.put(Biome.BiomeCategory.NETHER,0);

        desertScores.put(Biome.BiomeCategory.OCEAN,0);
        desertScores.put(Biome.BiomeCategory.JUNGLE,0);
        desertScores.put(Biome.BiomeCategory.SAVANNA,1);
        desertScores.put(Biome.BiomeCategory.PLAINS,1);
        desertScores.put(Biome.BiomeCategory.MOUNTAIN,3);
        desertScores.put(Biome.BiomeCategory.DESERT,4);
        desertScores.put(Biome.BiomeCategory.MESA,4);
        desertScores.put(Biome.BiomeCategory.NETHER,0);


        stemCropScores.put(Biome.BiomeCategory.OCEAN,4);
        stemCropScores.put(Biome.BiomeCategory.JUNGLE,4);
        stemCropScores.put(Biome.BiomeCategory.SAVANNA,3);
        stemCropScores.put(Biome.BiomeCategory.PLAINS,2);
        stemCropScores.put(Biome.BiomeCategory.MOUNTAIN,1);
        stemCropScores.put(Biome.BiomeCategory.DESERT,1);
        stemCropScores.put(Biome.BiomeCategory.MESA,1);
        stemCropScores.put(Biome.BiomeCategory.NETHER,0);

        plantScores.put(Biome.BiomeCategory.OCEAN,4);
        plantScores.put(Biome.BiomeCategory.JUNGLE,4);
        plantScores.put(Biome.BiomeCategory.SAVANNA,1);
        plantScores.put(Biome.BiomeCategory.PLAINS,2);
        plantScores.put(Biome.BiomeCategory.MOUNTAIN,2);
        plantScores.put(Biome.BiomeCategory.DESERT,0);
        plantScores.put(Biome.BiomeCategory.MESA,0);
        plantScores.put(Biome.BiomeCategory.NETHER,0);

        waterScores.put(Biome.BiomeCategory.OCEAN,4);
        waterScores.put(Biome.BiomeCategory.JUNGLE,4);
        waterScores.put(Biome.BiomeCategory.SAVANNA,1);
        waterScores.put(Biome.BiomeCategory.PLAINS,2);
        waterScores.put(Biome.BiomeCategory.MOUNTAIN,3);
        waterScores.put(Biome.BiomeCategory.DESERT,0);
        waterScores.put(Biome.BiomeCategory.MESA,0);
        waterScores.put(Biome.BiomeCategory.NETHER,0);

        ANIMAL_SCORES_MAP.put(Biome.BiomeCategory.OCEAN,4);
        ANIMAL_SCORES_MAP.put(Biome.BiomeCategory.JUNGLE,4);
        ANIMAL_SCORES_MAP.put(Biome.BiomeCategory.SAVANNA,4);
        ANIMAL_SCORES_MAP.put(Biome.BiomeCategory.PLAINS,2);
        ANIMAL_SCORES_MAP.put(Biome.BiomeCategory.MOUNTAIN,1);
        ANIMAL_SCORES_MAP.put(Biome.BiomeCategory.DESERT,0);
        ANIMAL_SCORES_MAP.put(Biome.BiomeCategory.MESA,0);

        MUSHROOM_SCORES_MAP.put(Biome.BiomeCategory.OCEAN,4);
        MUSHROOM_SCORES_MAP.put(Biome.BiomeCategory.JUNGLE,4);
        MUSHROOM_SCORES_MAP.put(Biome.BiomeCategory.SAVANNA,1);
        MUSHROOM_SCORES_MAP.put(Biome.BiomeCategory.PLAINS,2);
        MUSHROOM_SCORES_MAP.put(Biome.BiomeCategory.MOUNTAIN,3);
        MUSHROOM_SCORES_MAP.put(Biome.BiomeCategory.DESERT,2);
        MUSHROOM_SCORES_MAP.put(Biome.BiomeCategory.MESA,2);

        cactusScores.put(Biome.BiomeCategory.OCEAN,4);
        cactusScores.put(Biome.BiomeCategory.JUNGLE,4);
        cactusScores.put(Biome.BiomeCategory.SAVANNA,1);
        cactusScores.put(Biome.BiomeCategory.PLAINS,1);
        cactusScores.put(Biome.BiomeCategory.MOUNTAIN,0);
        cactusScores.put(Biome.BiomeCategory.DESERT,3);
        cactusScores.put(Biome.BiomeCategory.MESA,3);
        cactusScores.put(Biome.BiomeCategory.NETHER,0);

        treeScores.put(Biome.BiomeCategory.OCEAN,4);
        treeScores.put(Biome.BiomeCategory.JUNGLE,4);
        treeScores.put(Biome.BiomeCategory.SAVANNA,3);
        treeScores.put(Biome.BiomeCategory.PLAINS,1);
        treeScores.put(Biome.BiomeCategory.MOUNTAIN,0);
        treeScores.put(Biome.BiomeCategory.DESERT,3);
        treeScores.put(Biome.BiomeCategory.MESA,3);
        treeScores.put(Biome.BiomeCategory.NETHER,0);

        netherScores.put(Biome.BiomeCategory.OCEAN,0);
        netherScores.put(Biome.BiomeCategory.JUNGLE,0);
        netherScores.put(Biome.BiomeCategory.SAVANNA,1);
        netherScores.put(Biome.BiomeCategory.PLAINS,1);
        netherScores.put(Biome.BiomeCategory.MOUNTAIN,1);
        netherScores.put(Biome.BiomeCategory.DESERT,3);
        netherScores.put(Biome.BiomeCategory.MESA,3);
        netherScores.put(Biome.BiomeCategory.NETHER,4);


        endScores.put(Biome.BiomeCategory.OCEAN,0);
        endScores.put(Biome.BiomeCategory.JUNGLE,0);
        endScores.put(Biome.BiomeCategory.SAVANNA,1);
        endScores.put(Biome.BiomeCategory.PLAINS,1);
        endScores.put(Biome.BiomeCategory.MOUNTAIN,4);
        endScores.put(Biome.BiomeCategory.DESERT,2);
        endScores.put(Biome.BiomeCategory.MESA,2);
        endScores.put(Biome.BiomeCategory.NETHER,1);

        Block[] cropBlocks=new Block[]{Blocks.WHEAT,Blocks.CARROTS,Blocks.POTATOES,Blocks.BEETROOTS};
        Block[] stemCropBlocks=new Block[]{Blocks.PUMPKIN_STEM,Blocks.MELON_STEM,Blocks.ATTACHED_MELON_STEM,Blocks.ATTACHED_PUMPKIN_STEM};
        Block[] waterBlocks=new Block[]{Blocks.KELP,Blocks.SEAGRASS,Blocks.TALL_SEAGRASS,Blocks.TURTLE_EGG};
        Block[] plantBlocks=new Block[]{Blocks.BAMBOO,Blocks.BAMBOO_SAPLING,Blocks.SUGAR_CANE,Blocks.COCOA,Blocks.SWEET_BERRY_BUSH,Blocks.CAVE_VINES
                ,Blocks.CAVE_VINES_PLANT,Blocks.VINE,Blocks.GRASS,Blocks.FERN,Blocks.TALL_GRASS,Blocks.LARGE_FERN,Blocks.GRASS_BLOCK,Blocks.GLOW_LICHEN};
        Block[] treeBlocks=new Block[]{Blocks.ACACIA_LEAVES,Blocks.BIRCH_LEAVES,Blocks.DARK_OAK_LEAVES,
                Blocks.JUNGLE_LEAVES,Blocks.OAK_LEAVES,Blocks.SPRUCE_LEAVES,Blocks.AZALEA_LEAVES,Blocks.FLOWERING_AZALEA_LEAVES,Blocks.SPRUCE_SAPLING
                ,Blocks.JUNGLE_SAPLING,Blocks.DARK_OAK_SAPLING,Blocks.BIRCH_SAPLING,Blocks.ACACIA_SAPLING,Blocks.OAK_SAPLING};
        Block[] mushroomBlocks=new Block[]{Blocks.BROWN_MUSHROOM,Blocks.RED_MUSHROOM,Blocks.MYCELIUM};
        Block[] mineralBlocks=new Block[]{Blocks.COAL_ORE,Blocks.IRON_ORE,Blocks.DEEPSLATE_COAL_ORE,Blocks.DEEPSLATE_IRON_ORE,Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,Blocks.BUDDING_AMETHYST,Blocks.AMETHYST_CLUSTER,Blocks.POINTED_DRIPSTONE,
                Blocks.STONE,Blocks.GRAVEL,Blocks.DIRT,Blocks.CLAY,Blocks.COBBLESTONE,Blocks.OBSIDIAN,Blocks.DEEPSLATE,Blocks.TUFF};//Obsidian
        Block[] mountainBlocks=new Block[]{Blocks.EMERALD_ORE,Blocks.DEEPSLATE_EMERALD_ORE,Blocks.DIAMOND_ORE,Blocks.DEEPSLATE_DIAMOND_ORE
                ,Blocks.LAPIS_ORE,Blocks.DEEPSLATE_LAPIS_ORE};
        Block[] desertBlocks=new Block[]{Blocks.REDSTONE_ORE,Blocks.DEEPSLATE_REDSTONE_ORE,Blocks.GOLD_ORE,Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.SANDSTONE,Blocks.RED_SAND,Blocks.RED_SANDSTONE,Blocks.TERRACOTTA,Blocks.BLACK_TERRACOTTA,Blocks.BLUE_TERRACOTTA
                ,Blocks.BROWN_TERRACOTTA,Blocks.CYAN_TERRACOTTA,Blocks.GRAY_TERRACOTTA,Blocks.GREEN_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA,Blocks.LIGHT_GRAY_TERRACOTTA,Blocks.LIME_TERRACOTTA,Blocks.MAGENTA_TERRACOTTA,
                Blocks.ORANGE_TERRACOTTA,Blocks.PINK_TERRACOTTA,Blocks.RED_TERRACOTTA,Blocks.PURPLE_TERRACOTTA,
                Blocks.WHITE_TERRACOTTA,Blocks.YELLOW_TERRACOTTA};
        Block[] netherBlocks=new Block[]{Blocks.TWISTING_VINES,Blocks.WEEPING_VINES, Blocks.NETHER_WART,Blocks.NETHER_GOLD_ORE,Blocks.NETHER_QUARTZ_ORE,
                Blocks.NETHERRACK,Blocks.GLOWSTONE, Blocks.MAGMA_BLOCK,Blocks.SOUL_SAND,Blocks.SOUL_SOIL,
                Blocks.ANCIENT_DEBRIS,Blocks.GILDED_BLACKSTONE,Blocks.CRYING_OBSIDIAN};//Ancient Debris
        Block[] cactusBlocks=new Block[]{Blocks.CACTUS,Blocks.DEAD_BUSH,Blocks.SAND};
        Block[] endBlocks=new Block[]{Blocks.CHORUS_FLOWER,Blocks.CHORUS_PLANT};
        //,Blocks.ACACIA_SAPLING,
        //Blocks.BIRCH_SAPLING,Blocks.DARK_OAK_SAPLING,Blocks.JUNGLE_SAPLING,
        //Blocks.OAK_SAPLING,Blocks.SPRUCE_SAPLING};
        for(Block block : cropBlocks){
            BIOME_SCORES_MAP.put(block,cropScores);
        }
        for(Block block : stemCropBlocks){
            BIOME_SCORES_MAP.put(block,stemCropScores);
        }
        for(Block block : plantBlocks){
            BIOME_SCORES_MAP.put(block,plantScores);
        }
        for(Block block : waterBlocks){
            BIOME_SCORES_MAP.put(block,waterScores);
        }
        for(Block block : treeBlocks){
            BIOME_SCORES_MAP.put(block,treeScores);
        }
        for(Block block : mushroomBlocks){
            BIOME_SCORES_MAP.put(block,MUSHROOM_SCORES_MAP);
        }
        for(Block block: mineralBlocks){
            BIOME_SCORES_MAP.put(block,mineralScores);
        }
        for(Block block: mountainBlocks){
            BIOME_SCORES_MAP.put(block,mountainScores);
        }
        for(Block block: desertBlocks){
            BIOME_SCORES_MAP.put(block,desertScores);
        }
        for(Block block: cactusBlocks){
            BIOME_SCORES_MAP.put(block,cactusScores);
        }
        for(Block block: netherBlocks){
            BIOME_SCORES_MAP.put(block,netherScores);
        }
        for(Block block: endBlocks){
            BIOME_SCORES_MAP.put(block,endScores);
        }
    }
    public static int getScoreEntity(Entity entity){
        Biome.BiomeCategory category=getCategory(entity.level,entity.getOnPos());
        int score=1;
        if(entity instanceof Animal &&ANIMAL_SCORES_MAP.containsKey(category)){
            score = ANIMAL_SCORES_MAP.get(category);
            if(entity instanceof MushroomCow){
                score=Math.max(score,MUSHROOM_SCORES_MAP.get(category));
            }
        }
        if((entity instanceof Enemy)&&monsterScores.containsKey(category)){
            score=monsterScores.get(category);
        }
        return score;
    }
    public static int getScoreBlock(LevelAccessor world, BlockPos pos, Block block) {
        Biome.BiomeCategory category=getCategory(world,pos);
        if(BIOME_SCORES_MAP.containsKey(block)&&BIOME_SCORES_MAP.get(block).containsKey(category)){
            return BIOME_SCORES_MAP.get(block).get(category);
        }
        return 1;
    }
    public static int getScoreBlock1(Level world, BlockPos pos, Block block) {
        Biome.BiomeCategory category=getCategory(world,pos);
        if (BIOME_SCORES_MAP.containsKey(block)&&BIOME_SCORES_MAP.get(block).containsKey(category)) {
            return BIOME_SCORES_MAP.get(block).get(category);
        }
        return 1;
    }
    private static Biome.BiomeCategory getCategory(LevelAccessor world, BlockPos pos){
        return world.getBiome(pos).getBiomeCategory();
    }
}
