package pyre.coloredredstone.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class ColoredPropertiesSyncConfigMessage implements IMessage {

    private boolean waterproof;
    private boolean explosionproof;
    private boolean fireproof;
    private boolean despawnproof;
    private boolean cactusproof;
    private boolean burnable;
    private int burningTime;
    private boolean slimy;
    private int slimyChance;

    public ColoredPropertiesSyncConfigMessage() {
    }

    public ColoredPropertiesSyncConfigMessage(boolean waterproof, boolean explosionproof, boolean fireproof, boolean despawnproof,
                                              boolean cactusproof, boolean burnable, int burningTime, boolean slimy, int slimyChance) {
        this.waterproof = waterproof;
        this.explosionproof = explosionproof;
        this.fireproof = fireproof;
        this.despawnproof = despawnproof;
        this.cactusproof = cactusproof;
        this.burnable = burnable;
        this.burningTime = burningTime;
        this.slimy = slimy;
        this.slimyChance = slimyChance;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        waterproof = buf.readBoolean();
        explosionproof = buf.readBoolean();
        fireproof = buf.readBoolean();
        despawnproof = buf.readBoolean();
        cactusproof = buf.readBoolean();
        burnable = buf.readBoolean();
        burningTime = buf.readInt();
        slimy = buf.readBoolean();
        slimyChance = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(waterproof);
        buf.writeBoolean(explosionproof);
        buf.writeBoolean(fireproof);
        buf.writeBoolean(despawnproof);
        buf.writeBoolean(cactusproof);
        buf.writeBoolean(burnable);
        buf.writeInt(burningTime);
        buf.writeBoolean(slimy);
        buf.writeInt(slimyChance);
    }

    public static class ColoredPropertiesSyncConfigMessageHandler implements IMessageHandler<ColoredPropertiesSyncConfigMessage, IMessage> {

        @Override
        public IMessage onMessage(ColoredPropertiesSyncConfigMessage message, MessageContext ctx) {
            CurrentModConfig.setColoredPropertiesValues(message.waterproof,
                    message.explosionproof,
                    message.fireproof,
                    message.despawnproof,
                    message.cactusproof,
                    message.burnable,
                    message.burningTime,
                    message.slimy,
                    message.slimyChance);
            return null;
        }
    }
}
