package pyre.coloredredstone.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.util.EnumColor;

import java.util.Random;

public interface IColoredFeatures {
    float EXPLOSION_PROOF_BLOCK_RESISTANCE = 6000.0F;

    EnumColor EXPLOSION_PROOF_COLOR = EnumColor.ORANGE;
    EnumColor CACTUS_PROOF_COLOR = EnumColor.GREEN;
    EnumColor WATERPROOF_COLOR = EnumColor.BLUE;
    EnumColor FIREPROOF_COLOR = EnumColor.YELLOW;
    EnumColor DESPAWN_PROOF_COLOR = EnumColor.WHITE;
    EnumColor BURNABLE_COLOR = EnumColor.BROWN;
    EnumColor SLIMY_COLOR = EnumColor.LIME;
    EnumColor WITHERING_COLOR = EnumColor.BLACK;

    default void withering(World worldIn, Entity entityIn, EnumColor color) {
        if (CurrentModConfig.withering && !worldIn.isRemote && entityIn instanceof EntityLivingBase && (worldIn.getWorldTime() % 20 == 0) && color.equals(WITHERING_COLOR)) {
            int i = new Random().nextInt(100) + 1;
            if (i < CurrentModConfig.witheringChance) {
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, CurrentModConfig.witheringDuration));
            }
        }
    }
}
