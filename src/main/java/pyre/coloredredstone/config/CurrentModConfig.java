package pyre.coloredredstone.config;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.integration.chisel.ModIntegrationChisel;

public class CurrentModConfig {

    public static boolean inWorldRecoloring = ModConfig.inWorldRecoloring;

    public static boolean waterproof = ModConfig.coloredPropertiesConfig.waterproof;
    public static boolean explosionproof = ModConfig.coloredPropertiesConfig.explosionproof;
    public static boolean fireproof = ModConfig.coloredPropertiesConfig.fireproof;
    public static boolean despawnproof = ModConfig.coloredPropertiesConfig.despawnproof;
    public static boolean cactusproof = ModConfig.coloredPropertiesConfig.cactusproof;
    public static boolean burnable = ModConfig.coloredPropertiesConfig.burnable;
    public static boolean slimy = ModConfig.coloredPropertiesConfig.slimy;
    public static boolean withering = ModConfig.coloredPropertiesConfig.withering;

    public static int burnableBurningTime = ModConfig.coloredPropertiesConfig.burnableBurningTime;
    public static int slimyChance = ModConfig.coloredPropertiesConfig.slimyChance;
    public static int witheringChance = ModConfig.coloredPropertiesConfig.witheringChance;
    public static int witheringDuration = ModConfig.coloredPropertiesConfig.witheringDuration;

    public static boolean integrationChiselColoredBlocks = ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks;

    public static void setInWorldRecoloring(boolean recoloring) {
        inWorldRecoloring = recoloring;
    }

    public static void setColoredPropertiesValues(boolean water, boolean explosion, boolean fire, boolean despawn,
                                                  boolean cactus, boolean burn, int burningTime, boolean slime, int slimeChance,
                                                  boolean wither, int witherChance, int witherDuration) {
        waterproof = water;
        explosionproof = explosion;
        fireproof = fire;
        despawnproof = despawn;
        cactusproof = cactus;
        burnable = burn;
        burnableBurningTime = burningTime;
        slimy = slime;
        slimyChance = slimeChance;
        withering = wither;
        witheringChance = witherChance;
        witheringDuration = witherDuration;
    }

    public static void setIntegrationChiselColoredBlocks(boolean coloredBlocks) {
        integrationChiselColoredBlocks = coloredBlocks;
        if (coloredBlocks) {
            ModIntegrationChisel.addColoredBlocksVariations();
        } else {
            ModIntegrationChisel.removeColoredBlocksVariations();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void useClientSettings() {
        setInWorldRecoloring(ModConfig.inWorldRecoloring);
        setColoredPropertiesValues(ModConfig.coloredPropertiesConfig.waterproof,
                ModConfig.coloredPropertiesConfig.explosionproof,
                ModConfig.coloredPropertiesConfig.fireproof,
                ModConfig.coloredPropertiesConfig.despawnproof,
                ModConfig.coloredPropertiesConfig.cactusproof,
                ModConfig.coloredPropertiesConfig.burnable,
                ModConfig.coloredPropertiesConfig.burnableBurningTime,
                ModConfig.coloredPropertiesConfig.slimy,
                ModConfig.coloredPropertiesConfig.slimyChance,
                ModConfig.coloredPropertiesConfig.withering,
                ModConfig.coloredPropertiesConfig.witheringChance,
                ModConfig.coloredPropertiesConfig.witheringDuration);
        setIntegrationChiselColoredBlocks(ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks);
    }
}
