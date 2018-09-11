package pyre.coloredredstone.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLogic;

public class MaterialLogicWaterproof extends MaterialLogic {

    public MaterialLogicWaterproof(MapColor color) {
        super(color);
        this.setNoPushMobility();
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }
}
