package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class ExplosionproofSyncMessage extends ColoredPropertySyncConfigMessage {

    public ExplosionproofSyncMessage(){}

    public ExplosionproofSyncMessage(boolean isEnabled) {
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

    public static class ExplosionproofSyncMessageHandler implements IMessageHandler<ExplosionproofSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(ExplosionproofSyncMessage explosionproofSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setExplosionproof(explosionproofSyncMessage.propertyEnabled);
            return null;
        }
    }
}
