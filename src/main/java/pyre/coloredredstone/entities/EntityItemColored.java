package pyre.coloredredstone.entities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import pyre.coloredredstone.util.EnumColor;

public class EntityItemColored extends EntityItem {

    public EntityItemColored(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityItemColored(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    public EntityItemColored(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        ItemStack item = this.getItem();

        if (isItemFireproof(item) && source.isFireDamage()){
            return false;
        }
        if (isItemExplosionproof(item) && source.isExplosion()){
            return false;
        }
        if (isItemCactusproof(item) && source == DamageSource.CACTUS){
            return false;
        }

        return super.attackEntityFrom(source, amount);
    }

    private boolean isItemFireproof(ItemStack item) {
        return item.getMetadata() == EnumColor.YELLOW.getMetadata();
    }

    private boolean isItemExplosionproof(ItemStack item) {
        return item.getMetadata() == EnumColor.ORANGE.getMetadata();
    }

    private boolean isItemCactusproof(ItemStack item) {
        return item.getMetadata() == EnumColor.GREEN.getMetadata();
    }
}
