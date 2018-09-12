package pyre.coloredredstone.items;

import net.minecraft.item.Item;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.util.IHasModel;

public class ItemBase extends Item implements IHasModel {

    public ItemBase(String name) {

        setUnlocalizedName(name);
        setRegistryName(name);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        ColoredRedstone.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
