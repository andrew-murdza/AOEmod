package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(BoneMealItem.class)
public class BoneMealMixin {
    @Redirect(method = "growWaterPlant",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Objects;equals(Ljava/lang/Object;Ljava/lang/Object;)Z"))
    private static boolean useOnGround(Object a, Object b, ItemStack stack, Level world, BlockPos blockPos, @Nullable Direction facing){
        return BiomeScores.isTropical(world,blockPos);
    }
    @Redirect(method = "growWaterPlant",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I",ordinal = 5))
    private static int useOnGround1(Random random, int n, ItemStack stack, Level world, BlockPos blockPos, @Nullable Direction facing){
        int[] chances=new int[]{10,10,8,6,4};
        int score= BiomeScores.getScoreBlock(world,blockPos, Blocks.SEAGRASS);
        return random.nextInt(chances[score]);
    }
    @Redirect(method = "growWaterPlant",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private static boolean useOnGround2(Level world, BlockPos pos, BlockState state, int flags){
        Biome.BiomeCategory category=world.getBiome(pos).getBiomeCategory();
        if(BiomeScores.isMountains(world,pos)&&world.random.nextInt(20)==0){
            state=Blocks.WET_SPONGE.defaultBlockState();
        }
        return world.setBlockAndUpdate(pos,state);
    }
}
