package pyre.coloredredstone.proxy;

public interface IProxy {

    void preInit();
    void init();
    void postInit();

    String localize(String unlocalized, Object... args);
}
