package pyre.coloredredstone.blocks;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.init.ModMaterials;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;
import java.util.Random;

import static pyre.coloredredstone.util.EnumColor.RED;

@SuppressWarnings("NullableProblems")
public class BlockColoredRedstoneWire extends BlockRedstoneWire implements IColoredFeatures, IBlockColoredTE<TileEntityColoredRedstoneWire> {

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
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState actualState = super.getActualState(state, worldIn, pos);
        return actualState.withProperty(COLOR, getColor(worldIn, pos));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityColoredRedstoneWire(state.getValue(COLOR));
    }

    @SuppressWarnings("deprecation")
    @Override
    public Material getMaterial(IBlockState state) {
        return (CurrentModConfig.waterproof && state.getValue(COLOR) == WATERPROOF_COLOR) ?
                ModMaterials.CIRCUITS_WATERPROOF : super.getMaterial(state);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return (CurrentModConfig.explosionproof && getColor(world, pos) == EXPLOSION_PROOF_COLOR) ?
                EXPLOSION_PROOF_BLOCK_RESISTANCE : super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        EnumColor color = getColor(world, pos);
        return color != RED ? new ItemStack(ModItems.COLORED_REDSTONE_DUST, 1, color.getMetadata()) : new ItemStack(Items.REDSTONE);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        IBlockState actualState = getActualState(state, world, pos);
        super.getDrops(drops, world, pos, actualState, fortune);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(COLOR) != RED ? ModItems.COLORED_REDSTONE_DUST : Items.REDSTONE;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR) != RED ? state.getValue(COLOR).getMetadata() : 0;
    }

    //preserved TileEntity until after #getDrops has been called
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    //preserved TileEntity until after #getDrops has been called
    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        // If it will harvest, delay deletion of the block until after #getDrops
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        withering(worldIn, entityIn, getColor(worldIn, pos));
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
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int power = stateIn.getValue(POWER);

        if (power != 0) {
            double posX = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            double posY = (double) ((float) pos.getY() + 0.0625F);
            double posZ = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;

            EnumColor color = getColor(worldIn, pos);
            double red = color.getShades()[power].getR() / 255.0F;
            double green = color.getShades()[power].getG() / 255.0F;
            double blue = color.getShades()[power].getB() / 255.0F;

            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, red, green, blue);
        }
    }
}
