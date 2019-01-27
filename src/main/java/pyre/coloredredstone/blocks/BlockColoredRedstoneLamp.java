package pyre.coloredredstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@SuppressWarnings("NullableProblems")
public class BlockColoredRedstoneLamp extends Block implements IColoredFeatures, IBlockColoredTE<TileEntityColoredRedstoneLamp> {

    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);

    public BlockColoredRedstoneLamp(String name) {
        super(Material.REDSTONE_LIGHT);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.REDSTONE);
        setHardness(0.3F);
        setSoundType(SoundType.GLASS);
        setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumColor.RED).withProperty(POWER, 0));

        ModBlocks.BLOCKS.add(this);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(POWER);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, POWER, COLOR);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(COLOR, getColor(worldIn, pos));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand)
                .withProperty(COLOR, EnumColor.byMetadata(meta))
                .withProperty(POWER, 0);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityColoredRedstoneLamp(state.getValue(COLOR));
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return (CurrentModConfig.explosionproof && getColor(world, pos) == EXPLOSION_PROOF_COLOR) ?
                EXPLOSION_PROOF_BLOCK_RESISTANCE : super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        EnumColor color = getColor(world, pos);
        return new ItemStack(ModItems.COLORED_REDSTONE_LAMP, 1, color.getMetadata());
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        IBlockState actualState = getActualState(state, world, pos);
        super.getDrops(drops, world, pos, actualState, fortune);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.COLORED_REDSTONE_LAMP;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        Arrays.stream(EnumColor.values())
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
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWER, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(POWER);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            int power = getBlockPower(worldIn, pos);
            Integer value = state.getValue(POWER);
            if (power != value) {
                worldIn.setBlockState(pos, getActualState(state, worldIn, pos).withProperty(POWER, power), 2);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote) {
            int power = getBlockPower(worldIn, pos);
            Integer value = state.getValue(POWER);
            if (value != 0 && power == 0) {
                worldIn.scheduleUpdate(pos, this, 4);
            } else {
                worldIn.setBlockState(pos, getActualState(state, worldIn, pos).withProperty(POWER, power), 2);
            }
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            int power = getBlockPower(worldIn, pos);
            Integer value = state.getValue(POWER);
            if (power != value) {
                worldIn.setBlockState(pos, getActualState(state, worldIn, pos).withProperty(POWER, power), 2);
            }
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);

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

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (CurrentModConfig.soft && getColor(worldIn, pos) == SOFT_COLOR && !entityIn.isSneaking()) {
            float damageMultiplier = 1.0F - (CurrentModConfig.softDamageReduction / 100.0F);
            entityIn.fall(fallDistance, damageMultiplier);
        } else {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        }
    }

    @SideOnly(Side.CLIENT)
    public static int colorMultiplier(int power, EnumColor color) {
        int red = color.getShades()[power].getR();
        int green = color.getShades()[power].getG();
        int blue = color.getShades()[power].getB();

        return -16777216 | red << 16 | green << 8 | blue;
    }

    private int getBlockPower(World world, BlockPos pos) {
        int powerDown = world.getRedstonePower(pos.down(), EnumFacing.DOWN);
        int powerUp = world.getRedstonePower(pos.up(), EnumFacing.UP);
        int powerNorth = world.getRedstonePower(pos.north(), EnumFacing.NORTH);
        int powerSouth = world.getRedstonePower(pos.south(), EnumFacing.SOUTH);
        int powerWest = world.getRedstonePower(pos.west(), EnumFacing.WEST);
        int powerEast = world.getRedstonePower(pos.east(), EnumFacing.EAST);
        return IntStream.of(powerDown, powerUp, powerNorth, powerSouth, powerWest, powerEast)
                .max()
                .orElse(0);
    }
}
