package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class FireproofSyncMessage extends ColoredPropertySyncConfigMessage {

    public FireproofSyncMessage(){}

    public FireproofSyncMessage(boolean isEnabled) {
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

    public static class FireproofSyncMessageHandler implements IMessageHandler<FireproofSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(FireproofSyncMessage fireproofSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setFireproof(fireproofSyncMessage.propertyEnabled);
            return null;
        }
    }
}
