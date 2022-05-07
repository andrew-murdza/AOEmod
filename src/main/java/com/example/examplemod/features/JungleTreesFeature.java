package com.example.examplemod.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;
import net.minecraft.world.level.levelgen.feature.trunkplacers.*;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.antlr.runtime.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class JungleTreesFeature {
    private static BeehiveDecorator BEES_005;
    public static TreeDecorator CAVE_VINES;
    public static TreeDecorator SPORE_BLOSSOM;
    private static TrunkVineDecorator trunkVines;
    private static LeaveVineDecorator leafVines;

    public static ConfiguredFeature ACACIA_TREE;
    public static ConfiguredFeature AZALEA_TREE;
    public static ConfiguredFeature BIRCH_TREE;
    public static ConfiguredFeature DARK_OAK_TREE;
    public static ConfiguredFeature GIANT_JUNGLE_TREE;
    public static ConfiguredFeature JUNGLE_TREE;
    public static ConfiguredFeature OAK_TREE;
    public static ConfiguredFeature LARGE_OAK_TREE;
    private static List<WeightedPlacedFeature> entries = new ArrayList<>();
    public static ConfiguredFeature JUNGLE_TREES;

    public static PlacedFeature JUNGLE_TREE_PLACED;
    public static PlacedFeature DARK_OAK_TREE_PLACED;
    public static PlacedFeature LARGE_OAK_TREE_PLACED;
    public static PlacedFeature OAK_TREE_PLACED;
    public static PlacedFeature ACACIA_TREE_PLACED;
    public static PlacedFeature BIRCH_TREE_PLACED;
    public static PlacedFeature GIANT_JUNGLE_TREE_PLACED;
    public static PlacedFeature JUNGLE_TREES_PLACED;
    public static void setup(FMLCommonSetupEvent event){
        BEES_005 = new BeehiveDecorator(0.05f);
        CAVE_VINES=new CaveVinesFeature();//new BeehiveDecorator(0.00002f);
        SPORE_BLOSSOM=new BeehiveDecorator(0.00002f);//new SporeBlossomDecorator(0.01F);
        trunkVines = TrunkVineDecorator.INSTANCE;
        leafVines = LeaveVineDecorator.INSTANCE;

        ACACIA_TREE= createAcaciaTree();
        BIRCH_TREE=createBirchTree();
        DARK_OAK_TREE=createDarkOakTree();
        JUNGLE_TREE= createJungleTree();
        OAK_TREE=createOakTree();
        LARGE_OAK_TREE=createFancyOak();
        GIANT_JUNGLE_TREE=createGiantJungleTree();

        JUNGLE_TREE_PLACED=createTreePlaced(JUNGLE_TREE);
        DARK_OAK_TREE_PLACED=createTreePlaced(DARK_OAK_TREE);
        LARGE_OAK_TREE_PLACED=createTreePlaced(LARGE_OAK_TREE);
        OAK_TREE_PLACED=createTreePlaced(OAK_TREE);
        ACACIA_TREE_PLACED=createTreePlaced(ACACIA_TREE);
        BIRCH_TREE_PLACED=createTreePlaced(BIRCH_TREE);
        GIANT_JUNGLE_TREE_PLACED=createTreePlaced(GIANT_JUNGLE_TREE);

        System.out.println("this ran!!!!!!");
        ConfiguredFeature[] features=new ConfiguredFeature[]{JUNGLE_TREE,DARK_OAK_TREE,LARGE_OAK_TREE,ACACIA_TREE,
                BIRCH_TREE,GIANT_JUNGLE_TREE};
        PlacedFeature[] featuresPlaced=new PlacedFeature[]{JUNGLE_TREE_PLACED,DARK_OAK_TREE_PLACED,
                LARGE_OAK_TREE_PLACED,ACACIA_TREE_PLACED, BIRCH_TREE_PLACED,GIANT_JUNGLE_TREE_PLACED};

        add(DARK_OAK_TREE_PLACED,0.1F);
        add(LARGE_OAK_TREE_PLACED,0.30F);
        add(LARGE_OAK_TREE_PLACED,0.05F);
        add(ACACIA_TREE_PLACED,0.03F);
        add(BIRCH_TREE_PLACED,0.02F);
        add(GIANT_JUNGLE_TREE_PLACED,0.3F);
        add(TreePlacements.MEGA_SPRUCE_CHECKED,0.05F);
        add(TreePlacements.MEGA_PINE_CHECKED,0.05F);
        add(TreePlacements.SPRUCE_CHECKED,0.01F);
        List<PlacementModifier> modifier0= VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1f, 1));
        String[] featureNames=new String[]{"jungle_tree","dark_oak","large_oak","acacia","birch","giant_jungle_tree"};
