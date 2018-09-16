package pyre.coloredredstone.blocks;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.init.ModMaterials;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.OreDictionaryUtils;

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

    @Nullable
    private TileEntityColoredRedstoneWire getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityColoredRedstoneWire) world.getTileEntity(pos);
    }

    private EnumColor getColor(IBlockAccess world, BlockPos pos) {
        final TileEntityColoredRedstoneWire tileEntity = getTileEntity(world, pos);
        return tileEntity != null ? tileEntity.getColor() : EnumColor.RED;
    }

    private void setColor(IBlockAccess world, BlockPos pos, EnumColor color) {
        final TileEntityColoredRedstoneWire tileEntity = getTileEntity(world, pos);
        if (tileEntity != null) {
            tileEntity.setColor(color);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, POWER, COLOR);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        state = super.getActualState(state, worldIn, pos);
        return state.withProperty(COLOR, getColor(worldIn, pos));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityColoredRedstoneWire();
    }

    @Override
    public Material getMaterial(IBlockState state) {
        EnumColor color = state.getValue(COLOR);
        if (color == EnumColor.BLUE){
            return ModMaterials.CIRCUITS_WATERPROOF;
        }
        return super.getMaterial(state);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {

        EnumColor color = getColor(worldIn, pos);
        if (color != EnumColor.RED){
            return new ItemStack(ModItems.COLORED_REDSTONE_DUST, 1, color.getMetadata());
        }
        return new ItemStack(Items.REDSTONE);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        state = getActualState(state, world, pos);
        super.getDrops(drops, world, pos, state, fortune);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        EnumColor color = state.getValue(COLOR);
        if (color != EnumColor.RED) {
            return ModItems.COLORED_REDSTONE_DUST;
        }
        return Items.REDSTONE;
    }

    @Override
    public int damageDropped(IBlockState state) {
        EnumColor color = state.getValue(COLOR);
        if (color != EnumColor.RED) {
            return state.getValue(COLOR).getMetadata();
        }
        return 0;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);

        if (!heldItem.isEmpty()) {
            EnumColor color = OreDictionaryUtils.getDyeColor(heldItem);
            if (color != null && color != getColor(worldIn, pos)) {
                setColor(worldIn, pos, color);
                heldItem.shrink(1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
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
