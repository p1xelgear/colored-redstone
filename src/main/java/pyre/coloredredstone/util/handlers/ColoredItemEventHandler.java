package pyre.coloredredstone.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pyre.coloredredstone.blocks.*;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.items.IColoredItem;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.OreDictionaryUtils;

@Mod.EventBusSubscriber
public class ColoredItemEventHandler {

    @SubscribeEvent
    public static void preventDespawn(ItemExpireEvent event){
        ItemStack itemStack = event.getEntityItem().getItem();
        Item item = itemStack.getItem();
        if (item instanceof IColoredItem && itemStack.getMetadata() == EnumColor.WHITE.getMetadata()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void changeRedstoneTorchColor(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            ItemStack heldItemMainhand = event.getEntityPlayer().getHeldItemMainhand();
            if (heldItemMainhand.isEmpty()){
                return;
            }
            EnumColor dyeColor = OreDictionaryUtils.getDyeColor(heldItemMainhand);
            IBlockState blockState = event.getWorld().getBlockState(event.getPos());
            Block block = blockState.getBlock();

            if (dyeColor != null) {
                if ((block == Blocks.REDSTONE_TORCH || block == Blocks.UNLIT_REDSTONE_TORCH) && dyeColor != EnumColor.RED) {
                    IBlockState coloredTorchState;
                    if (block == Blocks.REDSTONE_TORCH) {
                        coloredTorchState = ModBlocks.COLORED_REDSTONE_TORCH.getDefaultState();
                    } else {
                        coloredTorchState = ModBlocks.UNLIT_COLORED_REDSTONE_TORCH.getDefaultState();
                    }
                    IBlockState stateToSet = coloredTorchState
                            .withProperty(BlockColoredRedstoneTorch.FACING, blockState.getValue(BlockRedstoneTorch.FACING))
                            .withProperty(BlockColoredRedstoneTorch.COLOR, dyeColor);
                    setBlockState(event, heldItemMainhand, 1, stateToSet, 3);
                } else if (block == ModBlocks.COLORED_REDSTONE_TORCH || block == ModBlocks.UNLIT_COLORED_REDSTONE_TORCH){
                    if (dyeColor != EnumColor.RED) {
                        setTileEntityColor(event, heldItemMainhand, dyeColor);
                    } else {
                        IBlockState coloredTorchState;
                        if (block == ModBlocks.COLORED_REDSTONE_TORCH) {
                            coloredTorchState = Blocks.REDSTONE_TORCH.getDefaultState();
                        } else {
                            coloredTorchState = Blocks.UNLIT_REDSTONE_TORCH.getDefaultState();
                        }
                        IBlockState stateToSet = coloredTorchState
                                .withProperty(BlockColoredRedstoneTorch.FACING, blockState.getValue(BlockRedstoneTorch.FACING));
                        setBlockState(event, heldItemMainhand, 1, stateToSet, 3);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void changeRedstoneBlockColor(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            ItemStack heldItemMainhand = event.getEntityPlayer().getHeldItemMainhand();
            if (heldItemMainhand.isEmpty() || heldItemMainhand.getCount() < 9){
                return;
            }
            EnumColor dyeColor = OreDictionaryUtils.getDyeColor(heldItemMainhand);
            IBlockState blockState = event.getWorld().getBlockState(event.getPos());
            Block block = blockState.getBlock();

            if (dyeColor != null) {
                if (dyeColor != EnumColor.RED &&
                        (block == Blocks.REDSTONE_BLOCK || (block == ModBlocks.COLORED_REDSTONE_BLOCK && dyeColor != blockState.getValue(BlockColoredRedstone.COLOR)))){
                    IBlockState stateToSet = ModBlocks.COLORED_REDSTONE_BLOCK
                            .getDefaultState()
                            .withProperty(BlockColoredRedstone.COLOR, dyeColor);
                    setBlockState(event, heldItemMainhand, 9, stateToSet, 2);
                } else if (block == ModBlocks.COLORED_REDSTONE_BLOCK && dyeColor == EnumColor.RED){
                    IBlockState stateToSet = Blocks.REDSTONE_BLOCK
                            .getDefaultState();
                    setBlockState(event, heldItemMainhand, 9, stateToSet, 2);
                }
            }
        }
    }

    @SubscribeEvent
    public static void changeRedstoneRepeaterColor(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntityPlayer().isSneaking()){
            changeTileEntityColor(event, BlockColoredRedstoneRepeater.class);
        }
    }

    @SubscribeEvent
    public static void changeRedstoneComparatorColor(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntityPlayer().isSneaking()){
            changeTileEntityColor(event, BlockColoredRedstoneComparator.class);
        }
    }

    @SubscribeEvent
    public static void changeRedstoneWireColor(PlayerInteractEvent.RightClickBlock event) {
        changeTileEntityColor(event, BlockColoredRedstoneWire.class);
    }

    private static void changeTileEntityColor(PlayerInteractEvent.RightClickBlock event, Class clazz) {
        if (!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            ItemStack heldItemMainhand = event.getEntityPlayer().getHeldItemMainhand();
            if (heldItemMainhand.isEmpty()){
                return;
            }
            EnumColor dyeColor = OreDictionaryUtils.getDyeColor(heldItemMainhand);
            Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

            if (clazz.isInstance(block) && dyeColor != null){
                setTileEntityColor(event, heldItemMainhand, dyeColor);
            }
        }
    }

    private static void setTileEntityColor(PlayerInteractEvent.RightClickBlock event, ItemStack heldItemMainhand, EnumColor dyeColor) {
        TileEntityColored tileEntity = (TileEntityColored) event.getWorld().getTileEntity(event.getPos());
        if (tileEntity.getColor() != dyeColor) {
            tileEntity.setColor(dyeColor);
            heldItemMainhand.shrink(1);
            event.setCanceled(true);
        }
    }

    private static void setBlockState(PlayerInteractEvent.RightClickBlock event, ItemStack heldItemMainhand, int shrink, IBlockState stateToSet, int flags) {
        event.getWorld().setBlockState(event.getPos(), stateToSet, flags);
        heldItemMainhand.shrink(shrink);
        event.setCanceled(true);
    }
}
