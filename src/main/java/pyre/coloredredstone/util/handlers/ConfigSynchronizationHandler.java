package pyre.coloredredstone.util.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.config.ModConfig;
import pyre.coloredredstone.network.ColoredPropertiesSyncConfigMessage;
import pyre.coloredredstone.network.IntegrationChiselSyncConfigMessage;
import pyre.coloredredstone.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ConfigSynchronizationHandler {

    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public static void eventClientConnectedToServer(PlayerEvent.PlayerLoggedInEvent event) {
        ColoredPropertiesSyncConfigMessage coloredPropertiesMessage = new ColoredPropertiesSyncConfigMessage(ModConfig.coloredPropertiesConfig.waterproof,
                ModConfig.coloredPropertiesConfig.explosionproof,
                ModConfig.coloredPropertiesConfig.fireproof,
                ModConfig.coloredPropertiesConfig.despawnproof,
                ModConfig.coloredPropertiesConfig.cactusproof);
        IntegrationChiselSyncConfigMessage chiselMessage = new IntegrationChiselSyncConfigMessage(ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks);
        NetworkHandler.INSTANCE.sendTo(coloredPropertiesMessage, (EntityPlayerMP) event.player);
        NetworkHandler.INSTANCE.sendTo(chiselMessage, (EntityPlayerMP) event.player); //TODO Sync not working (client restart necessary)
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void eventClientDisconnectionFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        CurrentModConfig.useClientSettings();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            if (Minecraft.getMinecraft().isSingleplayer() || Minecraft.getMinecraft().world == null) {
                CurrentModConfig.useClientSettings();
            }
        }
    }
}
