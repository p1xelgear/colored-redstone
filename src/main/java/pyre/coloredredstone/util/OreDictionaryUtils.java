package pyre.coloredredstone.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryUtils {

    public static EnumColor getDyeColor(ItemStack stack){

        for (EnumColor enumColor : EnumColor.values()) {
            if (isItemStackRegisteredForName(stack, enumColor.getOreDictionaryDyeEntry())){
                return enumColor;
            }
        }
        return null;
    }

    public static boolean isItemStackRegisteredForName(ItemStack stack, String entryName){
        int[] oreIDs = OreDictionary.getOreIDs(stack);

        for (int oreID : oreIDs) {
            if (entryName.equals(OreDictionary.getOreName(oreID))) {
                return true;
            }
        }
        return false;
    }
}
