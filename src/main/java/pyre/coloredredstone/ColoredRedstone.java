package pyre.coloredredstone;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModEntities;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.proxy.CommonProxy;
import pyre.coloredredstone.util.Reference;
import pyre.coloredredstone.util.handlers.NetworkHandler;
import pyre.coloredredstone.util.handlers.OreDictionaryHandler;
import pyre.coloredredstone.util.handlers.RegistryHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class ColoredRedstone {

    public static Logger logger;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        RegistryHandler.registerTileEntities();
        NetworkHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        OreDictionaryHandler.registerOreDictionary();
    }
}
