package net.steveson.horsemod.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.Level;
import net.steveson.horsemod.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Mixin(Llama.class)
public class LlamaMixin extends AbstractChestedHorse {
    public LlamaMixin(EntityType<? extends Llama> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyVariable(method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/horse/Llama;",
            at = @At(value = "STORE", ordinal = 0))
    public int modifyI(int i, ServerLevel pLevel, AgeableMob pOtherParent) {
        int mommy = this.getStrength();
        Llama llama1 = (Llama)pOtherParent;
        int daddy = llama1.getStrength();
        int max = Math.max(mommy, daddy);
        int min = Math.min(mommy, daddy);

        int j = (int)Math.ceil(
                max * (1 - (Math.pow(
                        this.random.nextDouble(), (1 + (
                                (double) (min - 1) / Math.max((max - 1), 1)
                        ))
                )))
        );

        double cfgChance = Config.buildBetterLlamas;
        double getBetter = (mommy == daddy ? 2 * cfgChance : cfgChance);

        if (this.random.nextFloat() < getBetter) {
            j = max + 1;
        }
        return j;
    }

    @Shadow
    private static final EntityDataAccessor<Integer> DATA_STRENGTH_ID = SynchedEntityData.defineId(Llama.class, EntityDataSerializers.INT);

    @Shadow
    public int getStrength() {
        return this.entityData.get(DATA_STRENGTH_ID);
    }
}
