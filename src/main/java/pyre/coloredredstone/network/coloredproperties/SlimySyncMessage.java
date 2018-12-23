package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class SlimySyncMessage extends ColoredPropertySyncConfigMessage {

    private int slimyChance;

    public SlimySyncMessage(){}

    public SlimySyncMessage(boolean isEnabled, int chance) {
        propertyEnabled = isEnabled;
        slimyChance = chance;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        slimyChance = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(slimyChance);
    }

    public static class SlimySyncMessageHandler implements IMessageHandler<SlimySyncMessage, IMessage> {

        @Override
        public IMessage onMessage(SlimySyncMessage slimySyncMessage, MessageContext messageContext) {
            CurrentModConfig.setSlimy(slimySyncMessage.propertyEnabled,
                    slimySyncMessage.slimyChance);
            return null;
        }
    }
}
