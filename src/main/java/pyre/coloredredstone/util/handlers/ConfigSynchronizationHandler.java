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
import pyre.coloredredstone.network.InWorldRecoloringSyncConfigMessage;
import pyre.coloredredstone.network.IntegrationChiselSyncConfigMessage;
import pyre.coloredredstone.network.coloredproperties.*;
import pyre.coloredredstone.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ConfigSynchronizationHandler {

    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public static void eventClientConnectedToServer(PlayerEvent.PlayerLoggedInEvent event) {
        InWorldRecoloringSyncConfigMessage inWorldRecoloringMessage = new InWorldRecoloringSyncConfigMessage(ModConfig.inWorldRecoloring);

        WaterproofSyncMessage waterproofSyncMessage = new WaterproofSyncMessage(ModConfig.coloredPropertiesConfig.waterproof);
        ExplosionproofSyncMessage explosionproofSyncMessage = new ExplosionproofSyncMessage(ModConfig.coloredPropertiesConfig.explosionproof);
        FireproofSyncMessage fireproofSyncMessage = new FireproofSyncMessage(ModConfig.coloredPropertiesConfig.fireproof);
        DespawnproofSyncMessage despawnproofSyncMessage = new DespawnproofSyncMessage(ModConfig.coloredPropertiesConfig.despawnproof);
        CactusproofSyncMessage cactusproofSyncMessage = new CactusproofSyncMessage(ModConfig.coloredPropertiesConfig.cactusproof);
        BurnableSyncMessage burnableSyncMessage = new BurnableSyncMessage(ModConfig.coloredPropertiesConfig.burnable,
                ModConfig.coloredPropertiesConfig.burnableBurningTime,
                ModConfig.coloredPropertiesConfig.burnableCatchFire);
        SlimySyncMessage slimySyncMessage = new SlimySyncMessage(ModConfig.coloredPropertiesConfig.slimy,
                ModConfig.coloredPropertiesConfig.slimyChance);
        WitheringSyncMessage witheringSyncMessage = new WitheringSyncMessage(ModConfig.coloredPropertiesConfig.withering,
                ModConfig.coloredPropertiesConfig.witheringChance,
                ModConfig.coloredPropertiesConfig.witheringDuration);
        SluggishSyncMessage sluggishSyncMessage = new SluggishSyncMessage(ModConfig.coloredPropertiesConfig.sluggish,
                ModConfig.coloredPropertiesConfig.sluggishChance,
                ModConfig.coloredPropertiesConfig.sluggishDuration);
        SpeedySyncMessage speedySyncMessage = new SpeedySyncMessage(ModConfig.coloredPropertiesConfig.speedy,
                ModConfig.coloredPropertiesConfig.speedyChance,
                ModConfig.coloredPropertiesConfig.speedyDuration);
        HealthySyncMessage healthySyncMessage = new HealthySyncMessage(ModConfig.coloredPropertiesConfig.healthy,
                ModConfig.coloredPropertiesConfig.healthyChance,
                ModConfig.coloredPropertiesConfig.healthyDuration);
        AlienatedSyncMessage alienatedSyncMessage = new AlienatedSyncMessage(ModConfig.coloredPropertiesConfig.alienated,
                ModConfig.coloredPropertiesConfig.alienatedChance);
        FishySyncMessage fishySyncMessage = new FishySyncMessage(ModConfig.coloredPropertiesConfig.fishy,
                ModConfig.coloredPropertiesConfig.fishyChance);
        SoftSyncMessage softSyncMessage = new SoftSyncMessage(ModConfig.coloredPropertiesConfig.soft,
                ModConfig.coloredPropertiesConfig.softDamageReduction);
        EdibleSyncMessage edibleSyncMessage = new EdibleSyncMessage(ModConfig.coloredPropertiesConfig.edible,
                ModConfig.coloredPropertiesConfig.edibleHunger,
                ModConfig.coloredPropertiesConfig.edibleSaturation,
                ModConfig.coloredPropertiesConfig.edibleSaturationChance,
                ModConfig.coloredPropertiesConfig.edibleSaturationDuration,
                ModConfig.coloredPropertiesConfig.edibleNauseaChance,
                ModConfig.coloredPropertiesConfig.edibleNauseaDuration);

        IntegrationChiselSyncConfigMessage chiselMessage = new IntegrationChiselSyncConfigMessage(ModConfig.integrationConfig.chiselIntegration.chiselRedstoneBlocks);

        NetworkHandler.sendTo(inWorldRecoloringMessage, (EntityPlayerMP) event.player);

        NetworkHandler.sendTo(waterproofSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(explosionproofSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(fireproofSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(despawnproofSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(cactusproofSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(burnableSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(slimySyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(witheringSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(sluggishSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(speedySyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(healthySyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(alienatedSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(fishySyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(softSyncMessage, (EntityPlayerMP) event.player);
        NetworkHandler.sendTo(edibleSyncMessage, (EntityPlayerMP) event.player);

        NetworkHandler.sendTo(chiselMessage, (EntityPlayerMP) event.player); //Sync not working (client restart necessary)
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
