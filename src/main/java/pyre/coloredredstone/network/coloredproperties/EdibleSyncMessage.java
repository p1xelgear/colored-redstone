package pyre.coloredredstone.network.coloredproperties;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pyre.coloredredstone.config.CurrentModConfig;

public class EdibleSyncMessage extends ColoredPropertySyncConfigMessage {

    private int edibleHunger;
    private float edibleSaturation;
    private int edibleSaturationChance;
    private int edibleSaturationDuration;
    private int edibleNauseaChance;
    private int edibleNauseaDuration;

    public EdibleSyncMessage(){}

    public EdibleSyncMessage(boolean isEnabled, int hunger, float saturation, int saturationChance, int saturationDuration, int nauseaChance, int nauseaDuration) {
        propertyEnabled = isEnabled;
        edibleHunger = hunger;
        edibleSaturation = saturation;
        edibleSaturationChance = saturationChance;
        edibleSaturationDuration = saturationDuration;
        edibleNauseaChance = nauseaChance;
        edibleNauseaDuration = nauseaDuration;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        propertyEnabled = byteBuf.readBoolean();
        edibleHunger = byteBuf.readInt();
        edibleSaturation = byteBuf.readFloat();
        edibleSaturationChance = byteBuf.readInt();
        edibleSaturationDuration = byteBuf.readInt();
        edibleNauseaChance = byteBuf.readInt();
        edibleNauseaDuration = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(propertyEnabled);
        byteBuf.writeInt(edibleHunger);
        byteBuf.writeFloat(edibleSaturation);
        byteBuf.writeInt(edibleSaturationChance);
        byteBuf.writeInt(edibleSaturationDuration);
        byteBuf.writeInt(edibleNauseaChance);
        byteBuf.writeInt(edibleNauseaDuration);
    }

    public static class EdibleSyncMessageHandler implements IMessageHandler<EdibleSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(EdibleSyncMessage edibleSyncMessage, MessageContext messageContext) {
            CurrentModConfig.setEdible(edibleSyncMessage.propertyEnabled,
                    edibleSyncMessage.edibleHunger,
                    edibleSyncMessage.edibleSaturation,
                    edibleSyncMessage.edibleSaturationChance,
                    edibleSyncMessage.edibleSaturationDuration,
                    edibleSyncMessage.edibleNauseaChance,
                    edibleSyncMessage.edibleNauseaDuration);
            return null;
        }
    }
}
