package pyre.coloredredstone.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class SyncConfigMessage implements IMessage {

    private boolean waterproof;
    private boolean explosionproof;
    private boolean fireproof;
    private boolean despawnproof;
    private boolean cactusproof;

    public SyncConfigMessage(){}

    public SyncConfigMessage(boolean waterproof, boolean explosionproof, boolean fireproof, boolean despawnproof, boolean cactusproof) {
        this.waterproof = waterproof;
        this.explosionproof = explosionproof;
        this.fireproof = fireproof;
        this.despawnproof = despawnproof;
        this.cactusproof = cactusproof;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        waterproof = buf.readBoolean();
        explosionproof = buf.readBoolean();
        fireproof = buf.readBoolean();
        despawnproof = buf.readBoolean();
        cactusproof = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(waterproof);
        buf.writeBoolean(explosionproof);
        buf.writeBoolean(fireproof);
        buf.writeBoolean(despawnproof);
        buf.writeBoolean(cactusproof);
    }

    public static class SyncConfigMessageHandler implements IMessageHandler<SyncConfigMessage, IMessage>{

        @Override
        public IMessage onMessage(SyncConfigMessage message, MessageContext ctx) {
            CurrentModConfig.setValues(message.waterproof, message.explosionproof, message.fireproof, message.despawnproof, message.cactusproof);
            return null;
        }
    }
}
