package pyre.coloredredstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.init.ModMaterials;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("NullableProblems")
public class BlockColoredRedstoneTorch extends BlockRedstoneTorch implements IColoredFeatures, IBlockColoredWithoutRedTE<TileEntityColoredRedstoneTorch> {

    private Method isBurnedOut;
    private Method shouldBeOff;
    private Field isOn;
    private Field toggles;

    public BlockColoredRedstoneTorch(String name, boolean isOn) {
        super(isOn);
        setRegistryName(name);
        setUnlocalizedName(name);
        setHardness(0.0F);
        setSoundType(SoundType.WOOD);

        ModBlocks.BLOCKS.add(this);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, COLOR);
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
                worldIn.setBlockState(pos, ModBlocks.UNLIT_COLORED_REDSTONE_TORCH.getDefaultState()
                        .withProperty(FACING, state.getValue(FACING))
                        .withProperty(COLOR, state.getValue(COLOR)), 3);

                if (this.isBurnedOut(worldIn, pos, true)) {
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS,
                            0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

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
            worldIn.setBlockState(pos, ModBlocks.COLORED_REDSTONE_TORCH.getDefaultState()
                    .withProperty(FACING, state.getValue(FACING))
                    .withProperty(COLOR, state.getValue(COLOR)), 3);
        }
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
        return color != EnumColor.RED ? new ItemStack(ModItems.COLORED_REDSTONE_TORCH, 1, color.getMetadata()) : new ItemStack(Blocks.REDSTONE_TORCH);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        IBlockState actualState = getActualState(state, world, pos);
        super.getDrops(drops, world, pos, actualState, fortune);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(COLOR) != EnumColor.RED ? ModItems.COLORED_REDSTONE_TORCH : Item.getItemFromBlock(Blocks.REDSTONE_TORCH);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR) != EnumColor.RED ? state.getValue(COLOR).getMetadata() : 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        Arrays.stream(EnumColor.values())
                .filter(color -> color != EnumColor.RED)
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
    public boolean isAssociatedBlock(Block other) {
        return super.isAssociatedBlock(other) || other == ModBlocks.COLORED_REDSTONE_TORCH || other == ModBlocks.UNLIT_COLORED_REDSTONE_TORCH;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        withering(worldIn, entityIn, getColor(worldIn, pos));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (isOn()) {
            double posX = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            double posY = (double) pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
            double posZ = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            EnumFacing enumfacing = stateIn.getValue(FACING);

            if (enumfacing.getAxis().isHorizontal()) {
                EnumFacing oppositeFacing = enumfacing.getOpposite();
                posX += 0.27D * (double) oppositeFacing.getFrontOffsetX();
                posY += 0.22D;
                posZ += 0.27D * (double) oppositeFacing.getFrontOffsetZ();
            }

            EnumColor color = getColor(worldIn, pos);
            double red = color.getShades()[15].getR() / 255.0F;
            double green = color.getShades()[15].getG() / 255.0F;
            double blue = color.getShades()[15].getB() / 255.0F;

            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, red, green, blue);
        }
    }

    private boolean isBurnedOut(World worldIn, BlockPos pos, boolean turnOff) {
        try {
            if (isBurnedOut == null) {
                isBurnedOut = ReflectionHelper.findMethod(this.getClass().getSuperclass(), "isBurnedOut",
                        "func_176598_a", World.class, BlockPos.class, boolean.class);
            }
            return (boolean) isBurnedOut.invoke(this, worldIn, pos, turnOff);
        } catch (IllegalAccessException | InvocationTargetException e) {
            ColoredRedstone.logger.error("Cannot invoke 'isBurnedOut' method for ColoredRedstoneTorch.", e);
            throw new RuntimeException("Cannot invoke 'isBurnedOut' method for ColoredRedstoneTorch.", e);
        }
    }

    private boolean shouldBeOff(World worldIn, BlockPos pos, IBlockState state) {
        try {
            if (shouldBeOff == null) {
                shouldBeOff = ReflectionHelper.findMethod(this.getClass().getSuperclass(), "shouldBeOff",
                        "func_176597_g", World.class, BlockPos.class, IBlockState.class);
            }
            return (boolean) shouldBeOff.invoke(this, worldIn, pos, state);
        } catch (IllegalAccessException | InvocationTargetException e) {
            ColoredRedstone.logger.error("Cannot invoke 'shouldBeOff' method for ColoredRedstoneTorch.", e);
            throw new RuntimeException("Cannot invoke 'shouldBeOff' method for ColoredRedstoneTorch.", e);
        }
    }

    private boolean isOn() {
        try {
            if (isOn == null) {
                isOn = ReflectionHelper.findField(this.getClass().getSuperclass(), "isOn", "field_150113_a");
            }
            return (boolean) isOn.get(this);
        } catch (IllegalAccessException e) {
            ColoredRedstone.logger.error("Cannot get 'isOn' value for ColoredRedstoneTorch.", e);
            throw new RuntimeException("Cannot get 'isOn' value for ColoredRedstoneTorch.", e);
        }
    }

    private Map<World, List<?>> getToggles() {
        try {
            if (toggles == null) {
                toggles = ReflectionHelper.findField(this.getClass().getSuperclass(), "toggles", "field_150112_b");
            }
            @SuppressWarnings("unchecked")
            Map<World, List<?>> toggle = (Map<World, List<?>>) toggles.get(this);
            return toggle;
        } catch (IllegalAccessException e) {
            ColoredRedstone.logger.error("Cannot get 'toggles' value for ColoredRedstoneTorch.", e);
            throw new RuntimeException("Cannot get 'toggles' value for ColoredRedstoneTorch.", e);
        }
    }

    private long getTimeForToggle(Object toggle, Class<?> innerClass) {
        try {
            Field time = ReflectionHelper.findField(innerClass, "time", "field_150844_d");
            return (long) time.get(toggle);
        } catch (IllegalAccessException e) {
            ColoredRedstone.logger.error("Cannot get 'toggles.time' value for ColoredRedstoneTorch.", e);
            throw new RuntimeException("Cannot get 'toggles.time' value for ColoredRedstoneTorch.", e);
        }
    }
}
