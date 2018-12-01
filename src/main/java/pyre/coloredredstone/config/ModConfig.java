package pyre.coloredredstone.config;

import net.minecraftforge.common.config.Config;
import pyre.coloredredstone.util.Reference;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("coloredredstone.config.title")
public class ModConfig {

    @Config.LangKey("coloredredstone.config.waterproof")
    @Config.Comment("If true, placed, blue colored redstone components will not be washed away by water.")
    public static boolean waterproof = true;

    @Config.LangKey("coloredredstone.config.explosionproof")
    @Config.Comment("If true, orange colored items and blocks in item form will not be destroyed by explosions, placed redstone components will not pop up.")
    public static boolean explosionproof = true;

    @Config.LangKey("coloredredstone.config.fireproof")
    @Config.Comment("If true, yellow items and blocks in item form will not be destroyed by fire or lava.")
    public static boolean fireproof = true;

    @Config.LangKey("coloredredstone.config.despawnproof")
    @Config.Comment("If true, white items and blocks in item form will not despawn when on the ground.")
    public static boolean despawnproof = true;

    @Config.LangKey("coloredredstone.config.cactusproof")
    @Config.Comment("If true, green items and blocks in item form will not be destroyed by Cactus.")
    public static boolean cactusproof = true;
}
