package me.lordsaad.refraction.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Saad on 3/24/2016.
 */
public class TileEntityMirror extends TileEntity {

    private int yaw = 0, pitch = 0;

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
    }

    public void subtractPitch(int amount) {
        if (pitch == 90) pitch = -89;
        if (pitch == -90) pitch = 89;
        if (pitch < 90 && pitch > -90) pitch -= amount;
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
    }

    public void subtractYaw(int amount) {
        if (yaw == 180) yaw = -179;
        if (yaw == -180) yaw = 179;
        if (yaw < 180 && yaw > -180) yaw -= amount;
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