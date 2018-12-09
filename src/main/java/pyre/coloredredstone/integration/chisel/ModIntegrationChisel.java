package pyre.coloredredstone.integration.chisel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import pyre.coloredredstone.config.ModConfig;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.integration.ModIntegration;
import pyre.coloredredstone.util.EnumColor;

import java.util.Arrays;

public class ModIntegrationChisel extends ModIntegration {

    private static final String GROUP_REDSTONE_BLOCK = "redstone";

    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        if (ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks) {
            addColoredBlocksVariations();
        }
    }

    @Override
    public void postInit() {

    }

    public static void addColoredBlocksVariations() {
        Arrays.stream(EnumColor.values())
                .filter(color -> color != EnumColor.RED)
                .forEach(color -> addVariation(GROUP_REDSTONE_BLOCK,
                        ModBlocks.COLORED_REDSTONE_BLOCK.getStateFromMeta(color.getMetadata()),
                        new ItemStack(ModBlocks.COLORED_REDSTONE_BLOCK, 1, color.getMetadata())));
    }

    public static void removeColoredBlocksVariations() {
        Arrays.stream(EnumColor.values())
                .filter(color -> color != EnumColor.RED)
                .forEach(color -> removeVariation(GROUP_REDSTONE_BLOCK,
                        ModBlocks.COLORED_REDSTONE_BLOCK.getStateFromMeta(color.getMetadata()),
                        new ItemStack(ModBlocks.COLORED_REDSTONE_BLOCK, 1, color.getMetadata())));
    }

    private static void addVariation(String group, IBlockState state, ItemStack stack) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("group", group);
        tag.setTag("stack", stack.writeToNBT(new NBTTagCompound()));
        tag.setString("block", state.getBlock().getRegistryName().toString());
        tag.setInteger("meta", state.getBlock().getMetaFromState(state));
        FMLInterModComms.sendMessage(Mods.CHISEL.modId, "add_variation", tag);
    }

    private static void removeVariation(String group, IBlockState state, ItemStack stack) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("group", group);
        tag.setTag("stack", stack.writeToNBT(new NBTTagCompound()));
        tag.setString("block", state.getBlock().getRegistryName().toString());
        tag.setInteger("meta", state.getBlock().getMetaFromState(state));
        FMLInterModComms.sendMessage(Mods.CHISEL.modId, "remove_variation", tag);
    }
}
