package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class AlienatedSyncMessage extends ColoredPropertySyncConfigMessage {

    private int alienatedChance;

    public AlienatedSyncMessage(){}

    public AlienatedSyncMessage(boolean isEnabled, int chance) {
        propertyEnabled = isEnabled;
        alienatedChance = chance;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        alienatedChance = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(alienatedChance);
    }

    public static class AlienatedSyncMessageHandler implements IMessageHandler<AlienatedSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(AlienatedSyncMessage alienatedSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setAlienated(alienatedSyncMessage.propertyEnabled,
                    alienatedSyncMessage.alienatedChance);
            return null;
        }
    }
}
