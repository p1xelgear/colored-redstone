package pyre.coloredredstone.util.handlers;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import pyre.coloredredstone.network.ColoredPropertiesSyncConfigMessage;
import pyre.coloredredstone.network.IntegrationChiselSyncConfigMessage;
import pyre.coloredredstone.util.Reference;

public class NetworkHandler {

    public static SimpleNetworkWrapper INSTANCE;

    public static void init() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        registerMessages();
    }

    private static void registerMessages() {
        INSTANCE.registerMessage(ColoredPropertiesSyncConfigMessage.ColoredPropertiesSyncConfigMessageHandler.class, ColoredPropertiesSyncConfigMessage.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(IntegrationChiselSyncConfigMessage.IntegrationChiselSyncConfigMessageHandler.class, IntegrationChiselSyncConfigMessage.class, 1, Side.CLIENT);
    }
}
