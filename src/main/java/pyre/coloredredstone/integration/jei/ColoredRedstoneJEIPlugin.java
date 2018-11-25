package pyre.coloredredstone.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import pyre.coloredredstone.init.ModItems;

@JEIPlugin
public class ColoredRedstoneJEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        registry.addIngredientInfo(new ItemStack(Items.REDSTONE), VanillaTypes.ITEM, "jei.description.colored_dust", "jei.description.in_world_recoloring", "jei.description.colored_dust_requirements");
        registry.addIngredientInfo(new ItemStack(Blocks.REDSTONE_BLOCK), VanillaTypes.ITEM, "jei.description.colored_block", "jei.description.in_world_recoloring", "jei.description.colored_block_requirements");
        registry.addIngredientInfo(new ItemStack(Blocks.REDSTONE_TORCH), VanillaTypes.ITEM, "jei.description.colored_torch", "jei.description.in_world_recoloring", "jei.description.colored_torch_requirements");
        registry.addIngredientInfo(new ItemStack(Items.REPEATER), VanillaTypes.ITEM, "jei.description.colored_repeater", "jei.description.in_world_recoloring_with_shift", "jei.description.colored_repeater_requirements");
        registry.addIngredientInfo(new ItemStack(Items.COMPARATOR), VanillaTypes.ITEM, "jei.description.colored_comparator", "jei.description.in_world_recoloring_with_shift", "jei.description.colored_comparator_requirements");

        registry.addIngredientInfo(new ItemStack(ModItems.COLORED_REDSTONE_DUST, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, "jei.description.colored_dust", "jei.description.in_world_recoloring", "jei.description.colored_dust_requirements");
        registry.addIngredientInfo(new ItemStack(ModItems.COLORED_REDSTONE_BLOCK, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, "jei.description.colored_block", "jei.description.in_world_recoloring", "jei.description.colored_block_requirements");
        registry.addIngredientInfo(new ItemStack(ModItems.COLORED_REDSTONE_TORCH, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, "jei.description.colored_torch", "jei.description.in_world_recoloring", "jei.description.colored_torch_requirements");
        registry.addIngredientInfo(new ItemStack(ModItems.COLORED_REDSTONE_REPEATER, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, "jei.description.colored_repeater", "jei.description.in_world_recoloring_with_shift", "jei.description.colored_repeater_requirements");
        registry.addIngredientInfo(new ItemStack(ModItems.COLORED_REDSTONE_COMPARATOR, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, "jei.description.colored_comparator", "jei.description.in_world_recoloring_with_shift", "jei.description.colored_comparator_requirements");
    }
}