//        for(int i=0;i<featureNames.length;i++){
//            registerConfigured(featureNames[i],features[i]);
//        }
        System.out.println("this ran preregister");
        for(int i=0;i<featureNames.length;i++){
            registerPlaced(featureNames[i],featuresPlaced[i]);
        }
        JUNGLE_TREES= JUNGLE_TREE;//AOEConfiguredFeatures.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(entries, JUNGLE_TREE_PLACED));
        JUNGLE_TREES_PLACED=JUNGLE_TREES.placed(modifier0);
        registerPlaced("tropical_trees",JUNGLE_TREES_PLACED);
        System.out.println("this ran postregister");
        //JUNGLE_TREE_PLACED= JUNGLE_TREE.placed(VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
    }

    private static void add(PlacedFeature feature, float chance){
        entries.add(new WeightedPlacedFeature(feature,chance));
    }

    private static void registerConfigured(String name, ConfiguredFeature feature){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation("aoemod", name), feature);
    }

    private static void registerPlaced(String name, PlacedFeature feature){
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation("aoemod", name), feature);
    }
    private static PlacedFeature createTreePlaced(ConfiguredFeature tree){
        return tree.filteredByBlockSurvival(Blocks.OAK_SAPLING);//.placed(VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));//.filteredByBlockSurvival(Blocks.OAK_SAPLING);
    }

    private static ConfiguredFeature createDarkOakTree(){
        TrunkPlacer trunk = new DarkOakTrunkPlacer(6, 2, 1);
        FoliagePlacer foliage = new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0));
        FeatureSize layers = new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty());
        TreeConfiguration.TreeConfigurationBuilder tree=builder(Blocks.DARK_OAK_LOG,Blocks.DARK_OAK_LEAVES,trunk,foliage,layers);
        ImmutableList<TreeDecorator> decorators = ImmutableList.of(SPORE_BLOSSOM,CAVE_VINES);
        return createTree(tree,decorators);
    }

    private static ConfiguredFeature createAcaciaTree(){
        TrunkPlacer trunk = new ForkingTrunkPlacer(5, 2, 2);
        FoliagePlacer foliage = new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0));
        FeatureSize layers = new TwoLayersFeatureSize(1, 0, 2);
        TreeConfiguration.TreeConfigurationBuilder tree=builder(Blocks.ACACIA_LOG,Blocks.ACACIA_LEAVES,trunk,foliage,layers);
        ImmutableList<TreeDecorator> decorators = ImmutableList.of(SPORE_BLOSSOM,CAVE_VINES);
        return createTree(tree,decorators);
    }

    private static ConfiguredFeature createGiantJungleTree(){
        TrunkPlacer trunk = new MegaJungleTrunkPlacer(10, 2, 19);
        FoliagePlacer foliage = new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2);
        FeatureSize layers = new TwoLayersFeatureSize(1, 1, 2);
        TreeConfiguration.TreeConfigurationBuilder tree=builder(Blocks.JUNGLE_LOG,Blocks.JUNGLE_LEAVES,trunk,foliage,layers);
        ImmutableList<TreeDecorator> decorators = ImmutableList.of(SPORE_BLOSSOM,CAVE_VINES,trunkVines,leafVines);
        return createTree(tree,decorators);
    }

    private static ConfiguredFeature createFancyOak(){
        TrunkPlacer trunk = new FancyTrunkPlacer(3, 11, 0);
        FoliagePlacer foliage = new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4);
        FeatureSize layers = new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4));
        TreeConfiguration.TreeConfigurationBuilder tree=builder(Blocks.OAK_LOG,Blocks.OAK_LEAVES,trunk,foliage,layers);
        ImmutableList<TreeDecorator> decorators = ImmutableList.of(SPORE_BLOSSOM,CAVE_VINES,BEES_005, trunkVines, leafVines);
        return createTree(tree,decorators);
    }

    private static ConfiguredFeature createJungleTree(){
        TreeConfiguration.TreeConfigurationBuilder tree = builder(Blocks.JUNGLE_LOG, Blocks.JUNGLE_LEAVES, 4, 8, 0, 2);
        CocoaDecorator cocoa = new CocoaDecorator(0.2f);
        ImmutableList<TreeDecorator> decorators = ImmutableList.of(cocoa,SPORE_BLOSSOM,CAVE_VINES, trunkVines, leafVines);
        return createTree(tree,decorators);
    }

    private static ConfiguredFeature createOakTree(){
        TreeConfiguration.TreeConfigurationBuilder tree = builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 2, 0, 2);
        ImmutableList<TreeDecorator> decorators = ImmutableList.of(SPORE_BLOSSOM,CAVE_VINES,BEES_005, trunkVines, leafVines);
        return createTree(tree,decorators);
    }

    private static ConfiguredFeature createBirchTree(){
        TreeConfiguration.TreeConfigurationBuilder tree = builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 0, 2);
        ImmutableList<TreeDecorator> decorators = ImmutableList.of(SPORE_BLOSSOM,CAVE_VINES,CAVE_VINES,BEES_005);
        return createTree(tree,decorators);
    }

    private static TreeConfiguration.TreeConfigurationBuilder builder(Block log, Block leaves, TrunkPlacer trunk, FoliagePlacer foliage, FeatureSize layers){
        BlockStateProvider logProv = BlockStateProvider.simple(log);
        BlockStateProvider leafProv = BlockStateProvider.simple(leaves);
        return new TreeConfiguration.TreeConfigurationBuilder(logProv, trunk, leafProv, foliage, layers).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder builder(Block log, Block leaves, int baseHeight, int height1, int height2, int radius) {
        BlobFoliagePlacer foliage = new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3);
        TwoLayersFeatureSize layers = new TwoLayersFeatureSize(1, 0, 1);
        StraightTrunkPlacer trunk = new StraightTrunkPlacer(baseHeight, height1, height2);
        return builder(log,leaves,trunk,foliage,layers);
    }

    private static ConfiguredFeature createTree(TreeConfiguration.TreeConfigurationBuilder tree, ImmutableList<TreeDecorator> decorators){
        return Feature.TREE.configured(tree.decorators(decorators).build());
    }
    private static ConfiguredFeature<TreeConfiguration, ?> createTree(){
        ImmutableList<TreeDecorator> decorators = ImmutableList.of(SPORE_BLOSSOM,CAVE_VINES,CAVE_VINES,BEES_005);
        BlockStateProvider log = BlockStateProvider.simple(Blocks.OAK_LOG);
        UniformInt Int = UniformInt.of(1, 2);
        BendingTrunkPlacer placer;
        placer=new BendingTrunkPlacer(4, 2, 0, 3, Int);

        SimpleWeightedRandomList.Builder<BlockState> builder1 = SimpleWeightedRandomList.<BlockState>builder();
        builder1=builder1.add(Blocks.AZALEA_LEAVES.defaultBlockState(), 3);
        builder1=builder1.add(Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState(), 1);
        WeightedStateProvider provider = new WeightedStateProvider(builder1);
        ConstantInt zero = ConstantInt.of(0);
        ConstantInt two = ConstantInt.of(2);
        ConstantInt three = ConstantInt.of(3);
        RandomSpreadFoliagePlacer foiliage=new RandomSpreadFoliagePlacer(three, zero, two, 50);
        TwoLayersFeatureSize layers = new TwoLayersFeatureSize(1, 0, 1);
        TreeConfiguration.TreeConfigurationBuilder builder = new TreeConfiguration.TreeConfigurationBuilder(log,placer, provider, foiliage, layers);
        builder=builder.dirt(BlockStateProvider.simple(Blocks.ROOTED_DIRT)).forceDirt();
        TreeConfiguration config = builder.decorators(decorators).build();
        return Feature.TREE.configured(config);
    }
    private static ConfiguredFeature<RootSystemConfiguration, ?> createAzaleaTree(){
        PlacedFeature tree = createTree().placed(new PlacementModifier[0]);
        ResourceLocation replaceable = BlockTags.AZALEA_ROOT_REPLACEABLE.getName();
        BlockStateProvider provider = BlockStateProvider.simple(Blocks.ROOTED_DIRT);
        BlockPredicate predicate1 = BlockPredicate.matchesBlocks(List.of(Blocks.AIR, Blocks.CAVE_AIR, Blocks.VOID_AIR, Blocks.WATER));
        BlockPredicate leaves = BlockPredicate.matchesTag(BlockTags.LEAVES);
        BlockPredicate plantReplaceable = BlockPredicate.matchesTag(BlockTags.REPLACEABLE_PLANTS);
        BlockPredicate predicate2 = BlockPredicate.anyOf(predicate1, leaves, plantReplaceable);
        BlockPredicate predicate3 = BlockPredicate.matchesTag(BlockTags.AZALEA_GROWS_ON, Direction.DOWN.getNormal());
        BlockPredicate predicate = BlockPredicate.allOf(predicate2,predicate3);
        ConfiguredFeature<RootSystemConfiguration, ?> feature = Feature.ROOT_SYSTEM.configured(new RootSystemConfiguration(() -> tree, 3, 3, replaceable, provider, 20, 100, 3, 2, provider, 20, 2, predicate));
        return feature;
    }
    private static PlacedFeature createAzaleaTreePlaced(){
        ConfiguredFeature feature=createAzaleaTree();
        return feature.placed(PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)));
    }
}

