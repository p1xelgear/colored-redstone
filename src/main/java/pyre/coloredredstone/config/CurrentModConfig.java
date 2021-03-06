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
    public static boolean sluggish = ModConfig.coloredPropertiesConfig.sluggish;
    public static boolean speedy = ModConfig.coloredPropertiesConfig.speedy;
    public static boolean healthy = ModConfig.coloredPropertiesConfig.healthy;
    public static boolean alienated = ModConfig.coloredPropertiesConfig.alienated;
    public static boolean fishy = ModConfig.coloredPropertiesConfig.fishy;
    public static boolean soft = ModConfig.coloredPropertiesConfig.soft;
    public static boolean edible = ModConfig.coloredPropertiesConfig.edible;

    public static int burnableBurningTime = ModConfig.coloredPropertiesConfig.burnableBurningTime;
    public static boolean burnableCatchFire = ModConfig.coloredPropertiesConfig.burnableCatchFire;
    public static int slimyChance = ModConfig.coloredPropertiesConfig.slimyChance;
    public static int witheringChance = ModConfig.coloredPropertiesConfig.witheringChance;
    public static int witheringDuration = ModConfig.coloredPropertiesConfig.witheringDuration;
    public static int sluggishChance = ModConfig.coloredPropertiesConfig.sluggishChance;
    public static int sluggishDuration = ModConfig.coloredPropertiesConfig.sluggishDuration;
    public static int speedyChance = ModConfig.coloredPropertiesConfig.speedyChance;
    public static int speedyDuration = ModConfig.coloredPropertiesConfig.speedyDuration;
    public static int healthyChance = ModConfig.coloredPropertiesConfig.healthyChance;
    public static int healthyDuration = ModConfig.coloredPropertiesConfig.healthyDuration;
    public static int alienatedChance = ModConfig.coloredPropertiesConfig.alienatedChance;
    public static int fishyChance = ModConfig.coloredPropertiesConfig.fishyChance;
    public static int softDamageReduction = ModConfig.coloredPropertiesConfig.softDamageReduction;
    public static int edibleHunger = ModConfig.coloredPropertiesConfig.edibleHunger;
    public static float edibleSaturation = ModConfig.coloredPropertiesConfig.edibleSaturation;
    public static int edibleSaturationChance = ModConfig.coloredPropertiesConfig.edibleSaturationChance;
    public static int edibleSaturationDuration = ModConfig.coloredPropertiesConfig.edibleSaturationDuration;
    public static int edibleNauseaChance = ModConfig.coloredPropertiesConfig.edibleNauseaChance;
    public static int edibleNauseaDuration = ModConfig.coloredPropertiesConfig.edibleNauseaDuration;

    public static boolean integrationChiselColoredBlocks = ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks;

    public static void setInWorldRecoloring(boolean recoloring) {
        inWorldRecoloring = recoloring;
    }

    public static void setWaterproof(boolean isWaterproof) {
        waterproof = isWaterproof;
    }

    public static void setExplosionproof(boolean isExplosionproof) {
        explosionproof = isExplosionproof;
    }

    public static void setFireproof(boolean isFireproof) {
        fireproof = isFireproof;
    }

    public static void setDespawnproof(boolean isDespawnproof) {
        despawnproof = isDespawnproof;
    }

    public static void setCactusproof(boolean isCactusproof) {
        cactusproof = isCactusproof;
    }

    public static void setBurnable(boolean isBurnable, int burningTime, boolean catchFire) {
        burnable = isBurnable;
        burnableBurningTime = burningTime;
        burnableCatchFire = catchFire;
    }

    public static void setSlimy(boolean isSlimy, int chance) {
        slimy = isSlimy;
        slimyChance = chance;
    }

    public static void setWithering(boolean isWithering, int chance, int duration) {
        withering = isWithering;
        witheringChance = chance;
        witheringDuration = duration;
    }

    public static void setSluggish(boolean isSluggish, int chance, int duration) {
        sluggish = isSluggish;
        sluggishChance = chance;
        sluggishDuration = duration;
    }

    public static void setSpeedy(boolean isSpeedy, int chance, int duration) {
        speedy = isSpeedy;
        speedyChance = chance;
        speedyDuration = duration;
    }

    public static void setHealthy(boolean isHealthy, int chance, int duration) {
        healthy = isHealthy;
        healthyChance = chance;
        healthyDuration = duration;
    }

    public static void setAlienated(boolean isAlienated, int chance) {
        alienated = isAlienated;
        alienatedChance = chance;
    }

    public static void setFishy(boolean isFishy, int chance) {
        fishy = isFishy;
        fishyChance = chance;
    }

    public static void setSoft(boolean isSoft, int reduction) {
        soft = isSoft;
        softDamageReduction = reduction;
    }

    public static void setEdible(boolean isEdible, int hunger, float saturation, int saturationChance, int saturationDuration, int nauseaChance, int nauseaDuration) {
        edible = isEdible;
        edibleHunger = hunger;
        edibleSaturation = saturation;
        edibleSaturationChance = saturationChance;
        edibleSaturationDuration = saturationDuration;
        edibleNauseaChance = nauseaChance;
        edibleNauseaDuration = nauseaDuration;
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
        setWaterproof(ModConfig.coloredPropertiesConfig.waterproof);
        setExplosionproof(ModConfig.coloredPropertiesConfig.explosionproof);
        setFireproof(ModConfig.coloredPropertiesConfig.fireproof);
        setDespawnproof(ModConfig.coloredPropertiesConfig.despawnproof);
        setCactusproof(ModConfig.coloredPropertiesConfig.cactusproof);
        setBurnable(ModConfig.coloredPropertiesConfig.burnable,
                ModConfig.coloredPropertiesConfig.burnableBurningTime,
                ModConfig.coloredPropertiesConfig.burnableCatchFire);
        setSlimy(ModConfig.coloredPropertiesConfig.slimy,
                ModConfig.coloredPropertiesConfig.slimyChance);
        setWithering(ModConfig.coloredPropertiesConfig.withering,
                ModConfig.coloredPropertiesConfig.witheringChance,
                ModConfig.coloredPropertiesConfig.witheringDuration);
        setSluggish(ModConfig.coloredPropertiesConfig.sluggish,
                ModConfig.coloredPropertiesConfig.sluggishChance,
                ModConfig.coloredPropertiesConfig.sluggishDuration);
        setSpeedy(ModConfig.coloredPropertiesConfig.speedy,
                ModConfig.coloredPropertiesConfig.speedyChance,
                ModConfig.coloredPropertiesConfig.speedyDuration);
        setHealthy(ModConfig.coloredPropertiesConfig.healthy,
                ModConfig.coloredPropertiesConfig.healthyChance,
                ModConfig.coloredPropertiesConfig.healthyDuration);
        setAlienated(ModConfig.coloredPropertiesConfig.alienated,
                ModConfig.coloredPropertiesConfig.alienatedChance);
        setFishy(ModConfig.coloredPropertiesConfig.fishy,
                ModConfig.coloredPropertiesConfig.fishyChance);
        setSoft(ModConfig.coloredPropertiesConfig.soft,
                ModConfig.coloredPropertiesConfig.softDamageReduction);
        setEdible(ModConfig.coloredPropertiesConfig.edible,
                ModConfig.coloredPropertiesConfig.edibleHunger,
                ModConfig.coloredPropertiesConfig.edibleSaturation,
                ModConfig.coloredPropertiesConfig.edibleSaturationChance,
                ModConfig.coloredPropertiesConfig.edibleSaturationDuration,
                ModConfig.coloredPropertiesConfig.edibleNauseaChance,
                ModConfig.coloredPropertiesConfig.edibleNauseaDuration);
        setIntegrationChiselColoredBlocks(ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks);
    }
}
