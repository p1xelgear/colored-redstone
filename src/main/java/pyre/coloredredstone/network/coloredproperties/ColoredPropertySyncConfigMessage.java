package pyre.coloredredstone.network.coloredproperties;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class ColoredPropertySyncConfigMessage implements IMessage {

    protected boolean propertyEnabled;
}
