package pyre.coloredredstone.blocks;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModMaterials;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockColoredRedstoneWire extends BlockRedstoneWire {

    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

    public BlockColoredRedstoneWire() {
        super();
        this.setDefaultState(super.getDefaultState().withProperty(COLOR, EnumDyeColor.RED));

        ModBlocks.BLOCKS.add(this);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, POWER, COLOR);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        state = super.getActualState(state, worldIn, pos);

        EnumDyeColor color = EnumDyeColor.RED;

        TileEntity tileentity = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityColoredRedstoneWire){
            color = ((TileEntityColoredRedstoneWire)tileentity).getColor();
        }
        state = state.withProperty(COLOR, color);

        return state;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityColoredRedstoneWire(state);
    }

    @Override
    public Material getMaterial(IBlockState state) {
        EnumDyeColor color = state.getValue(COLOR);
        if (color == EnumDyeColor.BLUE){
            return ModMaterials.CIRCUITS_WATERPROOF;
        }
        return super.getMaterial(state);
    }

    @SideOnly(Side.CLIENT)
    public static int colorMultiplier(int power, EnumDyeColor color)
    {
        float powerMultiplier = (float)power / 15.0F;
        float red, green, blue;

        if (color == EnumDyeColor.RED) {
            red = powerMultiplier * 0.6F + 0.4F;
        } else {
            red = powerMultiplier * powerMultiplier * 0.65F - 0.6F;
        }

        green = powerMultiplier * powerMultiplier * 0.7F - 0.5F;

        if (color == EnumDyeColor.RED) {
            blue = powerMultiplier * powerMultiplier * 0.6F - 0.7F;
        } else {
            blue = powerMultiplier * 0.6F + 0.45F;
        }

        if (power == 0)
        {
            if (color == EnumDyeColor.RED) {
                red = 0.3F;
            } else {
                blue = 0.35F;
            }
        }

        if (green < 0.0F)
        {
            green = 0.0F;
        }

        if (blue < 0.0F)
        {
            blue = 0.0F;
        }

        int i = MathHelper.clamp((int)(red * 255.0F), 0, 255);
        int j = MathHelper.clamp((int)(green * 255.0F), 0, 255);
        int k = MathHelper.clamp((int)(blue * 255.0F), 0, 255);
        return -16777216 | i << 16 | j << 8 | k;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        int i = stateIn.getValue(POWER);

        if (i != 0)
        {
            double d0 = (double)pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D;
            double d1 = (double)((float)pos.getY() + 0.0625F);
            double d2 = (double)pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D;
            float f = (float)i / 15.0F;
            float f1 = f * 0.6F + 0.4F;
            float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
            float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, (double)f1, (double)f2, (double)f3);
        }
    }
}
