package net.steveson.horsemod.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.steveson.horsemod.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin {

    /**
     * @author
     * @reason
     */
    @Overwrite
    static double createOffspringAttribute(double pValue1, double pValue2, double pMin, double pMax, RandomSource pRandom) {
        double cfgMin;
        double cfgMax;
        if (pMax == 1) {
            cfgMax = Config.maxJump;
            cfgMin = Config.minJump;
        } else if (pMax == 30) {
            cfgMax = Config.maxHealth;
            cfgMin = Config.minHealth;
        } else {
            cfgMax = Config.maxSpeed;
            cfgMin = Config.minSpeed;
        }
//        System.out.println(pMax + " became " + cfgMax);
        if (pMax <= pMin || cfgMax <= cfgMin) {
            throw new IllegalArgumentException("Incorrect range for an attribute");
        } else {
            if (pValue1 == pValue2){
                return pValue1;
            } else {

                //test code only
                return cfgMax;
            }
        }
    }

//    @Inject(method = "registerGoals", at = @At(value = "HEAD"))
//    protected void registerGoals(CallbackInfo ci) {
//
//        this.goalSelector.addGoal(3, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
//
//
//    }
//
//
//    @Unique
//    //used to be public
//    private static final Predicate<LivingEntity> PREY_SELECTOR = (p_289448_) -> {
//        EntityType<?> entitytype = p_289448_.getType();
//        return entitytype == EntityType.CHICKEN
////                && p_289448_.isBaby()
//                ;
//    };


}
