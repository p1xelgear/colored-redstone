package pyre.coloredredstone.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.config.ModConfig;
import pyre.coloredredstone.entities.EntityItemColored;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.Reference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pyre.coloredredstone.blocks.IColoredFeatures.BURNABLE_COLOR;
import static pyre.coloredredstone.blocks.IColoredFeatures.EDIBLE_COLOR;

public interface IColoredItem {

    default List<ItemStack> getSubItemsList(CreativeTabs tab, Item item) {
        List<ItemStack> result = new ArrayList<>();
        if (tab == CreativeTabs.REDSTONE) {
            Arrays.stream(EnumColor.values())
                    .filter(color -> color != EnumColor.RED)
                    .forEach(color -> result.add(new ItemStack(item, 1, color.getMetadata())));
        }
        return result;
    }

    default String getColoredItemStackDisplayName(String unlocalizedName, ItemStack stack) {
        int metadata = stack.getMetadata();
        EnumColor color = EnumColor.byMetadata(metadata);
        String localizedColor = ColoredRedstone.proxy.localize(color.getDisplayName());
        String displayColor = ModConfig.coloredNamesAndTooltips ? color.getChatColor() + localizedColor + TextFormatting.RESET : localizedColor;
        return ColoredRedstone.proxy.localize(unlocalizedName + ".name", displayColor);
    }

    default List<String> getColoredTooltips(ItemStack stack) {
        List<String> tooltips = new ArrayList<>();
        EnumColor color = EnumColor.byMetadata(stack.getMetadata());
        String chatColor = ModConfig.coloredNamesAndTooltips ? color.getChatColor().toString() : "";
        switch (color) {
            case BLACK:
                if (CurrentModConfig.withering) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.withering"));
                }
                break;
            case GREEN:
                if (CurrentModConfig.cactusproof) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.cactusproof"));
                }
                break;
            case BROWN:
                if (CurrentModConfig.burnable) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.burnable"));
                }
                break;
            case BLUE:
                if (CurrentModConfig.waterproof) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.waterproof"));
                }
                break;
            case PURPLE:
                if (CurrentModConfig.soft) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.soft"));
                }
                break;
            case CYAN:
                if (CurrentModConfig.alienated) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.alienated"));
                }
                break;
            case LIGHT_GRAY:
                if (CurrentModConfig.speedy) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.speedy"));
                }
                break;
            case GRAY:
                if (CurrentModConfig.sluggish) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.sluggish"));
                }
                break;
            case PINK:
                if (CurrentModConfig.healthy) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.healthy"));
                }
                break;
            case LIME:
                if (CurrentModConfig.slimy) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.slimy"));
                }
                break;
            case YELLOW:
                if (CurrentModConfig.fireproof) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.fireproof"));
                }
                break;
            case LIGHT_BLUE:
                if (CurrentModConfig.fishy) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.fishy"));
                }
                break;
            case MAGENTA:
                if (CurrentModConfig.edible) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.edible"));
                }
                break;
            case ORANGE:
                if (CurrentModConfig.explosionproof) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.explosionproof"));
                }
                break;
            case WHITE:
                if (CurrentModConfig.despawnproof) {
                    tooltips.add(chatColor + ColoredRedstone.proxy.localize(Reference.MOD_ID + ".tooltip.despawnproof"));
                }
                break;
            default:
        }
        return tooltips;
    }

    default EntityItem createColoredEntityItem(World world, Entity oldEntityItem, ItemStack itemstack) {
        EntityItemColored newEntityItem = new EntityItemColored(world, oldEntityItem.posX, oldEntityItem.posY, oldEntityItem.posZ, itemstack);
        newEntityItem.motionX = oldEntityItem.motionX;
        newEntityItem.motionY = oldEntityItem.motionY;
        newEntityItem.motionZ = oldEntityItem.motionZ;
        newEntityItem.hoverStart = ((EntityItem) oldEntityItem).hoverStart;
        newEntityItem.lifespan = ((EntityItem) oldEntityItem).lifespan;
        try {
            Field field = ReflectionHelper.findField(EntityItem.class, "pickupDelay", "field_145804_b");
            newEntityItem.setPickupDelay((Integer) field.get(oldEntityItem));
        } catch (IllegalAccessException e) {
            ColoredRedstone.logger.error("Cannot get 'pickupDelay' value for ColoredEntityItem.", e);
            newEntityItem.setDefaultPickupDelay();
        }

        return newEntityItem;
    }

    default int getBurnTime(ItemStack itemStack) {
        if (CurrentModConfig.burnable && itemStack.getMetadata() == BURNABLE_COLOR.getMetadata()) {
            return CurrentModConfig.burnableBurningTime;
        }
        return 0;
    }

    default ActionResult<ItemStack> tryEatItem(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (CurrentModConfig.edible && playerIn.isSneaking() && playerIn.canEat(false)) {
            ItemStack heldItem = playerIn.getHeldItem(handIn);
            EnumColor color = EnumColor.byMetadata(heldItem.getMetadata());
            if (color == EDIBLE_COLOR) {
                playerIn.getFoodStats().addStats(CurrentModConfig.edibleHunger, CurrentModConfig.edibleSaturation);
                worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_PLAYER_BURP,
                        SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
                if (!worldIn.isRemote && worldIn.rand.nextInt(100) < CurrentModConfig.edibleSaturationChance) {
                    playerIn.addPotionEffect(new PotionEffect(MobEffects.SATURATION, CurrentModConfig.edibleSaturationDuration));
                }
                if (!worldIn.isRemote && worldIn.rand.nextInt(100) + 1 < CurrentModConfig.edibleNauseaChance) {
                    playerIn.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, CurrentModConfig.edibleNauseaDuration));
                }
                playerIn.addStat(StatList.getObjectUseStats(heldItem.getItem()));

                if (playerIn instanceof EntityPlayerMP) {
                    CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) playerIn, heldItem);
                }
                heldItem.shrink(1);
                return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
            }
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}
