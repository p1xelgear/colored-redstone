package pyre.coloredredstone.config;

import net.minecraftforge.common.config.Config;

public class IntegrationConfig {

    @Config.LangKey("coloredredstone.config.integration.chisel")
    @Config.Comment("Mod integration with Chisel Mod.")
    public ChiselIntegration chiselIntegration = new ChiselIntegration();

    @Config.LangKey("coloredredstone.config.integration.albedo")
    @Config.Comment("Mod integration with Albedo Mod.")
    public AlbedoIntegration albedoIntegration = new AlbedoIntegration();

    public class ChiselIntegration {

        @Config.LangKey("coloredredstone.config.integration.chisel.redstone_blocks")
        @Config.Comment("If true, enables Colored Redstone Blocks to be used with Chisel.")
        @Config.RequiresMcRestart
        public boolean chiselRedstoneBlocks = true;
    }

    public class AlbedoIntegration {

        @Config.LangKey("coloredredstone.config.integration.albedo.redstone_torches")
        @Config.Comment("If true, enables colored lighting for Colored Redstone Torches.")
        public boolean albedoRedstoneTorches = true;

        @Config.LangKey("coloredredstone.config.integration.albedo.redstone_lamps")
        @Config.Comment("If true, enables colored lighting for Colored Redstone Lamps.")
        public boolean albedoRedstoneLamps = true;
    }
}
