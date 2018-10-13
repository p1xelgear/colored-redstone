package pyre.coloredredstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.OreDictionaryUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

public class BlockColoredRedstone extends Block implements IColoredFeatures, IBlockColoredWithoutRed {

    public BlockColoredRedstone(String name) {
        super(Material.IRON, MapColor.TNT);
        setUnlocalizedName(name);
        setRegistryName(name);
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(CreativeTabs.REDSTONE);
        setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumColor.BLACK));

        ModBlocks.BLOCKS.add(this);
    }

    @Override
    public void setColor(World world, BlockPos pos, EnumColor color) {
        IBlockState state;
        if (color == EnumColor.RED){
            state = Blocks.REDSTONE_BLOCK.getDefaultState();
        } else {
            state = ModBlocks.COLORED_REDSTONE_BLOCK.getDefaultState()
                    .withProperty(COLOR, color);
        }
        world.setBlockState(pos, state, 2);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return getColor(world, pos) == EXPLOSION_PROOF_COLOR ? EXPLOSION_PROOF_BLOCK_RESISTANCE : super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return 15;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        EnumColor color = getColor(worldIn, pos);
        return color != EnumColor.RED ? new ItemStack(ModItems.COLORED_REDSTONE_BLOCK, 1, color.getMetadata()) : new ItemStack(Blocks.REDSTONE_BLOCK);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(COLOR) != EnumColor.RED ? ModItems.COLORED_REDSTONE_BLOCK : Item.getItemFromBlock(Blocks.REDSTONE_BLOCK);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR) != EnumColor.RED ? state.getValue(COLOR).getMetadata() : 0;
    }

    @Override
    @SuppressWarnings({"deprecation", "NullableProblems"})
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(COLOR, EnumColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        Arrays.stream(EnumColor.values())
                .filter(color -> color.getMetadata() != EnumColor.RED.getMetadata())
                .forEach(color -> items.add(new ItemStack(this, 1, color.getMetadata())));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);

        if (!heldItem.isEmpty() && heldItem.getCount() >= 9) {
            EnumColor color = OreDictionaryUtils.getDyeColor(heldItem);
            if (color != null && color != getColor(worldIn, pos)) {
                if (color == EnumColor.RED){
                    IBlockState redstoneBlockState = Blocks.REDSTONE_BLOCK.getDefaultState();
                    worldIn.setBlockState(pos, redstoneBlockState, 2);
                }
                setColor(worldIn, pos, color);
                heldItem.shrink(9);
                return true;
            }
        }
        return false;
    }
}
