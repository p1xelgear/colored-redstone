package pyre.coloredredstone.config;

import net.minecraftforge.common.config.Config;

public class IntegrationConfig {

    @Config.LangKey("coloredredstone.config.integration.chisel")
    @Config.Comment("Mod integration with Chisel Mod.")
    public ChiselIntegration chiselIntegration = new ChiselIntegration();

    public class ChiselIntegration {

        @Config.LangKey("coloredredstone.config.integration.chisel.redstone_blocks")
        @Config.Comment("If true, enables Colored Redstone Blocks to be used with Chisel.")
        @Config.RequiresMcRestart
        public boolean chiselRedstoneBlocks = true;
    }
}
