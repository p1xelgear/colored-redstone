package pyre.coloredredstone.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.init.ModItems;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class ItemColoredRedstoneBlock extends ItemColored implements IColoredItem {

    public ItemColoredRedstoneBlock(Block block, String name) {
        super(block, true);
        setRegistryName(name);

        ModItems.ITEMS.add(this);
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
}
