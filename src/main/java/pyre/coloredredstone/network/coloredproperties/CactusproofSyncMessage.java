package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class CactusproofSyncMessage extends ColoredPropertySyncConfigMessage {

    public CactusproofSyncMessage(){}

    public CactusproofSyncMessage(boolean isEnabled) {
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

    public static class CactusproofSyncMessageHandler implements IMessageHandler<CactusproofSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(CactusproofSyncMessage cactusproofSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setCactusproof(cactusproofSyncMessage.propertyEnabled);
            return null;
        }
    }
}
