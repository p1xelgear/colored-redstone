package pyre.coloredredstone.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.init.ModItems;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Colored redstone Repeater and Comparator item.
 */
@SuppressWarnings("NullableProblems")
public class ItemColoredRedstoneDiode extends ItemBlockSpecial implements IColoredItem {

    private final Block block;

    public ItemColoredRedstoneDiode(Block block, String name) {
        super(block);
        this.block = block;
        setRegistryName(name);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.REDSTONE);

        ModItems.ITEMS.add(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (block == Blocks.SNOW_LAYER && iblockstate.getValue(BlockSnow.LAYERS) < 1) {
            facing = EnumFacing.UP;
        } else if (!block.isReplaceable(worldIn, pos)) {
            pos = pos.offset(facing);
        }

        ItemStack itemstack = player.getHeldItem(hand);

        if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, null)) {
            IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, itemstack.getMetadata(), player, hand);

            if (!worldIn.setBlockState(pos, iblockstate1, 11)) {
                return EnumActionResult.FAIL;
            } else {
                iblockstate1 = worldIn.getBlockState(pos);

                if (iblockstate1.getBlock() == this.block) {
                    ItemBlock.setTileEntityNBT(worldIn, player, pos, itemstack);
                    iblockstate1.getBlock().onBlockPlacedBy(worldIn, pos, iblockstate1, player, itemstack);

                    if (player instanceof EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemstack);
                    }
                }

                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.addAll(getSubItemsList(tab, this));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return getColoredItemStackDisplayName(this.getUnlocalizedName(stack), stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.addAll(getColoredTooltips(stack));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity oldEntityItem, ItemStack itemstack) {
        return createColoredEntityItem(world, oldEntityItem, itemstack);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.block.getUnlocalizedName();
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return getBurnTime(itemStack);
    }
}
