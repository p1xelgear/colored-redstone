package pyre.coloredredstone.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pyre.coloredredstone.util.EnumColor;

public class TileEntityColoredRedstoneComparator extends TileEntityColored {

    private static final String NBT_OUTPUT_SIGNAL_TAG = "OutputSignal";

    private int outputSignal;

    public TileEntityColoredRedstoneComparator() {
        super(EnumColor.RED);
    }

    public TileEntityColoredRedstoneComparator(EnumColor color) {
        super(color);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(NBT_OUTPUT_SIGNAL_TAG, this.outputSignal);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.outputSignal = compound.getInteger(NBT_OUTPUT_SIGNAL_TAG);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return !(newState.getBlock() instanceof BlockColoredRedstoneComparator);
    }

    public int getOutputSignal() {
        return this.outputSignal;
    }

    public void setOutputSignal(int outputSignalIn) {
        this.outputSignal = outputSignalIn;
    }
}
