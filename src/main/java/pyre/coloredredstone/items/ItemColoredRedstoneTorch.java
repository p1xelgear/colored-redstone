package pyre.coloredredstone.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.entities.EntityItemColored;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class ItemColoredRedstoneTorch extends ItemColored {

    public ItemColoredRedstoneTorch(Block block, String name) {
        super(block, true);
        setRegistryName(name);

        ModItems.ITEMS.add(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
        BlockPos blockpos = flag ? pos : pos.offset(facing);
        ItemStack itemstack = player.getHeldItem(hand);

        if (player.canPlayerEdit(blockpos, facing, itemstack) && worldIn.mayPlace(worldIn.getBlockState(blockpos).getBlock(), blockpos, false, facing, null) && ModBlocks.COLORED_REDSTONE_TORCH.canPlaceBlockAt(worldIn, blockpos)) {

            IBlockState state = ModBlocks.COLORED_REDSTONE_TORCH.getStateForPlacement(worldIn, blockpos, facing, hitX, hitY, hitZ, itemstack.getMetadata(), player);
            worldIn.setBlockState(blockpos, state, 3);

            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, blockpos, itemstack);
            }

            SoundType sound = this.block.getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
            worldIn.playSound(player, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == CreativeTabs.REDSTONE) {
            for (int i = 0; i < EnumColor.values().length; i++) {
                if (i != 1) { //skip RED
                    items.add(new ItemStack(this, 1, i));
                }
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String result = ColoredRedstone.proxy.localize(this.getUnlocalizedName(stack) + ".name");
        int metadata = stack.getMetadata();
        EnumColor color = EnumColor.byMetadata(metadata);
        result += " (" + color.getChatColor() + color.getDisplayName() + TextFormatting.WHITE + ")";

        return result;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        EnumColor color = EnumColor.byMetadata(stack.getMetadata());
        switch (color) {
            case BLUE:
                tooltip.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.waterproof"));
                break;
            case GREEN:
                tooltip.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.cactusproof"));
                break;
            case YELLOW:
                tooltip.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.fireproof"));
                break;
            case ORANGE:
                tooltip.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.explosionproof"));
                break;
            case WHITE:
                tooltip.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.despawnproof"));
        }
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity oldEntityItem, ItemStack itemstack) {

        EntityItemColored newEntityItem = new EntityItemColored(world, oldEntityItem.posX, oldEntityItem.posY, oldEntityItem.posZ, itemstack);
        newEntityItem.motionX = oldEntityItem.motionX;
        newEntityItem.motionY = oldEntityItem.motionY;
        newEntityItem.motionZ = oldEntityItem.motionZ;
        newEntityItem.setPickupDelay(40);
        newEntityItem.hoverStart = ((EntityItem) oldEntityItem).hoverStart;
        newEntityItem.lifespan = ((EntityItem) oldEntityItem).lifespan;

        return newEntityItem;
    }
}
