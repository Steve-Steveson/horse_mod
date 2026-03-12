package net.steveson.horsemod.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
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
public abstract class AbstractHorseAttackMixin extends Animal{

    protected AbstractHorseAttackMixin(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    /**
     * @author
     * @reason violence
     */
    @Overwrite
    public static AttributeSupplier.Builder createBaseHorseAttributes() {
        return Mob.createMobAttributes().add(Attributes.JUMP_STRENGTH).add(Attributes.MAX_HEALTH, 53.0D).add(Attributes.MOVEMENT_SPEED, (double)0.225F).add(Attributes.ATTACK_DAMAGE, 4);
    }



//    @Inject(method = "registerGoals", at = @At(value = "TAIL"))
//    protected void registerGoals(CallbackInfo ci) {
//
////        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Chicken.class, true));
//        System.out.println("I AM ALSO KNOWN AS: " + this);
////        this.addBehaviourGoals();
//    }

    @Inject(method = "addBehaviourGoals", at = @At(value = "TAIL"))
    protected void addBehaviourGoals(CallbackInfo ci) {

//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Chicken.class, true));
        System.out.println("I AM ALSO KNOWN AS: " + this);
    }







    @Unique
    //used to be public
    private static final Predicate<LivingEntity> PREY_SELECTOR = (p_289448_) -> {
        EntityType<?> entitytype = p_289448_.getType();
        return entitytype == EntityType.CHICKEN
//                && p_289448_.isBaby()
                ;
    };

    @Unique
    public boolean doHurtTarget(Entity pEntity) {
        this.playSound(SoundEvents.HORSE_EAT, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
//        return pEntity.hurt(this.damageSources().mobAttack(this), 4.0F);
        return super.doHurtTarget(pEntity);
    }






}
