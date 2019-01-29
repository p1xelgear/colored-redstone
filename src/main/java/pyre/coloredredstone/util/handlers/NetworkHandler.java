package pyre.coloredredstone.util.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import pyre.coloredredstone.network.InWorldRecoloringSyncConfigMessage;
import pyre.coloredredstone.network.IntegrationChiselSyncConfigMessage;
import pyre.coloredredstone.network.coloredproperties.*;
import pyre.coloredredstone.util.Reference;

public class NetworkHandler {

    private static int id = 0;

    public static SimpleNetworkWrapper INSTANCE;

    public static void init() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        registerMessages();
    }

    public static void sendTo(IMessage message, EntityPlayerMP player) {
        INSTANCE.sendTo(message, player);
    }

    private static void registerMessages() {
        INSTANCE.registerMessage(InWorldRecoloringSyncConfigMessage.InWorldRecoloringSyncConfigMessageHandler.class, InWorldRecoloringSyncConfigMessage.class, id, Side.CLIENT);
        INSTANCE.registerMessage(WaterproofSyncMessage.WaterproofSyncMessageHandler.class, WaterproofSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(ExplosionproofSyncMessage.ExplosionproofSyncMessageHandler.class, ExplosionproofSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(FireproofSyncMessage.FireproofSyncMessageHandler.class, FireproofSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(DespawnproofSyncMessage.DespawnproofSyncMessageHandler.class, DespawnproofSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(CactusproofSyncMessage.CactusproofSyncMessageHandler.class, CactusproofSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(BurnableSyncMessage.BurnableSyncMessageHandler.class, BurnableSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(SlimySyncMessage.SlimySyncMessageHandler.class, SlimySyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(WitheringSyncMessage.WitheringSyncMessageHandler.class, WitheringSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(SluggishSyncMessage.SluggishSyncMessageHandler.class, SluggishSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(SpeedySyncMessage.SpeedySyncMessageHandler.class, SpeedySyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(HealthySyncMessage.HealthySyncMessageHandler.class, HealthySyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(AlienatedSyncMessage.AlienatedSyncMessageHandler.class, AlienatedSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(FishySyncMessage.FishySyncMessageHandler.class, FishySyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(SoftSyncMessage.SoftSyncMessageHandler.class, SoftSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(EdibleSyncMessage.EdibleSyncMessageHandler.class, EdibleSyncMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(IntegrationChiselSyncConfigMessage.IntegrationChiselSyncConfigMessageHandler.class, IntegrationChiselSyncConfigMessage.class, id++, Side.CLIENT);
    }
}
