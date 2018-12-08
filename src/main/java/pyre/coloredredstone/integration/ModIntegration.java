package pyre.coloredredstone.integration;

import net.minecraftforge.fml.common.Loader;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.integration.chisel.ModIntegrationChisel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ModIntegration {

    public static final Map<String, Class<? extends ModIntegration>> modIntegrationsClasses = new HashMap<>();
    public static final Set<ModIntegration> integrations = new HashSet<>();

    static {
        modIntegrationsClasses.put(Mods.CHISEL.modId, ModIntegrationChisel.class);
    }

    public static void doIntegrationsPreInit(){
        for (Map.Entry<String, Class<? extends ModIntegration>> entry : modIntegrationsClasses.entrySet()) {
            if (Loader.isModLoaded(entry.getKey())){
                try {
                    ModIntegration integration = entry.getValue().newInstance();
                    integrations.add(integration);
                    integration.preInit();
                } catch (Exception e) {
                    ColoredRedstone.logger.error("Mod integration for " + entry.getKey() +
                            "could not be preInitialized. Report this and include the error message below!", e);
                }
            }
        }
    }

    public static void doIntegrationsInit(){
        for (ModIntegration integration : integrations) {
            try {
                integration.init();
            } catch (Exception e) {
                ColoredRedstone.logger.error("Mod integration for " + integration +
                        "could not be initialized. Report this and include the error message below!", e);
            }
        }
    }

    public static void doIntegrationsPostInit(){
        for (ModIntegration integration : integrations) {
            try {
                integration.postInit();
            } catch (Exception e) {
                ColoredRedstone.logger.error("Mod integration for " + integration +
                        "could not be postInitialized. Report this and include the error message below!", e);
            }
        }
    }

    public abstract void preInit();
    public abstract void init();
    public abstract void postInit();

    public enum Mods {

        CHISEL("chisel");

        public final String modId;

        Mods(String modId){
            this.modId = modId;
        }
    }
}
