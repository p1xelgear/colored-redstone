package pyre.coloredredstone.config;

import net.minecraftforge.common.config.Config;
import pyre.coloredredstone.util.Reference;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("coloredredstone.config.title")
public class ModConfig {

    @Config.LangKey("coloredredstone.config.colored_properties")
    @Config.Comment("Colored properties configuration.")
    public static ColoredProperties coloredPropertiesConfig = new ColoredProperties();

    @Config.LangKey("coloredredstone.config.integration")
    @Config.Comment("IntegrationConfig with another mods.")
    public static IntegrationConfig integrationConfig = new IntegrationConfig();

    public static class ColoredProperties {

        @Config.LangKey("coloredredstone.config.colored_properties.waterproof")
        @Config.Comment("If true, placed, blue colored redstone components will not be washed away by water.")
        public boolean waterproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.explosionproof")
        @Config.Comment("If true, orange colored items and blocks in item form will not be destroyed by explosions, placed redstone components will not pop up.")
        public boolean explosionproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.fireproof")
        @Config.Comment("If true, yellow items and blocks in item form will not be destroyed by fire or lava.")
        public boolean fireproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.despawnproof")
        @Config.Comment("If true, white items and blocks in item form will not despawn when on the ground.")
        public boolean despawnproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.cactusproof")
        @Config.Comment("If true, green items and blocks in item form will not be destroyed by Cactus.")
        public boolean cactusproof = true;
    }
}
