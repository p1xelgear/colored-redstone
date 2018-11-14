package pyre.coloredredstone.config;

public class CurrentModConfig {

    public static boolean waterproof = ModConfig.waterproof;
    public static boolean explosionproof = ModConfig.explosionproof;
    public static boolean fireproof = ModConfig.fireproof;
    public static boolean despawnproof = ModConfig.despawnproof;
    public static boolean cactusproof = ModConfig.cactusproof;

    public static void setValues(boolean water, boolean explosion, boolean fire, boolean despawn, boolean cactus){
        waterproof = water;
        explosionproof = explosion;
        fireproof = fire;
        despawnproof = despawn;
        cactusproof = cactus;
    }

    public static void useClientSettings(){
        setValues(ModConfig.waterproof, ModConfig.explosionproof, ModConfig.fireproof, ModConfig.despawnproof, ModConfig.cactusproof);
    }
}
