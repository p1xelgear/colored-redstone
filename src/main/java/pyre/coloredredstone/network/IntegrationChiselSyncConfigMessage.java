package pyre.coloredredstone.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class IntegrationChiselSyncConfigMessage implements IMessage {

    private boolean coloredBlocks;

    public IntegrationChiselSyncConfigMessage() {
    }

    public IntegrationChiselSyncConfigMessage(boolean coloredBlocks) {
        this.coloredBlocks = coloredBlocks;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        coloredBlocks = byteBuf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(coloredBlocks);
    }

    public static class IntegrationChiselSyncConfigMessageHandler implements IMessageHandler<IntegrationChiselSyncConfigMessage, IMessage> {

        @Override
        public IMessage onMessage(IntegrationChiselSyncConfigMessage message, MessageContext messageContext) {
            CurrentModConfig.setIntegrationChiselColoredBlocks(message.coloredBlocks);
            return null;
        }
    }
}
