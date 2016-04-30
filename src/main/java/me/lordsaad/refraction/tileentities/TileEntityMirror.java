package me.lordsaad.refraction.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import static net.minecraft.util.EnumFacing.*;

/**
 * Created by Saad on 3/24/2016.
 */
public class TileEntityMirror extends TileEntity {

    private int yaw = 0, pitch = 0;
    private int effectiveYaw = 0, effectivePitch = 0;

    public EnumFacing getEffectiveHorizontalDirection() {
        if (yaw < 0) return EAST;
        else if (yaw > 0) return WEST;
        else if (pitch < 0) return NORTH;
        else if (pitch > 0) return SOUTH;
        else return UP;
    }

    // The effective pitch is the correct pitch that light beams are drawn from
    public int getEffectivePitch() {
        return effectivePitch;
    }

    public void setEffectivePitch(int effectivePitch) {
        this.effectivePitch = effectivePitch;
    }

    public void addEffectivePitch(int amount) {
        if (effectivePitch == 90) effectivePitch = -89;
        if (effectivePitch == -90) effectivePitch = 89;
        if (effectivePitch < 90 && effectivePitch > -90) effectivePitch += amount;
    }

    public void subtractEffectivePitch(int amount) {
        if (effectivePitch == 90) effectivePitch = -89;
        if (effectivePitch == -90) effectivePitch = 89;
        if (effectivePitch < 90 && effectivePitch > -90) effectivePitch -= amount;
    }

    // The effective yaw is the correct yaw that light beams are drawn from
    public int getEffectiveYaw() {
        return effectiveYaw;
    }

    public void setEffectiveYaw(int effectiveYaw) {
        this.effectiveYaw = effectiveYaw;
    }

    public void addEffectiveYaw(int amount) {
        if (effectiveYaw == 180) effectiveYaw = -179;
        if (effectiveYaw == -180) effectiveYaw = 179;
        if (effectiveYaw < 180 && effectiveYaw > -180) effectiveYaw += amount;
    }

    public void subtractEffectiveYaw(int amount) {
        if (effectiveYaw == 180) effectiveYaw = -179;
        if (effectiveYaw == -180) effectiveYaw = 179;
        if (effectiveYaw < 180 && effectiveYaw > -180) effectiveYaw -= amount;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
        markDirty();
    }

    public void addPitch(int amount) {
        if (pitch == 90) pitch = -89;
        if (pitch == -90) pitch = 89;
        if (pitch < 90 && pitch > -90) pitch += amount;
        markDirty();
    }

    public void subtractPitch(int amount) {
        if (pitch == 90) pitch = -89;
        if (pitch == -90) pitch = 89;
        if (pitch < 90 && pitch > -90) pitch -= amount;
        markDirty();
    }

    public int getYaw() {
        return yaw;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
        markDirty();
    }

    public void addYaw(int amount) {
        if (yaw == 180) yaw = -179;
        if (yaw == -180) yaw = 179;
        if (yaw < 180 && yaw > -180) yaw += amount;
        markDirty();
    }

    public void subtractYaw(int amount) {
        if (yaw == 180) yaw = -179;
        if (yaw == -180) yaw = 179;
        if (yaw < 180 && yaw > -180) yaw -= amount;
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
        yaw = compound.getInteger("yaw");
        pitch = compound.getInteger("pitch");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("yaw", yaw);
        compound.setInteger("pitch", pitch);
    }
}