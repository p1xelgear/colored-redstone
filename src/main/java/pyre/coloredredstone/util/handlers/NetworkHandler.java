package pyre.coloredredstone.util.handlers;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import pyre.coloredredstone.network.SyncConfigMessage;
import pyre.coloredredstone.util.Reference;

public class NetworkHandler {

    public static SimpleNetworkWrapper INSTANCE;

    public static void init(){
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        registerMessages();
    }

    private static void registerMessages(){
        INSTANCE.registerMessage(SyncConfigMessage.SyncConfigMessageHandler.class, SyncConfigMessage.class, 0, Side.CLIENT);
    }
}
