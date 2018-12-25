package pyre.coloredredstone.blocks;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import java.util.Arrays;
import java.util.Random;

import static pyre.coloredredstone.util.EnumColor.RED;

@SuppressWarnings("NullableProblems")
public class BlockColoredRedstoneRepeater extends BlockRedstoneRepeater implements IColoredFeatures, IBlockColoredTE<TileEntityColoredRedstoneRepeater> {

    public BlockColoredRedstoneRepeater(String name, boolean powered) {
        super(powered);
        setRegistryName(name);
        setHardness(0.0F);
        setSoundType(SoundType.WOOD);
        disableStats();
        this.setDefaultState(super.getDefaultState().withProperty(COLOR, RED));

        ModBlocks.BLOCKS.add(this);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, DELAY, LOCKED, COLOR);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState newState = super.getActualState(state, worldIn, pos);
        return newState.withProperty(COLOR, getColor(worldIn, pos));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(COLOR, EnumColor.byMetadata(meta));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityColoredRedstoneRepeater(state.getValue(COLOR));
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
        return color != RED ? new ItemStack(ModItems.COLORED_REDSTONE_REPEATER, 1, color.getMetadata()) : new ItemStack(Items.REPEATER);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        IBlockState newState = getActualState(state, world, pos);
        super.getDrops(drops, world, pos, newState, fortune);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(COLOR) != RED ? ModItems.COLORED_REDSTONE_REPEATER : Items.REPEATER;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR) != RED ? state.getValue(COLOR).getMetadata() : 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        Arrays.stream(EnumColor.values())
                .filter(color -> color != RED)
                .forEach(color -> items.add(new ItemStack(this, 1, color.getMetadata())));
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
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        this.notifyNeighbors(worldIn, pos, state);
    }

    @Override
    protected IBlockState getPoweredState(IBlockState unpoweredState) {
        int delay = unpoweredState.getValue(DELAY);
        boolean isLocked = unpoweredState.getValue(LOCKED);
        EnumFacing facing = unpoweredState.getValue(FACING);
        EnumColor color = unpoweredState.getValue(COLOR);
        return ModBlocks.POWERED_COLORED_REDSTONE_REPEATER.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(DELAY, delay)
                .withProperty(LOCKED, isLocked)
                .withProperty(COLOR, color);
    }

    @Override
    protected IBlockState getUnpoweredState(IBlockState poweredState) {
        int delay = poweredState.getValue(DELAY);
        boolean isLocked = poweredState.getValue(LOCKED);
        EnumFacing facing = poweredState.getValue(FACING);
        EnumColor color = poweredState.getValue(COLOR);
        return ModBlocks.UNPOWERED_COLORED_REDSTONE_REPEATER.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(DELAY, delay)
                .withProperty(LOCKED, isLocked)
                .withProperty(COLOR, color);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

        if (!worldIn.isRemote && entityIn instanceof EntityLivingBase && (worldIn.getWorldTime() % 20 == 0)) {
            EnumColor color = getColor(worldIn, pos);

            if (color.equals(WITHERING_COLOR)) {
                withering(worldIn, entityIn);
            } else if (color.equals(SLUGGISH_COLOR)) {
                sluggish(worldIn, entityIn);
            } else if (color.equals(SPEEDY_COLOR)) {
                speedy(worldIn, entityIn);
            } else if (color.equals(HEALTHY_COLOR)) {
                healthy(worldIn, entityIn);
            }
        }
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        EnumColor color = getColor(world, pos);
        if (CurrentModConfig.burnable && CurrentModConfig.burnableCatchFire && color.equals(BURNABLE_COLOR)) {
            return BURNABLE_FLAMMABILITY;
        }
        return 0;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        EnumColor color = getColor(world, pos);
        if (CurrentModConfig.burnable && CurrentModConfig.burnableCatchFire && color.equals(BURNABLE_COLOR)) {
            return BURNABLE_FIRE_SPREAD_SPEED;
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (this.isRepeaterPowered) {
            EnumFacing enumfacing = stateIn.getValue(FACING);
            double posX = (double) ((float) pos.getX() + 0.5F) + (rand.nextDouble() - 0.5F) * 0.2D;
            double posY = (double) ((float) pos.getY() + 0.4F) + (rand.nextDouble() - 0.5F) * 0.2D;
            double posZ = (double) ((float) pos.getZ() + 0.5F) + (rand.nextDouble() - 0.5F) * 0.2D;
            float f = -5.0F;

            if (rand.nextBoolean()) {
                f = (float) (stateIn.getValue(DELAY) * 2 - 1);
            }

            f = f / 16.0F;
            double offsetX = (double) (f * (float) enumfacing.getFrontOffsetX());
            double offsetZ = (double) (f * (float) enumfacing.getFrontOffsetZ());

            EnumColor color = getColor(worldIn, pos);
            double red = color.getShades()[15].getR() / 255.0F;
            double green = color.getShades()[15].getG() / 255.0F;
            double blue = color.getShades()[15].getB() / 255.0F;

            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, posX + offsetX, posY, posZ + offsetZ, red, green, blue);
        }
    }
}
