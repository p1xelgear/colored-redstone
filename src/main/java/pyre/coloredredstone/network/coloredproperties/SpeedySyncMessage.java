package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class SpeedySyncMessage extends ColoredPropertySyncConfigMessage {

    private int speedyChance;
    private int speedyDuration;

    public SpeedySyncMessage(){}

    public SpeedySyncMessage(boolean isEnabled, int chance, int duration) {
        propertyEnabled = isEnabled;
        speedyChance = chance;
        speedyDuration = duration;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        speedyChance = byteBuf.readInt();
        speedyDuration = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(speedyChance);
        byteBuf.writeInt(speedyDuration);
    }

    public static class SpeedySyncMessageHandler implements IMessageHandler<SpeedySyncMessage, IMessage> {

        @Override
        public IMessage onMessage(SpeedySyncMessage speedySyncMessage, MessageContext messageContext) {
            CurrentModConfig.setWithering(speedySyncMessage.propertyEnabled,
                    speedySyncMessage.speedyChance,
                    speedySyncMessage.speedyDuration);
            return null;
        }
    }
}
