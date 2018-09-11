package pyre.coloredredstone.proxy;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;

public class CommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {

    }

    public void registerRenderers(){

    }

    public String localize(String unlocalized, Object... args) {
        return new TextComponentTranslation(unlocalized, args).getUnformattedComponentText();
    }
}
