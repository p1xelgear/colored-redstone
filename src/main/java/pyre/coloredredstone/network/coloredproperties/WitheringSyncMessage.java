package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class WitheringSyncMessage extends ColoredPropertySyncConfigMessage {

    private int witheringChance;
    private int witheringDuration;

    public WitheringSyncMessage(){}

    public WitheringSyncMessage(boolean isEnabled, int chance, int duration) {
        propertyEnabled = isEnabled;
        witheringChance = chance;
        witheringDuration = duration;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        witheringChance = byteBuf.readInt();
        witheringDuration = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(witheringChance);
        byteBuf.writeInt(witheringDuration);
    }

    public static class WitheringSyncMessageHandler implements IMessageHandler<WitheringSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(WitheringSyncMessage witheringSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setWithering(witheringSyncMessage.propertyEnabled,
                    witheringSyncMessage.witheringChance,
                    witheringSyncMessage.witheringDuration);
            return null;
        }
    }
}
