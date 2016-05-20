package me.lordsaad.refraction.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Saad on 3/24/2016.
 */
public class TileEntityMirror extends TileEntity {

    private int padYaw = 0, padPitch = 0;
    private int beamPitch = 0, beamYaw = 0;

    public int getBeamPitch() {
        return beamPitch;
    }

    public void setBeamPitch(int beamPitch) {
        this.beamPitch = beamPitch;
    }

    public int getBeamYaw() {
        return beamYaw;
    }

    public void setBeamYaw(int beamYaw) {
        this.beamYaw = beamYaw;
    }

    public void addPadPitch(int amount) {
        if (padPitch == 90) padPitch = -89;
        if (padPitch == -90) padPitch = 89;
        if (padPitch < 90 && padPitch > -90) {
            padPitch += amount;
        }
        markDirty();
    }

    public void subtractPadPitch(int amount) {
        if (padPitch == 90) padPitch = -89;
        if (padPitch == -90) padPitch = 89;
        if (padPitch < 90 && padPitch > -90) {
            padPitch -= amount;
        }
        markDirty();
    }

    public void addPadYaw(int amount) {
        if (padYaw == 180) padYaw = -179;
        if (padYaw == -180) padYaw = 179;
        if (padYaw < 180 && padYaw > -180) padYaw += amount;

        markDirty();
    }

    public void subtractPadYaw(int amount) {
        if (padYaw == 180) padYaw = -179;
        if (padYaw == -180) padYaw = 179;
        if (padYaw < 180 && padYaw > -180) padYaw -= amount;

        markDirty();
    }

    public int getPadPitch() {
        return padPitch;
    }

    public void setPadPitch(int pitch) {
        this.padPitch = pitch;
        markDirty();
    }

    public int getPadYaw() {
        return padYaw;
    }

    public void setPadYaw(int yaw) {
        this.padYaw = yaw;
        markDirty();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        padYaw = compound.getInteger("padYaw");
        padPitch = compound.getInteger("padPitch");
        padPitch = compound.getInteger("beamYaw");
        padPitch = compound.getInteger("beamPitch");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("padYaw", padYaw);
        compound.setInteger("padPitch", padPitch);
        compound.setInteger("beamPitch", padPitch);
        compound.setInteger("beamYaw", padPitch);
    }
}