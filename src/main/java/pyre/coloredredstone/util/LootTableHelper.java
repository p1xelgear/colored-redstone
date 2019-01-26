package pyre.coloredredstone.util;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.util.exceptions.PrivateMethodInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class LootTableHelper {

    private static Method endermanGetLoot = ReflectionHelper.findMethod(EntityEnderman.class, "getLootTable", "func_184647_J");
    private static ResourceLocation fishingLootLocation = LootTableList.GAMEPLAY_FISHING;

    public static List<ItemStack> generateEndermanLoot(EntityPlayerMP player, World world) {
        ResourceLocation endermanLootLocation;
        try {
            EntityEnderman dummyEnderman = new EntityEnderman(world);
            endermanLootLocation = (ResourceLocation) endermanGetLoot.invoke(dummyEnderman);
        } catch (IllegalAccessException | InvocationTargetException e) {
            ColoredRedstone.logger.error("Cannot invoke 'getLootTable' method for LootTableHelper.", e);
            throw new PrivateMethodInvocationException("Cannot invoke 'getLootTable' method for LootTableHelper.", e);
        }
        LootTable lootTable = world.getLootTableManager().getLootTableFromLocation(endermanLootLocation);
        LootContext.Builder builder = new LootContext.Builder((WorldServer) world).withPlayer(player);
        return lootTable.generateLootForPools(world.rand, builder.build());
    }

    public static List<ItemStack> generateFishingLoot(EntityPlayerMP player, World world) {
        LootTable lootTable = world.getLootTableManager().getLootTableFromLocation(fishingLootLocation);
        LootContext.Builder builder = new LootContext.Builder((WorldServer) world).withPlayer(player);
        return lootTable.generateLootForPools(world.rand, builder.build());
    }
}
