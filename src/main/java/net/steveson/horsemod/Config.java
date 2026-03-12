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

//    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
//            .comment("Whether to log the dirt block on common setup")
//            .define("logDirtBlock", true);

    // 15 - 30
    private static final ForgeConfigSpec.DoubleValue H_MAX_HEALTH = BUILDER
            .comment("Horse Max Health")
            .defineInRange("maxHealth", 40, 0, Double.MAX_VALUE);
    private static final ForgeConfigSpec.DoubleValue H_MIN_HEALTH = BUILDER
            .comment("Horse Min Health")
            .defineInRange("minHealth", 10, 0, Double.MAX_VALUE);

    // 0.4 - 1
    private static final ForgeConfigSpec.DoubleValue H_MAX_JUMP = BUILDER
            .comment("Horse Max Jump Strength")
            .defineInRange("maxJump", 1.5, 0, Double.MAX_VALUE);
    private static final ForgeConfigSpec.DoubleValue H_MIN_JUMP = BUILDER
            .comment("Horse Min Jump Strength")
            .defineInRange("minJump", 0.2, 0, Double.MAX_VALUE);

    // 0.1125 - 0.3375
    private static final ForgeConfigSpec.DoubleValue H_MAX_SPEED = BUILDER
            .comment("Horse Max Speed")
            .defineInRange("maxSpeed", 0.5, 0, Double.MAX_VALUE);
    private static final ForgeConfigSpec.DoubleValue H_MIN_SPEED = BUILDER
            .comment("Horse Min Speed")
            .defineInRange("minSpeed", 0.05, 0, Double.MAX_VALUE);

//    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

//    public static boolean logDirtBlock;
    public static double maxHealth;
    public static double minHealth;
    public static double maxJump;
    public static double minJump;
    public static double maxSpeed;
    public static double minSpeed;
//    public static String magicNumberIntroduction;
    public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
//        logDirtBlock = LOG_DIRT_BLOCK.get();
        maxHealth = H_MAX_HEALTH.get();
        minHealth = H_MIN_HEALTH.get();
        maxJump = H_MAX_JUMP.get();
        minJump = H_MIN_JUMP.get();
        maxSpeed = H_MAX_SPEED.get();
        minSpeed = H_MIN_SPEED.get();

//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

        // convert the list of strings into a set of items
        items = ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
    }
}
