package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class WaterproofSyncMessage extends ColoredPropertySyncConfigMessage {

    public WaterproofSyncMessage(){}

    public WaterproofSyncMessage(boolean isEnabled) {
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

    public static class WaterproofSyncMessageHandler implements IMessageHandler<WaterproofSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(WaterproofSyncMessage waterproofSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setWaterproof(waterproofSyncMessage.propertyEnabled);
            return null;
        }
    }
}
