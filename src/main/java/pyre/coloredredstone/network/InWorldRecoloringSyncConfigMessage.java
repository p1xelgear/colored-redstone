package pyre.coloredredstone.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class InWorldRecoloringSyncConfigMessage implements IMessage {

    private boolean inWorldRecoloring;

    public InWorldRecoloringSyncConfigMessage() {
    }

    public InWorldRecoloringSyncConfigMessage(boolean inWorldRecoloring) {
        this.inWorldRecoloring = inWorldRecoloring;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        inWorldRecoloring = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(inWorldRecoloring);
    }

    public static class InWorldRecoloringSyncConfigMessageHandler implements IMessageHandler<InWorldRecoloringSyncConfigMessage, IMessage> {

        @Override
        public IMessage onMessage(InWorldRecoloringSyncConfigMessage message, MessageContext ctx) {
            CurrentModConfig.setInWorldRecoloring(message.inWorldRecoloring);
            return null;
        }
    }
}
