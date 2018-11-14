package pyre.coloredredstone.config;

import net.minecraftforge.common.config.Config;
import pyre.coloredredstone.util.Reference;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("coloredredstone.config.title")
public class ModConfig {

    @Config.LangKey("coloredredstone.config.waterproof")
    @Config.Comment("If true, blue colored redstone components will not be washed away by water.")
    public static boolean waterproof = true;

    @Config.LangKey("coloredredstone.config.explosionproof")
    @Config.Comment("If true, orange colored redstone components (both in item and block form) will not be destroyed by explosions.")
    public static boolean explosionproof = true;

    @Config.LangKey("coloredredstone.config.fireproof")
    @Config.Comment("If true, yellow colored redstone items will not be destroyed by fire.")
    public static boolean fireproof = true;

    @Config.LangKey("coloredredstone.config.despawnproof")
    @Config.Comment("If true, white colored redstone items will not despawn.")
    public static boolean despawnproof = true;

    @Config.LangKey("coloredredstone.config.cactusproof")
    @Config.Comment("If true, green colored redstone items will not be destroyed by cactus.")
    public static boolean cactusproof = true;
}
