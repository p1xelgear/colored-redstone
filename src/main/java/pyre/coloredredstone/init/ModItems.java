package pyre.coloredredstone.init;

import net.minecraft.item.Item;
import pyre.coloredredstone.items.*;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<>();

    public static Item COLORED_REDSTONE_DUST;
    public static Item COLORED_REDSTONE_BLOCK;
    public static Item COLORED_REDSTONE_TORCH;
    public static Item COLORED_REDSTONE_REPEATER;
    public static Item COLORED_REDSTONE_COMPARATOR;
    public static Item COLORED_REDSTONE_LAMP;

    public static void init(){
        COLORED_REDSTONE_DUST = new ItemColoredRedstoneDust("colored_redstone_dust");
        COLORED_REDSTONE_BLOCK = new ItemColoredRedstoneBlock(ModBlocks.COLORED_REDSTONE_BLOCK, "colored_redstone_block");
        COLORED_REDSTONE_TORCH = new ItemColoredRedstoneTorch(ModBlocks.COLORED_REDSTONE_TORCH, "colored_redstone_torch_item");
        COLORED_REDSTONE_REPEATER = new ItemColoredRedstoneDiode(ModBlocks.UNPOWERED_COLORED_REDSTONE_REPEATER, "colored_redstone_repeater_item");
        COLORED_REDSTONE_COMPARATOR = new ItemColoredRedstoneDiode(ModBlocks.UNPOWERED_COLORED_REDSTONE_COMPARATOR, "colored_redstone_comparator_item");
        COLORED_REDSTONE_LAMP = new ItemColoredRedstoneLamp(ModBlocks.COLORED_REDSTONE_LAMP, "colored_redstone_lamp");
    }
}
