package pyre.coloredredstone.util.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;

public class OreDictionaryHandler {

    public static void registerOreDictionary() {
        for (int meta = 1; meta <= 15; meta++) {
            OreDictionary.registerOre("dustColoredRedstone", new ItemStack(ModItems.COLORED_REDSTONE_DUST, 1, meta));
            OreDictionary.registerOre("blockRedstone", new ItemStack(ModBlocks.COLORED_REDSTONE_BLOCK, 1, meta));
            OreDictionary.registerOre("torchRedstone", new ItemStack(ModBlocks.COLORED_REDSTONE_TORCH, 1, meta));
            OreDictionary.registerOre("comparator", new ItemStack(ModItems.COLORED_REDSTONE_COMPARATOR, 1, meta));
            OreDictionary.registerOre("repeater", new ItemStack(ModItems.COLORED_REDSTONE_REPEATER, 1, meta));
        }
        for (int meta = 0; meta <= 15; meta++) {
            OreDictionary.registerOre("lampRedstone", new ItemStack(ModItems.COLORED_REDSTONE_LAMP, 1, meta));
        }
    }
}
