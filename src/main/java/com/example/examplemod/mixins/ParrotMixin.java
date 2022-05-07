package com.example.examplemod.mixins;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Parrot.class)
public abstract class ParrotMixin extends Animal {
    protected ParrotMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }
    /**
     * @author
     */
    @Overwrite
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob p_148994_) {
        Parrot parrot=EntityType.PARROT.create(world);
        parrot.setVariant(random.nextInt(5));
        return  parrot;
    }

    /**
     * @author
     */
    @Overwrite
    public boolean canMate(Animal other){
        return other!=this&&other.getClass()==Parrot.class&&isInLove()&&other.isInLove();
    }
}
