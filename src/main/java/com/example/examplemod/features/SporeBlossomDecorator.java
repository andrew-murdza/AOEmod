package com.example.examplemod.features;

import com.example.examplemod.mixins.TreeDecoratedAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class SporeBlossomDecorator extends TreeDecorator {
    public static Codec<SporeBlossomDecorator> CODEC;
    public static TreeDecoratorType<SporeBlossomDecorator> type;
//    static{
//        SporeBlossomDecorator sporeBlossom=new SporeBlossomDecorator(0.01F);
//        CODEC=Codec.unit(() -> sporeBlossom);
//        type= (TreeDecoratorType<SporeBlossomDecorator>) TreeDecoratedAccessor.createType(CODEC);
//    }
    protected TreeDecoratorType<?> type() {
        return type;
    }

    @Override
    public void place(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> pLogPositions, List<BlockPos> leafPositions) {
        leafPositions.forEach(pos -> {
            if(random.nextFloat()<chance){
                generateSporeBlossom(pos,world,random,replacer);
            }
        });
    }

    private float chance;
    public SporeBlossomDecorator() {
        this.chance=0.01f;
    }

    private void generateSporeBlossom(BlockPos pos, LevelSimulatedReader world, Random random, BiConsumer<BlockPos, BlockState> replacer) {
        BlockPos pos1= pos.below();
        for(int i=0;i<4;i++){
            Direction dir=Direction.from2DDataValue(i);
            if(!Feature.isAir(world,pos1.relative(dir))){
                return;
            }
        }
        replacer.accept(pos1, Blocks.SPORE_BLOSSOM.defaultBlockState());
    }

}
