package pyre.coloredredstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModMaterials;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.OreDictionaryUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockColoredRedstoneTorch extends BlockRedstoneTorch {

    public static final PropertyEnum<EnumColor> COLOR = PropertyEnum.create("color", EnumColor.class, input -> input != EnumColor.RED);
    public static final float EXPLOSION_PROOF_BLOCK_RESISTANCE = 6000.0F;

    public BlockColoredRedstoneTorch(String name, boolean isOn) {
        super(isOn);
        setRegistryName(name);
        setUnlocalizedName(name);
        setHardness(0.0F);
        setSoundType(SoundType.WOOD);

        ModBlocks.BLOCKS.add(this);
    }

    @Nullable
    private TileEntityColoredRedstoneTorch getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityColoredRedstoneTorch) world.getTileEntity(pos);
    }

    private EnumColor getColor(IBlockAccess world, BlockPos pos) {
        final TileEntityColoredRedstoneTorch tileEntity = getTileEntity(world, pos);
        return tileEntity != null ? tileEntity.getColor() : EnumColor.RED;
    }

    private void setColor(IBlockAccess world, BlockPos pos, EnumColor color) {
        final TileEntityColoredRedstoneTorch tileEntity = getTileEntity(world, pos);
        if (tileEntity != null) {
            tileEntity.setColor(color);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, COLOR);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        state = super.getActualState(state, worldIn, pos);
        return state.withProperty(COLOR, getColor(worldIn, pos));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(COLOR, EnumColor.byMetadata(meta));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityColoredRedstoneTorch(state.getValue(COLOR));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        boolean flag = this.shouldBeOff(worldIn, pos, state);
        List<?> list = getToggles().get(worldIn);

        while (list != null && !list.isEmpty() && worldIn.getTotalWorldTime() - getTimeForToggle(list.get(0), list.get(0).getClass()) > 60L) {
            list.remove(0);
        }

        if (isOn()) {
            if (flag) {
                worldIn.setBlockState(pos, ModBlocks.UNLIT_COLORED_REDSTONE_TORCH.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(COLOR, state.getValue(COLOR)), 3);

                if (this.isBurnedOut(worldIn, pos, true)) {
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

                    for (int i = 0; i < 5; ++i) {
                        double d0 = (double) pos.getX() + rand.nextDouble() * 0.6D + 0.2D;
                        double d1 = (double) pos.getY() + rand.nextDouble() * 0.6D + 0.2D;
                        double d2 = (double) pos.getZ() + rand.nextDouble() * 0.6D + 0.2D;
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }

                    worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 160);
                }
            }
        } else if (!flag && !this.isBurnedOut(worldIn, pos, false)) {
            worldIn.setBlockState(pos, ModBlocks.COLORED_REDSTONE_TORCH.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(COLOR, state.getValue(COLOR)), 3);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);

        if (!heldItem.isEmpty()) {
            EnumColor color = OreDictionaryUtils.getDyeColor(heldItem);
            if (color != null && color != getColor(worldIn, pos)) {
                if (color == EnumColor.RED){
                    IBlockState redstoneTorchState;
                    if (isOn()){
                        redstoneTorchState = Blocks.REDSTONE_TORCH.getDefaultState().withProperty(FACING, state.getValue(FACING));
                    } else {
                        redstoneTorchState = Blocks.UNLIT_REDSTONE_TORCH.getDefaultState().withProperty(FACING, state.getValue(FACING));
                    }
                    worldIn.setBlockState(pos, redstoneTorchState, 3);
                }
                setColor(worldIn, pos, color);
                heldItem.shrink(1);
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Material getMaterial(IBlockState state) {
        EnumColor color = state.getValue(COLOR);
        if (color == EnumColor.BLUE) {
            return ModMaterials.CIRCUITS_WATERPROOF;
        }
        return super.getMaterial(state);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        EnumColor color = getColor(world, pos);
        if (color == EnumColor.ORANGE) {
            return EXPLOSION_PROOF_BLOCK_RESISTANCE;
        }
        return super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        EnumColor color = getColor(worldIn, pos);
        if (color != EnumColor.RED) {
            return new ItemStack(ModBlocks.COLORED_REDSTONE_TORCH, 1, color.getMetadata());
        }
        return new ItemStack(Blocks.REDSTONE_TORCH);
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
            return Item.getItemFromBlock(ModBlocks.COLORED_REDSTONE_TORCH);
        }
        return Item.getItemFromBlock(Blocks.REDSTONE_TORCH);
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
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < EnumColor.values().length; i++) {
            if (i != 1) { //skip RED
                items.add(new ItemStack(this, 1, i));
            }
        }
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
    public boolean isAssociatedBlock(Block other) {
        return super.isAssociatedBlock(other) || other == ModBlocks.COLORED_REDSTONE_TORCH || other == ModBlocks.UNLIT_COLORED_REDSTONE_TORCH;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (isOn()) {
            double d0 = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            double d1 = (double) pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
            double d2 = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            EnumFacing enumfacing = stateIn.getValue(FACING);

            if (enumfacing.getAxis().isHorizontal()) {
                EnumFacing enumfacing1 = enumfacing.getOpposite();
                d0 += 0.27D * (double) enumfacing1.getFrontOffsetX();
                d1 += 0.22D;
                d2 += 0.27D * (double) enumfacing1.getFrontOffsetZ();
            }

            EnumColor color = getTileEntity(worldIn, pos).getColor();

            double f1 = color.getShades()[15].getR() / 255.0F;
            double f2 = color.getShades()[15].getG() / 255.0F;
            double f3 = color.getShades()[15].getB() / 255.0F;

            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, f1, f2, f3);
        }
    }

    private boolean isBurnedOut(World worldIn, BlockPos pos, boolean turnOff) {
        try {
            Method isBurnedOutMethod = this.getClass().getSuperclass().getDeclaredMethod("isBurnedOut", World.class, BlockPos.class, boolean.class);
            isBurnedOutMethod.setAccessible(true);
            return (boolean) isBurnedOutMethod.invoke(this, worldIn, pos, turnOff);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            ColoredRedstone.logger.warn("Cannot invoke 'isBurnedOut' method for ColoredRedstoneTorch.");
            return false;
        }
    }

    private boolean shouldBeOff(World worldIn, BlockPos pos, IBlockState state) {
        try {
            Method isBurnedOutMethod = this.getClass().getSuperclass().getDeclaredMethod("shouldBeOff", World.class, BlockPos.class, IBlockState.class);
            isBurnedOutMethod.setAccessible(true);
            return (boolean) isBurnedOutMethod.invoke(this, worldIn, pos, state);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            ColoredRedstone.logger.warn("Cannot invoke 'shouldBeOff' method for ColoredRedstoneTorch.");
            return false;
        }
    }

    private boolean isOn() {
        try {
            Field isOnField = this.getClass().getSuperclass().getDeclaredField("isOn");
            isOnField.setAccessible(true);
            return (boolean) isOnField.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            ColoredRedstone.logger.warn("Cannot get 'isOn' value for ColoredRedstoneTorch.");
            return true;
        }
    }

    private Map<World, List<?>> getToggles() {
        try {
            Field isOnField = this.getClass().getSuperclass().getDeclaredField("toggles");
            isOnField.setAccessible(true);
            return (Map<World, List<?>>) isOnField.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            ColoredRedstone.logger.warn("Cannot get 'toggles' value for ColoredRedstoneTorch.");
            return null;
        }
    }

    private long getTimeForToggle(Object toggle, Class<?> innerClass) {
        try {
            Field timeField = innerClass.getDeclaredField("time");
            timeField.setAccessible(true);
            return (long) timeField.get(toggle);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            ColoredRedstone.logger.warn("Cannot get 'toggles.time' value for ColoredRedstoneTorch.");
            return Long.MAX_VALUE;
        }
    }
}
