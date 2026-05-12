package net.steveson.horsemod.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.horse.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Llama.class)
public class LlamaMixin {

    /**
     * @author
     * @reason
     */
    @Overwrite
    public Llama getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        Llama llama = Llama.makeNewLlama();
        if (llama != null) {
            this.setOffspringAttributes(pOtherParent, llama);
            Llama llama1 = (Llama)pOtherParent;
            int i = this.random.nextInt(Math.max(this.getStrength(), llama1.getStrength())) + 1;
            if (this.random.nextFloat() < 0.03F) {
                ++i;
            }

            llama.setStrength(i);
            llama.setVariant(this.random.nextBoolean() ? this.getVariant() : llama1.getVariant());
        }

        return llama;
    }

}
