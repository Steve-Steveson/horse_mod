package net.steveson.horsemod;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // 15 - 30
    private static final ModConfigSpec.DoubleValue H_MAX_HEALTH = BUILDER
            .comment("Highest possible Health value attainable when breeding Horses")
            .comment("Vanilla value: 30")
            .defineInRange("maxHealth", 30, 0, 1024.0);
    private static final ModConfigSpec.DoubleValue H_MIN_HEALTH = BUILDER
            .comment("Lowest possible Health value attainable when breeding Horses")
            .comment("Vanilla value: 15")
            .defineInRange("minHealth", 15, 0, 1024.0);

    // 0.4 - 1
    private static final ModConfigSpec.DoubleValue H_MAX_JUMP = BUILDER
            .comment("")
            .comment("Highest possible Jump Strength attainable when breeding Horses")
            .comment("Values higher that 2 wouldn't work anyway")
            .comment("Vanilla value: 1.0")
            .defineInRange("maxJump", 1.0, 0, 2);
    private static final ModConfigSpec.DoubleValue H_MIN_JUMP = BUILDER
            .comment("Lowest possible Jump Strength attainable when breeding Horses")
            .comment("Vanilla value: 0.4")
            .defineInRange("minJump", 0.4, 0, 2);

    // 0.1125 - 0.3375
    private static final ModConfigSpec.DoubleValue H_MAX_SPEED = BUILDER
            .comment("")
            .comment("Highest possible Speed value attainable when breeding Horses")
            .comment("Multiply by 42.16 to approximate blocks/second")
            .comment("Vanilla value: 0.3375")
            .defineInRange("maxSpeed", 0.3375, 0, 25);
    public static final ModConfigSpec.DoubleValue H_MIN_SPEED = BUILDER
            .comment("Lowest possible Speed value attainable when breeding Horses")
            .comment("Vanilla value: 0.1125")
            .defineInRange("minSpeed", 0.1125, 0, 25);

    public static final ModConfigSpec.BooleanValue H_EATS_CHICKENS = BUILDER
            .comment("")
            .comment("Whether horses can have a little snack as a treat")
            .define("getHungry", true);

    private static final ModConfigSpec.BooleanValue FAST_FOOD = BUILDER
            .comment("Whether horses can snack while being ridden")
            .define("fastFood", false);

    private static final ModConfigSpec.BooleanValue KEEP_MATCHING_STATS = BUILDER
            .comment("Whether 2 horses with a nearly identical stat value  should pass on that value unchanged")
            .define("keepMatchingStats", true);

    public static final ModConfigSpec.DoubleValue BETTER_LLAMA_CHANCE = BUILDER
            .comment("Chance that a baby llama will have a higher strength that its strongest parent")
            .comment("Chance doubles if both parents have the same strength")
            .defineInRange("strongerLlamaChance", 0.04, 0, 0.5);

    static final ModConfigSpec SPEC = BUILDER.build();
}
