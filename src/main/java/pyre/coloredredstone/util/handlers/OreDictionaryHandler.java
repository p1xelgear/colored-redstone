package pyre.coloredredstone.util.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import pyre.coloredredstone.init.ModItems;

public class OreDictionaryHandler {

    public static void registerOreDictionary() {
        OreDictionary.registerOre("dustRedstone", new ItemStack(ModItems.COLORED_REDSTONE_DUST, 1, OreDictionary.WILDCARD_VALUE));
    }
}
