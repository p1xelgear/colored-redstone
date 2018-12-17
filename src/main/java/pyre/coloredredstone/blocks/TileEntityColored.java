package pyre.coloredredstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public abstract class TileEntityColored extends TileEntity {

    private static final String NBT_COLOR_TAG = "color";

    private EnumColor color;

    public TileEntityColored(EnumColor color) {
        this.color = color;
    }

    private void notifyBlockUpdate() {
        final IBlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        notifyBlockUpdate();
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (this.color != null) {
            int metadata = this.color.getMetadata();
            compound.setInteger(NBT_COLOR_TAG, metadata);
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey(NBT_COLOR_TAG)) {
            int colorMeta = compound.getInteger(NBT_COLOR_TAG);
            if (colorMeta >= 0 && colorMeta <= 15) {
                this.color = EnumColor.byMetadata(colorMeta);
            }
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        notifyBlockUpdate();
    }

    @Override
    public void onLoad() {
        setActualState();
    }

    public EnumColor getColor() {
        return color;
    }

    public void setColor(EnumColor color) {
        this.color = color;
        this.markDirty();
        setActualState();
    }

    @SuppressWarnings("deprecation")
    private void setActualState() {
        if (!world.isRemote) {
            IBlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            IBlockState actualState = block.getActualState(blockState, world, pos);
            world.setBlockState(pos, actualState, 3);
        }
    }
}
