package net.steveson.horsemod.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.steveson.horsemod.Config;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class NoRiderRandomTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final Animal rideableMob;

    public NoRiderRandomTargetGoal(Animal mob, Class<T> targetType, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetType, 10, mustSee, mustReach, targetPredicate);
        this.rideableMob = mob;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        return (!this.rideableMob.isVehicle() || Config.FAST_FOOD.get()) && super.canUse();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.targetConditions != null ? this.targetConditions.test(this.mob, this.target) : super.canContinueToUse();
    }

}
