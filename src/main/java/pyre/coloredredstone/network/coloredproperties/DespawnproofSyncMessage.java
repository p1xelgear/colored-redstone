package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class DespawnproofSyncMessage extends ColoredPropertySyncConfigMessage {

    public DespawnproofSyncMessage(){}

    public DespawnproofSyncMessage(boolean isEnabled) {
        propertyEnabled = isEnabled;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
    }

    public static class DespawnproofSyncMessageHandler implements IMessageHandler<DespawnproofSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(DespawnproofSyncMessage despawnproofSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setDespawnproof(despawnproofSyncMessage.propertyEnabled);
            return null;
        }
    }
}
