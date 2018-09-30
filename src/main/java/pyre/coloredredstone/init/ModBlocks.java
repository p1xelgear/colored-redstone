package pyre.coloredredstone.init;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import pyre.coloredredstone.blocks.BlockColoredRedstone;
import pyre.coloredredstone.blocks.BlockColoredRedstoneTorch;
import pyre.coloredredstone.blocks.BlockColoredRedstoneWire;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static BlockColoredRedstoneWire COLORED_REDSTONE_WIRE;
    public static BlockColoredRedstone COLORED_REDSTONE_BLOCK;
    public static BlockColoredRedstoneTorch COLORED_REDSTONE_TORCH;
    public static BlockColoredRedstoneTorch UNLIT_COLORED_REDSTONE_TORCH;

    public static void init(){
        COLORED_REDSTONE_WIRE = new BlockColoredRedstoneWire();
        COLORED_REDSTONE_WIRE.setRegistryName("minecraft:redstone_wire");
        COLORED_REDSTONE_BLOCK = new BlockColoredRedstone("colored_redstone_block");
        COLORED_REDSTONE_TORCH = new BlockColoredRedstoneTorch("colored_redstone_torch", true);
        COLORED_REDSTONE_TORCH.setLightLevel(0.5F).setCreativeTab(CreativeTabs.REDSTONE);
        UNLIT_COLORED_REDSTONE_TORCH = new BlockColoredRedstoneTorch("unlit_colored_redstone_torch", false);
    }
}
