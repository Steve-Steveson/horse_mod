package net.steveson.horsemod.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class NoRiderRandomTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        private final Animal rideableMob;

        public NoRiderRandomTargetGoal(Animal pAnimal, Class<T> pTargetType, boolean pMustSee, boolean pMustReach, @Nullable Predicate<LivingEntity> pTargetPredicate) {
            super(pAnimal, pTargetType, 10, pMustSee, pMustReach, pTargetPredicate);
            this.rideableMob = pAnimal;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.rideableMob.isVehicle() && super.canUse();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return this.targetConditions != null ? this.targetConditions.test(this.mob, this.target) : super.canContinueToUse();
        }
}
