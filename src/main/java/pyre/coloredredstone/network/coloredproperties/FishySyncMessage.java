package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class FishySyncMessage extends ColoredPropertySyncConfigMessage {

    private int fishyChance;

    public FishySyncMessage(){}

    public FishySyncMessage(boolean isEnabled, int chance) {
        propertyEnabled = isEnabled;
        fishyChance = chance;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        fishyChance = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(fishyChance);
    }

    public static class FishySyncMessageHandler implements IMessageHandler<FishySyncMessage, IMessage> {

        @Override
        public IMessage onMessage(FishySyncMessage fishySyncMessage, MessageContext messageContext) {
            CurrentModConfig.setAlienated(fishySyncMessage.propertyEnabled,
                    fishySyncMessage.fishyChance);
            return null;
        }
    }
}
