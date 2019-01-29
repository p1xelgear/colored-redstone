package pyre.coloredredstone.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class ItemColoredRedstoneLamp extends ItemColored implements IColoredItem {

    public ItemColoredRedstoneLamp(Block block, String name) {
        super(block, true);
        setRegistryName(name);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == CreativeTabs.REDSTONE) {
            Arrays.stream(EnumColor.values())
                    .forEach(color -> items.add(new ItemStack(this, 1, color.getMetadata())));
        }
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
    public int getItemBurnTime(ItemStack itemStack) {
        return getBurnTime(itemStack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return tryEatItem(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    public static int colorMultiplier(EnumColor color) {
        int red = color.getShades()[0].getR();
        int green = color.getShades()[0].getG();
        int blue = color.getShades()[0].getB();

        return -16777216 | red << 16 | green << 8 | blue;
    }
}
