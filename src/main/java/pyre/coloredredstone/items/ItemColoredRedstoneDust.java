package pyre.coloredredstone.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.blocks.BlockColoredRedstoneWire;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.util.EnumColor;
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
            IBlockState state = ModBlocks.COLORED_REDSTONE_WIRE.getDefaultState().withProperty(BlockColoredRedstoneWire.COLOR, EnumColor.byMetadata(itemstack.getMetadata()));
            worldIn.setBlockState(blockpos, state, 2);

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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i = 0; i < EnumColor.values().length; i++) {
            if (i != 1){ //skip RED
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String result = ColoredRedstone.proxy.localize(this.getUnlocalizedName(stack) + ".name");
        int metadata = stack.getMetadata();
        EnumColor color = EnumColor.byMetadata(metadata);
        result += " (" + color.getChatColor() + color.getDisplayName() + TextFormatting.WHITE +")";

        return result;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getMetadata() == 4) {
            tooltip.add(EnumColor.byMetadata(4).getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.waterproof"));
        }
    }
}
