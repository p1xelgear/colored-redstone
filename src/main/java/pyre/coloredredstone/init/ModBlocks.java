package pyre.coloredredstone.init;

import net.minecraft.block.Block;
import pyre.coloredredstone.blocks.BlockColoredRedstoneWire;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static BlockColoredRedstoneWire COLORED_REDSTONE_WIRE;

    public static void init(){
        COLORED_REDSTONE_WIRE = new BlockColoredRedstoneWire();
        COLORED_REDSTONE_WIRE.setRegistryName("minecraft:redstone_wire");
    }
}
