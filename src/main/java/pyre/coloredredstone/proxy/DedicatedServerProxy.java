package pyre.coloredredstone.proxy;

import net.minecraft.util.text.TextComponentTranslation;

public class DedicatedServerProxy implements IProxy {

    @Override
    public void preInit() {
        //nothing to do
    }

    @Override
    public void init() {
        //nothing to do
    }

    @Override
    public void postInit() {
        //nothing to do
    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return new TextComponentTranslation(unlocalized, args).getUnformattedComponentText();
    }
}
