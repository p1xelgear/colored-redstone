package pyre.coloredredstone.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.util.EnumColor;

public interface IColoredFeatures {
    float EXPLOSION_PROOF_BLOCK_RESISTANCE = 6000.0F;
    int BURNABLE_FLAMMABILITY = 20;
    int BURNABLE_FIRE_SPREAD_SPEED = 5;

    EnumColor EXPLOSION_PROOF_COLOR = EnumColor.ORANGE;
    EnumColor CACTUS_PROOF_COLOR = EnumColor.GREEN;
    EnumColor WATERPROOF_COLOR = EnumColor.BLUE;
    EnumColor FIREPROOF_COLOR = EnumColor.YELLOW;
    EnumColor DESPAWN_PROOF_COLOR = EnumColor.WHITE;
    EnumColor BURNABLE_COLOR = EnumColor.BROWN;
    EnumColor SLIMY_COLOR = EnumColor.LIME;
    EnumColor WITHERING_COLOR = EnumColor.BLACK;
    EnumColor SLUGGISH_COLOR = EnumColor.GRAY;
    EnumColor SPEEDY_COLOR = EnumColor.LIGHT_GRAY;
    EnumColor HEALTHY_COLOR = EnumColor.PINK;

    default void withering(World worldIn, Entity entityIn) {
        if (CurrentModConfig.withering) {
            int i = worldIn.rand.nextInt(100) + 1;
            if (i < CurrentModConfig.witheringChance) {
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, CurrentModConfig.witheringDuration));
            }
        }
    }

    default void sluggish(World worldIn, Entity entityIn) {
        if (CurrentModConfig.sluggish) {
            int i = worldIn.rand.nextInt(100) + 1;
            if (i < CurrentModConfig.sluggishChance) {
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, CurrentModConfig.sluggishDuration));
            }
        }
    }

    default void speedy(World worldIn, Entity entityIn) {
        if (CurrentModConfig.speedy) {
            int i = worldIn.rand.nextInt(100) + 1;
            if (i < CurrentModConfig.speedyChance) {
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SPEED, CurrentModConfig.speedyDuration));
            }
        }
    }

    default void healthy(World worldIn, Entity entityIn) {
        if (CurrentModConfig.healthy) {
            int i = worldIn.rand.nextInt(100) + 1;
            if (i < CurrentModConfig.healthyChance) {
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, CurrentModConfig.healthyDuration));
            }
        }
    }
}
