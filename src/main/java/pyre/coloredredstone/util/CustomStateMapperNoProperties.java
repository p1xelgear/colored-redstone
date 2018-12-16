package pyre.coloredredstone.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

public class CustomStateMapperNoProperties extends StateMapperBase {

    private String blockState;

    public CustomStateMapperNoProperties(String blockState) {
        this.blockState = blockState;
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        return new ModelResourceLocation(blockState, "");
    }
}
