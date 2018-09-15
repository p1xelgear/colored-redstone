package pyre.coloredredstone.blocks;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.init.ModMaterials;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;
import java.util.Random;

import static pyre.coloredredstone.util.EnumColor.RED;

public class BlockColoredRedstoneWire extends BlockRedstoneWire {

    public static final PropertyEnum<EnumColor> COLOR = PropertyEnum.create("color", EnumColor.class);

    public BlockColoredRedstoneWire() {
        super();
        this.setDefaultState(super.getDefaultState().withProperty(COLOR, RED));

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

        EnumColor color = state.getValue(COLOR);

        TileEntity tileentity = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityColoredRedstoneWire){
            color = ((TileEntityColoredRedstoneWire)tileentity).getColor();
            if (color == null){
                color = state.getValue(COLOR);
                ((TileEntityColoredRedstoneWire)tileentity).setColor(color);
            }
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
        TileEntityColoredRedstoneWire te = new TileEntityColoredRedstoneWire();
        te.setColor(state.getValue(COLOR));
        return te;
    }

    @Override
    public Material getMaterial(IBlockState state) {
        EnumColor color = state.getValue(COLOR);
        if (color == EnumColor.BLUE){
            return ModMaterials.CIRCUITS_WATERPROOF;
        }
        return super.getMaterial(state);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {

        int metadata = state.getValue(COLOR).getMetadata();
        if (metadata != 1){
            return new ItemStack(ModItems.COLORED_REDSTONE_DUST, 1, metadata);
        }
        return new ItemStack(Items.REDSTONE);
    }

    @SideOnly(Side.CLIENT)
    public static int colorMultiplier(int power, EnumColor color) {

        int red = color.getShades()[power].getR();
        int green = color.getShades()[power].getG();
        int blue = color.getShades()[power].getB();

        return -16777216 | red << 16 | green << 8 | blue;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        int i = stateIn.getValue(POWER);

        if (i != 0)
        {
            double d0 = (double)pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D;
            double d1 = (double)((float)pos.getY() + 0.0625F);
            double d2 = (double)pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D;

            EnumColor color =  EnumColor.RED;
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityColoredRedstoneWire) {
                color = ((TileEntityColoredRedstoneWire)tileEntity).getColor();
            }
            float f1 = color.getShades()[i].getR() / 255.0F;
            float f2 = color.getShades()[i].getG() / 255.0F;
            float f3 = color.getShades()[i].getB() / 255.0F;

            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, (double)f1, (double)f2, (double)f3);
        }
    }
}
