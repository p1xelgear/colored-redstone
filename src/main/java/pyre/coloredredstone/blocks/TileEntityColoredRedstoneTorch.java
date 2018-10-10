package pyre.coloredredstone.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pyre.coloredredstone.util.EnumColor;

public class TileEntityColoredRedstoneTorch extends TileEntityColored {

    public TileEntityColoredRedstoneTorch() {
        super(EnumColor.BLACK);
    }

    public TileEntityColoredRedstoneTorch(EnumColor color) {
        super(color);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return !(newState.getBlock() instanceof BlockColoredRedstoneTorch);
    }
}
