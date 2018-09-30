package pyre.coloredredstone.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pyre.coloredredstone.blocks.BlockColoredRedstoneTorch;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.OreDictionaryUtils;

@Mod.EventBusSubscriber
public class ColoredItemEventHandler {

    @SubscribeEvent
    public static void preventDespawn(ItemExpireEvent event){
        ItemStack itemStack = event.getEntityItem().getItem();
        if (itemStack.getItem() == ModItems.COLORED_REDSTONE_DUST || itemStack.getItem() == ModItems.COLORED_REDSTONE_BLOCK || itemStack.getItem() == ModItems.COLORED_REDSTONE_TORCH){
            if (itemStack.getItem().getMetadata(itemStack) == EnumColor.WHITE.getMetadata()){
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void changeVanillaRedstoneTorchColor(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote) {
            ItemStack heldItemMainhand = event.getEntityPlayer().getHeldItemMainhand();
            if (heldItemMainhand.isEmpty()){
                return;
            }
            EnumColor dyeColor = OreDictionaryUtils.getDyeColor(heldItemMainhand);
            IBlockState blockState = event.getWorld().getBlockState(event.getPos());
            Block block = blockState.getBlock();

            if ((block == Blocks.REDSTONE_TORCH || block == Blocks.UNLIT_REDSTONE_TORCH) && dyeColor != null && dyeColor != EnumColor.RED){
                IBlockState coloredTorchState;
                if (block == Blocks.REDSTONE_TORCH){
                    coloredTorchState = ModBlocks.COLORED_REDSTONE_TORCH.getDefaultState();
                } else {
                    coloredTorchState = ModBlocks.UNLIT_COLORED_REDSTONE_TORCH.getDefaultState();
                }
                IBlockState stateToSet = coloredTorchState.withProperty(BlockColoredRedstoneTorch.FACING, blockState.getValue(BlockRedstoneTorch.FACING)).withProperty(BlockColoredRedstoneTorch.COLOR, dyeColor);
                event.getWorld().setBlockState(event.getPos(), stateToSet, 3);
                heldItemMainhand.shrink(1);
                event.setCanceled(true);
            }
        }
    }
}
