package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class SoftSyncMessage extends ColoredPropertySyncConfigMessage {

    private int softReduction;

    public SoftSyncMessage(){}

    public SoftSyncMessage(boolean isEnabled, int chance) {
        propertyEnabled = isEnabled;
        softReduction = chance;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        softReduction = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(softReduction);
    }

    public static class SoftSyncMessageHandler implements IMessageHandler<SoftSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(SoftSyncMessage softSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setSoft(softSyncMessage.propertyEnabled,
                    softSyncMessage.softReduction);
            return null;
        }
    }
}
