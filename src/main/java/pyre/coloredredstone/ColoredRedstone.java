package pyre.coloredredstone;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModEntities;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.integration.ModIntegration;
import pyre.coloredredstone.proxy.IProxy;
import pyre.coloredredstone.util.Reference;
import pyre.coloredredstone.util.handlers.NetworkHandler;
import pyre.coloredredstone.util.handlers.OreDictionaryHandler;
import pyre.coloredredstone.util.handlers.RegistryHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class ColoredRedstone {

    public static Logger logger;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        RegistryHandler.registerTileEntities();
        NetworkHandler.init();
        ModIntegration.doIntegrationsPreInit();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        OreDictionaryHandler.registerOreDictionary();
        ModIntegration.doIntegrationsInit();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModIntegration.doIntegrationsPostInit();
        proxy.postInit();
    }
}
