package pyre.coloredredstone.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pyre.coloredredstone.util.EnumColor;

import javax.annotation.Nullable;

public class TileEntityColoredRedstoneWire extends TileEntity {

    private EnumColor color;

    public TileEntityColoredRedstoneWire() {
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT( new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (this.color != null){
            int metadata = this.color.getMetadata();
            compound.setInteger("color", metadata);
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("color")){
            int colorMeta = compound.getInteger("color");
            if (colorMeta >= 0 && colorMeta <= 15){
                this.color = EnumColor.byMetadata(colorMeta);
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public EnumColor getColor() {
        return color;
    }

    public void setColor(EnumColor color) {
        this.color = color;
        this.markDirty();
    }
}