package pyre.coloredredstone.blocks;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pyre.coloredredstone.util.EnumColor;

public interface IBlockColoredWithoutRed {
    PropertyEnum<EnumColor> COLOR = PropertyEnum.create("color", EnumColor.class, input -> input != EnumColor.RED);

    default EnumColor getColor(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getValue(COLOR);
    }

    void setColor(World world, BlockPos pos, EnumColor color);
}
