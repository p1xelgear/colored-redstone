package pyre.coloredredstone.items;

import net.minecraft.item.Item;
import pyre.coloredredstone.init.ModItems;

public class ItemBase extends Item {

    public ItemBase(String name) {

        setUnlocalizedName(name);
        setRegistryName(name);

        ModItems.ITEMS.add(this);
    }
}
