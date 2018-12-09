package pyre.coloredredstone.config;

import pyre.coloredredstone.integration.chisel.ModIntegrationChisel;

public class CurrentModConfig {

    public static boolean waterproof = ModConfig.coloredPropertiesConfig.waterproof;
    public static boolean explosionproof = ModConfig.coloredPropertiesConfig.explosionproof;
    public static boolean fireproof = ModConfig.coloredPropertiesConfig.fireproof;
    public static boolean despawnproof = ModConfig.coloredPropertiesConfig.despawnproof;
    public static boolean cactusproof = ModConfig.coloredPropertiesConfig.cactusproof;

    public static boolean integrationChiselColoredBlocks = ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks;

    public static void setColoredPropertiesValues(boolean water, boolean explosion, boolean fire, boolean despawn, boolean cactus) {
        waterproof = water;
        explosionproof = explosion;
        fireproof = fire;
        despawnproof = despawn;
        cactusproof = cactus;
    }

    public static void setIntegrationChiselColoredBlocks(boolean coloredBlocks) {
        integrationChiselColoredBlocks = coloredBlocks;
        if (coloredBlocks) {
            ModIntegrationChisel.addColoredBlocksVariations();
        } else {
            ModIntegrationChisel.removeColoredBlocksVariations();
        }
    }

    public static void useClientSettings() {
        setColoredPropertiesValues(ModConfig.coloredPropertiesConfig.waterproof,
                ModConfig.coloredPropertiesConfig.explosionproof,
                ModConfig.coloredPropertiesConfig.fireproof,
                ModConfig.coloredPropertiesConfig.despawnproof,
                ModConfig.coloredPropertiesConfig.cactusproof);
        setIntegrationChiselColoredBlocks(ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks);
    }
}
