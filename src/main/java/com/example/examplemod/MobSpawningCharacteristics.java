package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobSpawningCharacteristics {
    @SubscribeEvent
    public static void CharacteristicChangesEventHandler(EntityJoinWorldEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Entity entity=event.getEntity();
        if(!event.getWorld().isClientSide&&entity instanceof Mob){
            Mob mob=(Mob) entity;
            if(mob.getTags().contains("aoe.checkedCharacteristics")){//||mob.getTags().contains("aoe.bred")){
                return;
            }
            mob.addTag("aoe.checkedCharacteristics");
//            if(true){
//                return;
//            }
            if(mob instanceof MushroomCow){
                Method setMushroomType=MushroomCow.class.getDeclaredMethod("setMushroomType", MushroomCow.MushroomType.class);
                setMushroomType.setAccessible(true);
                setMushroomType.invoke(mob,Helper.select(MushroomCow.MushroomType.RED,MushroomCow.MushroomType.BROWN));
            }
            else if(mob instanceof Axolotl){
                Method setVariant=Axolotl.class.getDeclaredMethod("setVariant",Axolotl.Variant.class);
                setVariant.setAccessible(true);
                setVariant.invoke(mob,Helper.select(Axolotl.Variant.values()));
            }
            else if(mob instanceof Rabbit){
                setRabbit(event.getWorld(),mob,BiomeScores::isTropical,()->Helper.nextInt(5));
                setRabbit(event.getWorld(),mob,BiomeScores::isMountains,()->Helper.nextInt(100) < 80 ? 1 : 3);
            }
            else if(mob instanceof Fox){
                setFox(event.getWorld(),mob,BiomeScores::isTropical,()->Helper.select(Fox.Type.RED,Fox.Type.SNOW));
                setFox(event.getWorld(),mob,BiomeScores::isMountains,()->Fox.Type.SNOW);
            }
            else if(mob instanceof Llama){
                Method setStrength=Llama.class.getDeclaredMethod("setStrength",int.class);
                setStrength.setAccessible(true);
                setStrength.invoke(mob,5);
                int score=BiomeScores.getAnimalScore(event.getWorld(),Helper.pos(mob));
                int[] healths=new int[]{12,18,23,30,40};
                mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(healths[score]);
            }
            else if(mob instanceof Horse){
                float[] speeds=new float[]{0.1F,0.15F,0.225F,0.30F,0.4F};
                int[] healths=new int[]{12,18,23,30,40};
                float[] jumpStrengths=new float[]{0.3F,0.45F,0.7F,0.9F,1.1F};
                setHorseStats(speeds,healths,jumpStrengths,mob,event.getWorld());
            }
            else if(mob instanceof Donkey){
                float[] speeds=new float[]{0.08F,0.12F,0.175F,0.23F,0.31F};
                int[] healths=new int[]{12,18,23,30,40};
                float[] jumpStrengths=new float[]{0.3F,0.4F,0.5F,0.7F,1.0F};
                setHorseStats(speeds,healths,jumpStrengths,mob,event.getWorld());
            }
        }
    }
    private static void setRabbit(Level world, Mob mob, BiFunction<LevelAccessor, BlockPos,Boolean> fn, Supplier<Integer> id){
        if(fn.apply(world,Helper.pos(mob))){
            ((Rabbit)mob).setRabbitType(id.get());
        }
    }
    private static void setHorseStats(float[] speeds, int[] healths, float[] jumpStrengths, Mob mob, LevelAccessor world){
        int score=BiomeScores.getAnimalScore(world,Helper.pos(mob));
        mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(healths[score]);
        mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speeds[score]);
        mob.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(jumpStrengths[score]);
    }
    private static void setFox(Level world, Mob mob, BiFunction<LevelAccessor, BlockPos,Boolean> fn, Supplier<Fox.Type> type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(fn.apply(world,Helper.pos(mob))){
            Method setFoxType=Fox.class.getDeclaredMethod("setFoxType", Fox.Type.class);
            setFoxType.setAccessible(true);
            setFoxType.invoke(mob,type.get());
        }
    }

}
