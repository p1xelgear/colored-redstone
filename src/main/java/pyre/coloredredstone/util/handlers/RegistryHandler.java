package pyre.coloredredstone.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.blocks.*;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModEntities;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.util.CustomStateMapper;
import pyre.coloredredstone.util.EnumColor;

@Mod.EventBusSubscriber
public class RegistryHandler {

    private static final String ITEM_VARIANT_INVENTORY = "inventory";
    private static final String SUB_ITEM_VARIANT_COLOR = "color=";

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityEntry> event){
        event.getRegistry().registerAll(ModEntities.ENTITY_ENTRIES.toArray(new EntityEntry[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        registerItemModels();
        registerItemFromBlockModels();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void setCustomStateMappers(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(ModBlocks.UNPOWERED_COLORED_REDSTONE_REPEATER, new CustomStateMapper("coloredredstone:unpowered_colored_redstone_repeater"));
        ModelLoader.setCustomStateMapper(ModBlocks.POWERED_COLORED_REDSTONE_REPEATER, new CustomStateMapper("coloredredstone:powered_colored_redstone_repeater"));
        ModelLoader.setCustomStateMapper(ModBlocks.UNPOWERED_COLORED_REDSTONE_COMPARATOR, new CustomStateMapper("coloredredstone:unpowered_colored_redstone_comparator"));
        ModelLoader.setCustomStateMapper(ModBlocks.POWERED_COLORED_REDSTONE_COMPARATOR, new CustomStateMapper("coloredredstone:powered_colored_redstone_comparator"));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColors(ColorHandlerEvent.Block event) {
        event.getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                BlockColoredRedstoneWire.colorMultiplier(state.getValue(BlockColoredRedstoneWire.POWER), state.getValue(BlockColoredRedstoneWire.COLOR)), ModBlocks.COLORED_REDSTONE_WIRE);
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityColoredRedstoneWire.class, ModBlocks.COLORED_REDSTONE_WIRE.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityColoredRedstoneTorch.class, ModBlocks.COLORED_REDSTONE_TORCH.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityColoredRedstoneRepeater.class, ModBlocks.UNPOWERED_COLORED_REDSTONE_REPEATER.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityColoredRedstoneComparator.class, ModBlocks.UNPOWERED_COLORED_REDSTONE_COMPARATOR.getRegistryName());
    }

    private static void registerItemModels() {
        for (Item item : ModItems.ITEMS) {
            if (!item.getHasSubtypes()){
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), ITEM_VARIANT_INVENTORY));
            } else {
                NonNullList<ItemStack> subItemList = NonNullList.create();
                item.getSubItems(CreativeTabs.REDSTONE, subItemList);
                for (ItemStack itemStack : subItemList){
                    ModelResourceLocation subItemModel = new ModelResourceLocation(item.getRegistryName(), SUB_ITEM_VARIANT_COLOR + EnumColor.getNameByMetadata(itemStack.getMetadata()));
                    ModelLoader.setCustomModelResourceLocation(item, itemStack.getMetadata(), subItemModel);
                }
            }
        }
    }

    private static void registerItemFromBlockModels() {
        for (Block block : ModBlocks.BLOCKS) {
            Item itemFromBlock = Item.getItemFromBlock(block);
            if (itemFromBlock != Items.AIR && !itemFromBlock.getHasSubtypes()){
                ModelLoader.setCustomModelResourceLocation(itemFromBlock, 0, new ModelResourceLocation(itemFromBlock.getRegistryName(), ITEM_VARIANT_INVENTORY));
            }
        }
    }
}
