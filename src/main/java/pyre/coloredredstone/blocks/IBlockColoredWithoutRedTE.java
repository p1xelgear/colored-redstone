package pyre.coloredredstone.blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;

public interface IBlockColoredWithoutRedTE<TE extends TileEntityColored> extends IBlockColoredWithoutRed {

    @Nullable
    @SuppressWarnings("unchecked")
    default TE getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TE) world.getTileEntity(pos);
    }

    @Override
    default EnumColor getColor(IBlockAccess world, BlockPos pos) {
        final TE tileEntity = getTileEntity(world, pos);
        return tileEntity != null ? tileEntity.getColor() : EnumColor.BLACK;
    }

    @Override
    default void setColor(World world, BlockPos pos, EnumColor color) {
        final TE tileEntity = getTileEntity(world, pos);
        if (tileEntity != null) {
            tileEntity.setColor(color);
        }
    }
}
