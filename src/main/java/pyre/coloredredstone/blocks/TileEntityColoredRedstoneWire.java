package pyre.coloredredstone.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pyre.coloredredstone.util.EnumColor;

public class TileEntityColoredRedstoneWire extends TileEntityColored {

    public TileEntityColoredRedstoneWire() {
        super(EnumColor.RED);
    }

    public TileEntityColoredRedstoneWire(EnumColor color) {
        super(color);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
