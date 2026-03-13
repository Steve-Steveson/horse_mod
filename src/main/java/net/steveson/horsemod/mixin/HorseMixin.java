package net.steveson.horsemod.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.steveson.horsemod.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Predicate;

@Mixin(Horse.class)
public class HorseMixin extends AbstractHorse{

    protected HorseMixin(EntityType<? extends AbstractHorse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        if(Config.eatChicken) {
            this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Chicken.class, true, PREY_SELECTOR));
        }
    }

    @Unique
    public boolean doHurtTarget(Entity pEntity) {
        this.playSound(SoundEvents.HORSE_EAT, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        if (this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }
        return pEntity.hurt(this.damageSources().mobAttack(this), 4.0F);
    }

    @Unique
    //used to be public
    private static final Predicate<LivingEntity> PREY_SELECTOR = (potentialVictim) -> {
        return potentialVictim.isBaby();
    };
}
