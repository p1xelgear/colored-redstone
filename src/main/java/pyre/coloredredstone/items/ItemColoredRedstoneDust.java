package pyre.coloredredstone.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.blocks.BlockColoredRedstoneWire;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class ItemColoredRedstoneDust extends ItemBase {

    public ItemColoredRedstoneDust(String name) {
        super(name);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
        BlockPos blockpos = flag ? pos : pos.offset(facing);
        ItemStack itemstack = player.getHeldItem(hand);

        if (player.canPlayerEdit(blockpos, facing, itemstack) && worldIn.mayPlace(worldIn.getBlockState(blockpos).getBlock(), blockpos, false, facing, (Entity)null) && ModBlocks.COLORED_REDSTONE_WIRE.canPlaceBlockAt(worldIn, blockpos))
        {
            IBlockState state = ModBlocks.COLORED_REDSTONE_WIRE.getDefaultState().withProperty(BlockColoredRedstoneWire.COLOR, EnumDyeColor.BLUE);
            worldIn.setBlockState(blockpos, state);

            if (player instanceof EntityPlayerMP)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, blockpos, itemstack);
            }

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public void registerModels() {
        ModelResourceLocation blackColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=0");
        ModelResourceLocation greenColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=1");
        ModelResourceLocation brownColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=2");
        ModelResourceLocation blueColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=3");
        ModelResourceLocation purpleColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=4");
        ModelResourceLocation cyanColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=5");
        ModelResourceLocation lightGrayColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=6");
        ModelResourceLocation grayColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=7");
        ModelResourceLocation pinkColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=8");
        ModelResourceLocation limeColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=9");
        ModelResourceLocation yellowColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=10");
        ModelResourceLocation lightBlueColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=11");
        ModelResourceLocation magentaColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=12");
        ModelResourceLocation orangeColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=13");
        ModelResourceLocation whiteColoredRedstoneDustModel = new ModelResourceLocation(getRegistryName(), "color=14");

        ModelLoader.setCustomModelResourceLocation(this, 0, blackColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 1, greenColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 2, brownColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 3, blueColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 4, purpleColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 5, cyanColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 6, lightGrayColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 7, grayColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 8, pinkColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 9, limeColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 10, yellowColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 11, lightBlueColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 12, magentaColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 13, orangeColoredRedstoneDustModel);
        ModelLoader.setCustomModelResourceLocation(this, 14, whiteColoredRedstoneDustModel);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == CreativeTabs.REDSTONE){
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
            items.add(new ItemStack(this, 1, 2));
            items.add(new ItemStack(this, 1, 3));
            items.add(new ItemStack(this, 1, 4));
            items.add(new ItemStack(this, 1, 5));
            items.add(new ItemStack(this, 1, 6));
            items.add(new ItemStack(this, 1, 7));
            items.add(new ItemStack(this, 1, 8));
            items.add(new ItemStack(this, 1, 9));
            items.add(new ItemStack(this, 1, 10));
            items.add(new ItemStack(this, 1, 11));
            items.add(new ItemStack(this, 1, 12));
            items.add(new ItemStack(this, 1, 13));
            items.add(new ItemStack(this, 1, 14));
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getMetadata() == 3) {
            tooltip.add(TextFormatting.AQUA + I18n.format(Reference.MOD_ID + ".tooltip.waterproof"));
        }
    }
}
