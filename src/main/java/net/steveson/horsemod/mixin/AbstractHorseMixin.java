package net.steveson.horsemod.mixin;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.steveson.horsemod.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal{

    protected AbstractHorseMixin(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * @author Minecraft
     * @reason Complete re-write of to use config min and max, keep matching stats, and not lose max stats every time.
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
            double parentValue1 = Mth.clamp(pValue1, cfgMin, cfgMax);
            double parentValue2 = Mth.clamp(pValue2, cfgMin, cfgMax);
            if (Math.abs(parentValue1 - parentValue2) <= 0.0025 * (pMax-pMin)){
                double avr = (cfgMax + cfgMin) / 2;
                if (Math.abs(avr - parentValue1) >= Math.abs(avr - parentValue2)) {
                    return parentValue1;
                } else {
                    return parentValue2;
                }
            } else {
                double d0partOfRange = 0.3 * (pMax - pMin);
                double d1maxSpread = Math.abs(parentValue1 - parentValue2) + d0partOfRange;
                double d2parentalAverage = (parentValue1 + parentValue2) / 2;
                double d3randomness = (pRandom.nextDouble() + pRandom.nextDouble() + pRandom.nextDouble()) / 3.0D - 0.5D;
                double d4unclampedResult = d2parentalAverage + d1maxSpread * d3randomness;

                return Mth.clamp(d4unclampedResult, cfgMin, cfgMax);

                //test code only
//                return cfgMax;
            }
        }
    }

    /**
     * @author Minecraft
     * @reason fall damage reduction for ultra-high jumpers
     */
    @Overwrite
    // this uses "extends Animal"
    protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
        double jumpMod;
        if (getAttributeValue(Attributes.JUMP_STRENGTH) < 1){
            jumpMod = 0;
        } else {
            jumpMod = getAttributeValue(Attributes.JUMP_STRENGTH) * getAttributeValue(Attributes.JUMP_STRENGTH) - 1;
        }
        return Mth.ceil((pDistance * 0.5F - 3.0F - jumpMod * 3) * pDamageMultiplier);
    }
}
