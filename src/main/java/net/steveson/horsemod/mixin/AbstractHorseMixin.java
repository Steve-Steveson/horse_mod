package net.steveson.horsemod.mixin;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.Level;
import net.steveson.horsemod.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal {

    protected AbstractHorseMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }


    /**
     * @author Minecraft
     * @reason Complete re-write to use config min and max, keep matching stats, and not lose max stats every time.
     */
    @Overwrite
    static double createOffspringAttribute(double pValue1, double pValue2, double pMin, double pMax, RandomSource pRandom) {
        double cfgMin;
        double cfgMax;
        if (pMax == 1) {
            cfgMax = Config.H_MAX_JUMP.get();
            cfgMin = Config.H_MIN_JUMP.get();
        } else if (pMax == 30) {
            cfgMax = Config.H_MAX_HEALTH.get();
            cfgMin = Config.H_MIN_HEALTH.get();
        } else {
            cfgMax = Config.H_MAX_SPEED.get();
            cfgMin = Config.H_MIN_SPEED.get();
        }
//        System.out.println(pMax + " became " + cfgMax);
        if (pMax <= pMin || cfgMax < cfgMin) {
            throw new IllegalArgumentException("Incorrect range for an attribute");
        } else {
            double parentValue1 = Mth.clamp(pValue1, cfgMin, cfgMax);
            double parentValue2 = Mth.clamp(pValue2, cfgMin, cfgMax);
            if (Config.KEEP_MATCHING_STATS.get() && Math.abs(parentValue1 - parentValue2) <= 0.0025 * (pMax-pMin)){
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
            }
        }
    }



    @Inject(method = "setOffspringAttributes", at = @At(value = "TAIL"))
    protected void setOffspringAttributes(AgeableMob parent, AbstractHorse child, CallbackInfo ci) {
        double jumpValue = Mth.clamp(child.getAttributeValue(Attributes.JUMP_STRENGTH), 1, 2);
        double jumpHeight = jumpValue * jumpValue * 3.6 - 3.6;
        child.getAttribute(Attributes.SAFE_FALL_DISTANCE).setBaseValue(jumpHeight + 6);
    }
}
