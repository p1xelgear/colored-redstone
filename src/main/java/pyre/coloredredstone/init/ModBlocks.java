package pyre.coloredredstone.init;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import pyre.coloredredstone.blocks.*;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static BlockColoredRedstoneWire COLORED_REDSTONE_WIRE;
    public static BlockColoredRedstone COLORED_REDSTONE_BLOCK;
    public static BlockColoredRedstoneTorch COLORED_REDSTONE_TORCH;
    public static BlockColoredRedstoneTorch UNLIT_COLORED_REDSTONE_TORCH;
    public static BlockColoredRedstoneRepeater POWERED_COLORED_REDSTONE_REPEATER;
    public static BlockColoredRedstoneRepeater UNPOWERED_COLORED_REDSTONE_REPEATER;
    public static BlockColoredRedstoneComparator POWERED_COLORED_REDSTONE_COMPARATOR;
    public static BlockColoredRedstoneComparator UNPOWERED_COLORED_REDSTONE_COMPARATOR;
    public static BlockColoredRedstoneLamp COLORED_REDSTONE_LAMP;

    public static void init(){
        COLORED_REDSTONE_WIRE = new BlockColoredRedstoneWire();
        COLORED_REDSTONE_WIRE.setRegistryName("minecraft:redstone_wire");
        COLORED_REDSTONE_BLOCK = new BlockColoredRedstone("colored_redstone_block");
        COLORED_REDSTONE_TORCH = new BlockColoredRedstoneTorch("colored_redstone_torch", true);
        COLORED_REDSTONE_TORCH.setLightLevel(0.5F).setCreativeTab(CreativeTabs.REDSTONE);
        UNLIT_COLORED_REDSTONE_TORCH = new BlockColoredRedstoneTorch("unlit_colored_redstone_torch", false);
        POWERED_COLORED_REDSTONE_REPEATER = new BlockColoredRedstoneRepeater("minecraft:powered_repeater", true);
        UNPOWERED_COLORED_REDSTONE_REPEATER = new BlockColoredRedstoneRepeater("minecraft:unpowered_repeater", false);
        UNPOWERED_COLORED_REDSTONE_REPEATER.setCreativeTab(CreativeTabs.REDSTONE).setUnlocalizedName("unpowered_colored_redstone_repeater");
        POWERED_COLORED_REDSTONE_COMPARATOR = new BlockColoredRedstoneComparator("minecraft:powered_comparator", true);
        POWERED_COLORED_REDSTONE_COMPARATOR.setLightLevel(0.625F);
        UNPOWERED_COLORED_REDSTONE_COMPARATOR = new BlockColoredRedstoneComparator("minecraft:unpowered_comparator", false);
        UNPOWERED_COLORED_REDSTONE_COMPARATOR.setCreativeTab(CreativeTabs.REDSTONE).setUnlocalizedName("unpowered_colored_redstone_comparator");
        COLORED_REDSTONE_LAMP = new BlockColoredRedstoneLamp("colored_redstone_lamp");
    }
}
