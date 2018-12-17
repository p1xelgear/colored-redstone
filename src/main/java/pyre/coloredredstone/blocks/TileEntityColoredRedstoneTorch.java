package pyre.coloredredstone.blocks;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import pyre.coloredredstone.config.ModConfig;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.util.Colors;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;

@Optional.Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
public class TileEntityColoredRedstoneTorch extends TileEntityColored implements ILightProvider {

    public TileEntityColoredRedstoneTorch() {
        super(EnumColor.BLACK);
    }

    public TileEntityColoredRedstoneTorch(EnumColor color) {
        super(color);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return !(newState.getBlock() instanceof BlockColoredRedstoneTorch);
    }

    @Optional.Method(modid = "albedo")
    @Nullable
    @Override
    public Light provideLight() {
        if (ModConfig.integrationConfig.albedoIntegration.albedoRedstoneTorches &&
                world.getBlockState(pos).getBlock() == ModBlocks.COLORED_REDSTONE_TORCH) {
            Colors.RGBColor rgbColor = getColor().getShades()[2];
            float r = rgbColor.getR() / 255.0f;
            float g = rgbColor.getG() / 255.0f;
            float b = rgbColor.getB() / 255.0f;
            return Light.builder().pos(pos).color(r, g, b).radius(5.0f).build();
        }
        return null;
    }
}
