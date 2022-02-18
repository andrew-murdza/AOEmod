package com.example.examplemod.mixins;

import com.example.examplemod.BiomeScores;
import com.example.examplemod.Helper;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(EatBlockGoal.class)
public class EatBlockGoalMixin {
    @Shadow
    Mob mob;
    @Redirect(method = "canUse",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I"))
    public int nextInt(Random random, int n) {
        int[] chances=new int[]{0,3000,1000,600,300};
        int[] babyChances=new int[]{0,150,50,30,15};
        int score= BiomeScores.getScoreEntity(mob);
        return Helper.nextInt(mob.isBaby()?babyChances[score]:chances[score]);
    }
}
