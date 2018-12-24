package pyre.coloredredstone.proxy;

import net.minecraft.client.resources.I18n;

public class CombinedClientProxy implements IProxy {

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
        return I18n.format(unlocalized, args);
    }
}
