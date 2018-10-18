package pyre.coloredredstone.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.entities.EntityItemColored;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.Reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IColoredItem {

    default List<ItemStack> getSubItemsList(CreativeTabs tab, Item item){
        List<ItemStack> result = new ArrayList<>();
        if (tab == CreativeTabs.REDSTONE) {
            Arrays.stream(EnumColor.values())
                    .filter(color -> color != EnumColor.RED)
                    .forEach(color -> result.add(new ItemStack(item, 1, color.getMetadata())));
        }
        return result;
    }

    default String getColoredItemStackDisplayName(String unlocalizedName, ItemStack stack) {
        String result = ColoredRedstone.proxy.localize(unlocalizedName + ".name");
        int metadata = stack.getMetadata();
        EnumColor color = EnumColor.byMetadata(metadata);
        result += " (" + color.getChatColor() + color.getDisplayName() + TextFormatting.WHITE + ")";
        return result;
    }

    default List<String> getColoredTooltips(ItemStack stack){
        List<String> tooltips = new ArrayList<>();
        EnumColor color = EnumColor.byMetadata(stack.getMetadata());
        switch (color) {
            case BLUE:
                tooltips.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.waterproof"));
                break;
            case GREEN:
                tooltips.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.cactusproof"));
                break;
            case YELLOW:
                tooltips.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.fireproof"));
                break;
            case ORANGE:
                tooltips.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.explosionproof"));
                break;
            case WHITE:
                tooltips.add(color.getChatColor() + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.despawnproof"));
        }
        return tooltips;
    }

    default EntityItem createColoredEntityItem(World world, Entity oldEntityItem, ItemStack itemstack){
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