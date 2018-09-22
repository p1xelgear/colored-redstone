package pyre.coloredredstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockColoredRedstone extends Block {

    public static final PropertyEnum<EnumColor> COLOR = PropertyEnum.create("color", EnumColor.class, input -> input != EnumColor.RED);
    public static final float EXPLOSION_PROOF_BLOCK_RESISTANCE = 6000.0F;

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

    private EnumColor getColor(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getValue(COLOR);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
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
        if (color != EnumColor.RED){
            return new ItemStack(ModItems.COLORED_REDSTONE_BLOCK, 1, color.getMetadata());
        }
        return new ItemStack(Blocks.REDSTONE_BLOCK);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        EnumColor color = state.getValue(COLOR);
        if (color != EnumColor.RED) {
            return ModItems.COLORED_REDSTONE_BLOCK;
        }
        return Item.getItemFromBlock(Blocks.REDSTONE_BLOCK);
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
        for (int i = 0; i < EnumColor.values().length; i++) {
            if (i != 1) { //skip RED
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
}
