package me.lordsaad.refraction.network;

import io.netty.buffer.ByteBuf;
import me.lordsaad.refraction.tileentities.TileEntityMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Saad on 4/8/2016.
 */
public class PacketMirror implements IMessage {

    private int padYaw, padPitch = 0;
    private int beamYaw, beamPitch = 0;
    private BlockPos pos;

    public PacketMirror() {
    }

    public PacketMirror(int padYaw, int padPitch, int beamYaw, int beamPitch, BlockPos pos) {
        this.padYaw = padYaw;
        this.padPitch = padPitch;
        this.beamYaw = beamYaw;
        this.beamPitch = beamPitch;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        padYaw = byteBuf.readInt();
        padPitch = byteBuf.readInt();
        beamYaw = byteBuf.readInt();
        beamPitch = byteBuf.readInt();
        pos = new BlockPos(byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt());
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(padYaw);
        byteBuf.writeInt(padPitch);
        byteBuf.writeInt(beamYaw);
        byteBuf.writeInt(beamPitch);
        byteBuf.writeInt(pos.getX());
        byteBuf.writeInt(pos.getY());
        byteBuf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<PacketMirror, IMessage> {

        @Override
        public IMessage onMessage(PacketMirror mirror, MessageContext messageContext) {
            int padYaw = mirror.padYaw;
            int padPitch = mirror.padPitch;
            int beamYaw = mirror.beamYaw;
            int beamPitch = mirror.beamPitch;
            BlockPos pos = mirror.pos;
            TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(pos);
            if (tileEntity instanceof TileEntityMirror) {
                TileEntityMirror tileEntityMirror = (TileEntityMirror) tileEntity;
                tileEntityMirror.setPadYaw(padYaw);
                tileEntityMirror.setPadPitch(padPitch);
                tileEntityMirror.setBeamYaw(beamYaw);
                tileEntityMirror.setBeamPitch(beamPitch);
            }
            return null;
        }
    }
}
