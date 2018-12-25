package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class HealthySyncMessage extends ColoredPropertySyncConfigMessage {

    private int healthyChance;
    private int healthyDuration;

    public HealthySyncMessage(){}

    public HealthySyncMessage(boolean isEnabled, int chance, int duration) {
        propertyEnabled = isEnabled;
        healthyChance = chance;
        healthyDuration = duration;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        healthyChance = byteBuf.readInt();
        healthyDuration = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(healthyChance);
        byteBuf.writeInt(healthyDuration);
    }

    public static class HealthySyncMessageHandler implements IMessageHandler<HealthySyncMessage, IMessage> {

        @Override
        public IMessage onMessage(HealthySyncMessage healthySyncMessage, MessageContext messageContext) {
            CurrentModConfig.setWithering(healthySyncMessage.propertyEnabled,
                    healthySyncMessage.healthyChance,
                    healthySyncMessage.healthyDuration);
            return null;
        }
    }
}
