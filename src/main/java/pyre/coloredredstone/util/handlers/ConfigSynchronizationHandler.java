package pyre.coloredredstone.util.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.config.ModConfig;
import pyre.coloredredstone.network.SyncConfigMessage;
import pyre.coloredredstone.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ConfigSynchronizationHandler {

    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public static void eventClientConnectedToServer(PlayerEvent.PlayerLoggedInEvent event){
        SyncConfigMessage msg = new SyncConfigMessage(ModConfig.waterproof, ModConfig.explosionproof, ModConfig.fireproof, ModConfig.despawnproof, ModConfig.cactusproof);
        NetworkHandler.INSTANCE.sendTo(msg, (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void eventClientDisconnectionFromServer﻿(FMLNetworkEvent.ClientDisconnectionFromServerEvent event){
        CurrentModConfig.useClientSettings();
    }
}
