package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class BurnableSyncMessage extends ColoredPropertySyncConfigMessage {

    private int burnableBurningTime;

    public BurnableSyncMessage(){}

    public BurnableSyncMessage(boolean isEnabled, int burningTime) {
        propertyEnabled = isEnabled;
        burnableBurningTime = burningTime;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        burnableBurningTime = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(burnableBurningTime);
    }

    public static class BurnableSyncMessageHandler implements IMessageHandler<BurnableSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(BurnableSyncMessage burnableSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setBurnable(burnableSyncMessage.propertyEnabled,
                    burnableSyncMessage.burnableBurningTime);
            return null;
        }
    }
}
