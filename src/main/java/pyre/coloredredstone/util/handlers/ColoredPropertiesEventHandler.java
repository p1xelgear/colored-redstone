package pyre.coloredredstone.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pyre.coloredredstone.blocks.IBlockColored;
import pyre.coloredredstone.blocks.IBlockColoredWithoutRed;
import pyre.coloredredstone.blocks.IColoredFeatures;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.items.IColoredItem;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.LootTableHelper;

import java.util.List;
import java.util.Random;

import static pyre.coloredredstone.blocks.IColoredFeatures.*;

@Mod.EventBusSubscriber
public class ColoredPropertiesEventHandler {

    @SubscribeEvent
    public static void preventDespawn(ItemExpireEvent event) {
        if (CurrentModConfig.despawnproof) {
            ItemStack itemStack = event.getEntityItem().getItem();
            Item item = itemStack.getItem();
            if (item instanceof IColoredItem && itemStack.getMetadata() == DESPAWN_PROOF_COLOR.getMetadata()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void alienatedLoot(BlockEvent.HarvestDropsEvent event) {
        if (CurrentModConfig.alienated && hasChance(CurrentModConfig.alienatedChance)) {
            World world = event.getWorld();
            EntityPlayer player = event.getHarvester();
            BlockPos pos = event.getPos();
            if (!world.isRemote && player instanceof EntityPlayerMP && isCorrectColor(world, pos, ALIENATED_COLOR)) {
                List<ItemStack> loot = LootTableHelper.generateEndermanLoot((EntityPlayerMP) player, world);
                updateDrops(event, loot);
            }
        }
    }

    @SubscribeEvent
    public static void fishyLoot(BlockEvent.HarvestDropsEvent event) {
        if (CurrentModConfig.fishy && hasChance(CurrentModConfig.fishyChance)) {
            World world = event.getWorld();
            EntityPlayer player = event.getHarvester();
            BlockPos pos = event.getPos();
            if (!world.isRemote && player instanceof EntityPlayerMP && isCorrectColor(world, pos, FISHY_COLOR)) {
                List<ItemStack> loot = LootTableHelper.generateFishingLoot((EntityPlayerMP) player, world);
                updateDrops(event, loot);
            }
        }
    }

    @SubscribeEvent
    public static void spawnSlime(BlockEvent.BreakEvent event) {
        if (CurrentModConfig.slimy && hasChance(CurrentModConfig.slimyChance)) {
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            if (isCorrectColor(world, pos, SLIMY_COLOR)) {
                EntitySlime entitySlime = new EntitySlime(world);
                entitySlime.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                world.spawnEntity(entitySlime);
            }
        }
    }

    //workaround for not solid/full blocks
    @SubscribeEvent
    public static void softFall(LivingFallEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();
        Block block = world.getBlockState(pos).getBlock();

        if (block instanceof IColoredFeatures && CurrentModConfig.soft && isCorrectColor(world, pos, SOFT_COLOR) && !entity.isSneaking()) {
            float damageMultiplier = 1.0F - (CurrentModConfig.softDamageReduction / 100.0F);
            event.setDamageMultiplier(damageMultiplier);
        }
    }

    private static boolean isCorrectColor(World world, BlockPos pos, EnumColor color) {
        Block block = world.getBlockState(pos).getBlock();
        return (block instanceof IBlockColored && ((IBlockColored) block).getColor(world, pos) == color) ||
                (block instanceof IBlockColoredWithoutRed && ((IBlockColoredWithoutRed) block).getColor(world, pos) == color);
    }

    private static boolean hasChance(int chance) {
        int i = new Random().nextInt(100) + 1;
        return i < chance;
    }

    private static void updateDrops(BlockEvent.HarvestDropsEvent event, List<ItemStack> loot) {
        if (!loot.isEmpty()) {
            List<ItemStack> drops = event.getDrops();
            drops.clear();
            drops.addAll(loot);
        }
    }
}
