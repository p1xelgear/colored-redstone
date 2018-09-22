package pyre.coloredredstone.util.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.util.EnumColor;

@Mod.EventBusSubscriber
public class ColoredItemEventHandler {

    @SubscribeEvent
    public static void preventDespawn(ItemExpireEvent event){
        ItemStack itemStack = event.getEntityItem().getItem();
        if (itemStack.getItem() == ModItems.COLORED_REDSTONE_DUST || itemStack.getItem() == ModItems.COLORED_REDSTONE_BLOCK){
            if (itemStack.getItem().getMetadata(itemStack) == EnumColor.WHITE.getMetadata()){
                event.setCanceled(true);
            }
        }
    }
}
