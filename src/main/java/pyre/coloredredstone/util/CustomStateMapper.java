package pyre.coloredredstone.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

public class CustomStateMapper extends StateMapperBase {

    private String blockState;

    public CustomStateMapper(String blockState) {
        this.blockState = blockState;
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        return new ModelResourceLocation(blockState, this.getPropertyString(state.getProperties()));
    }
}
