package pyre.coloredredstone.init;

import net.minecraft.item.Item;
import pyre.coloredredstone.items.ItemColoredRedstoneDust;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<>();

    public static Item COLORED_REDSTONE_DUST;

    public static void init(){
        COLORED_REDSTONE_DUST = new ItemColoredRedstoneDust("colored_redstone_dust");
    }
}
