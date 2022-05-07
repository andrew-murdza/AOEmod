package com.example.examplemod.features;

import com.example.examplemod.mixins.TreeDecoratedAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CaveVinesFeature extends TreeDecorator {
    public static Codec<CaveVinesFeature> CODEC;
    public static TreeDecoratorType<CaveVinesFeature> type;
    public static float chance=1.0F;
    public static float maxPercent=100F;//0.01F;
//    static{
//        //Function<Random,Integer> height=(random)->random.nextInt(3)+1;
//        CaveVinesFeature caveVines=new CaveVinesFeature();
//        CODEC=Codec.unit(() -> caveVines);
//        type=(TreeDecoratorType<CaveVinesFeature>) TreeDecoratedAccessor.createType(CODEC);
//    }
    //private float chance;
    private int minAge;
    private int maxAge;
    private float berriesChance;
    private Function<Random, Integer> height;
    //this structure doesn't do anything
    public CaveVinesFeature(float chance){//, int minAge, int maxAge, float berriesChance, Function<Random,Integer> height){
        this.chance=chance;
        this.minAge=25;//minAge;
        this.maxAge=25;//maxAge;
        this.berriesChance=1;//berriesChance;
        this.height=height;
        //CODEC=Codec.unit(() -> this);
    }

    public CaveVinesFeature(){
        this(chance);
    }


    public boolean generateVineColumn(LevelSimulatedReader world, Random random, BlockPos.MutableBlockPos pos, BiConsumer<BlockPos, BlockState> replacer) {
        int length=0;//height.apply(random);
        BlockPos.MutableBlockPos pos1=pos.mutable();
        for(int i=0;i<4;i++){
            Direction dir=Direction.from2DDataValue(i);
            if(!Feature.isAir(world,pos1.move(dir))){
                return false;
            }
            for(int j=0;j<4;j++) {
                Direction dir1 = Direction.from2DDataValue(j);
                if(!Feature.isAir(world,pos1.move(dir).move(dir1))){
                    return false;
                }
            }
        }

        while(Feature.isAir(world,pos1)){
            length++;
            pos1.move(Direction.DOWN);
        }
        length=length-4;
        length=Math.min(length,6);
        //length=length<=0?0:random.nextInt(length);
        boolean flag=false;
        for (int i = 0; i <= length; ++i) {
            if (Feature.isAir(world,pos)) {
                if (i == length || !Feature.isAir(world,pos.below())) {
                    flag=true;
                    int age= Mth.nextInt(random, minAge, maxAge);
                    BlockState state= Blocks.CAVE_VINES.defaultBlockState().setValue(BlockStateProperties.AGE_25, age);
                    state=state.setValue(BlockStateProperties.BERRIES,random.nextFloat()<berriesChance?true:false);
                    replacer.accept(pos,state);
                    break;
                }
                replacer.accept(pos,Blocks.CAVE_VINES_PLANT.defaultBlockState().setValue(BlockStateProperties.BERRIES,random.nextFloat()<berriesChance?true:false));
            }
            pos.move(Direction.DOWN);
        }
        return flag;
    }

    protected TreeDecoratorType<?> type() {
        return type;
    }

    @Override
    public void place(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> blockSetter, Random random, List<BlockPos> pLogPositions, List<BlockPos> leafPositions) {
        AtomicInteger sum= new AtomicInteger();
        int maxVines=(int)(maxPercent*leafPositions.size());
        List<BlockPos> positions=new ArrayList<>();
        leafPositions.forEach(pos -> {
            if(random.nextFloat()<chance&& sum.get() <maxVines){
                sum.set(sum.get() + 1);
                generateVines(pos,world,random,blockSetter,positions);
            }
        });
    }

    private void generateVines(BlockPos pos, LevelSimulatedReader world, Random random, BiConsumer<BlockPos, BlockState> replacer, List<BlockPos> positions) {
        BlockPos.MutableBlockPos mutable = pos.mutable().move(Direction.DOWN);
        if (!Feature.isAir(world,mutable)) {
            return;
        }
        for(BlockPos pos1: positions){
            int x=pos1.getX();
            int z=pos1.getZ();
            if(Math.abs(x-pos.getX())<4||Math.abs(z-pos.getZ())<4){
                return;
            }
        }
        if(generateVineColumn(world, random, mutable, replacer)){
            positions.add(mutable);
        }
    }

}
