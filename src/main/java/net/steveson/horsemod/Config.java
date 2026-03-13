package net.steveson.horsemod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ExtremeHorseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // 15 - 30
    private static final ForgeConfigSpec.DoubleValue H_MAX_HEALTH = BUILDER
            .comment("Highest possible Health value attainable when breeding Horses")
            .comment("Vanilla value: 30")
            .defineInRange("maxHealth", 30, 0, 1024.0);
    private static final ForgeConfigSpec.DoubleValue H_MIN_HEALTH = BUILDER
            .comment("Lowest possible Health value attainable when breeding Horses")
            .comment("Vanilla value: 15")
            .defineInRange("minHealth", 15, 0, 1024.0);

    // 0.4 - 1
    private static final ForgeConfigSpec.DoubleValue H_MAX_JUMP = BUILDER
            .comment("Highest possible Jump Strength attainable when breeding Horses")
            .comment("Vanilla value: 1.0")
            .defineInRange("maxJump", 1.0, 0, 255);
    private static final ForgeConfigSpec.DoubleValue H_MIN_JUMP = BUILDER
            .comment("Lowest possible Jump Strength attainable when breeding Horses")
            .comment("Vanilla value: 0.4")
            .defineInRange("minJump", 0.4, 0, 255);

    // 0.1125 - 0.3375
    private static final ForgeConfigSpec.DoubleValue H_MAX_SPEED = BUILDER
            .comment("Highest possible Speed value attainable when breeding Horses")
            .comment("Vanilla value: 0.3375")
            .defineInRange("maxSpeed", 0.3375, 0, 100);
    private static final ForgeConfigSpec.DoubleValue H_MIN_SPEED = BUILDER
            .comment("Lowest possible Speed value attainable when breeding Horses")
            .comment("Vanilla value: 0.1125")
            .defineInRange("minSpeed", 0.1125, 0, 100);

    private static final ForgeConfigSpec.BooleanValue H_EATS_CHICKENS = BUILDER
            .comment("Whether horses can have a little snack as a treat")
            .define("getHungry", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean eatChicken;
    public static double maxHealth;
    public static double minHealth;
    public static double maxJump;
    public static double minJump;
    public static double maxSpeed;
    public static double minSpeed;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        eatChicken = H_EATS_CHICKENS.get();
        maxHealth = H_MAX_HEALTH.get();
        minHealth = H_MIN_HEALTH.get();
        maxJump = H_MAX_JUMP.get();
        minJump = H_MIN_JUMP.get();
        maxSpeed = H_MAX_SPEED.get();
        minSpeed = H_MIN_SPEED.get();
    }
}
