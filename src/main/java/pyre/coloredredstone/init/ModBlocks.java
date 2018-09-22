package pyre.coloredredstone.init;

import net.minecraft.block.Block;
import pyre.coloredredstone.blocks.BlockColoredRedstone;
import pyre.coloredredstone.blocks.BlockColoredRedstoneWire;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static BlockColoredRedstoneWire COLORED_REDSTONE_WIRE;
    public static BlockColoredRedstone COLORED_REDSTONE_BLOCK;

    public static void init(){
        COLORED_REDSTONE_WIRE = new BlockColoredRedstoneWire();
        COLORED_REDSTONE_WIRE.setRegistryName("minecraft:redstone_wire");
        COLORED_REDSTONE_BLOCK = new BlockColoredRedstone("colored_redstone_block");
    }
}
