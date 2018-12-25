package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class SluggishSyncMessage extends ColoredPropertySyncConfigMessage {

    private int sluggishChance;
    private int sluggishDuration;

    public SluggishSyncMessage(){}

    public SluggishSyncMessage(boolean isEnabled, int chance, int duration) {
        propertyEnabled = isEnabled;
        sluggishChance = chance;
        sluggishDuration = duration;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        sluggishChance = byteBuf.readInt();
        sluggishDuration = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(sluggishChance);
        byteBuf.writeInt(sluggishDuration);
    }

    public static class SluggishSyncMessageHandler implements IMessageHandler<SluggishSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(SluggishSyncMessage sluggishSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setWithering(sluggishSyncMessage.propertyEnabled,
                    sluggishSyncMessage.sluggishChance,
                    sluggishSyncMessage.sluggishDuration);
            return null;
        }
    }
}
