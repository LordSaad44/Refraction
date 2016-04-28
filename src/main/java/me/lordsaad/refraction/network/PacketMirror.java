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

    private int yaw, pitch = -90;
    private BlockPos pos;

    public PacketMirror() {
    }

    public PacketMirror(int yaw, int pitch, BlockPos pos) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        yaw = byteBuf.readInt();
        pitch = byteBuf.readInt();
        pos = new BlockPos(byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt());
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(yaw);
        byteBuf.writeInt(pitch);
        byteBuf.writeInt(pos.getX());
        byteBuf.writeInt(pos.getY());
        byteBuf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<PacketMirror, IMessage> {

        @Override
        public IMessage onMessage(PacketMirror mirror, MessageContext messageContext) {
            int yaw = mirror.yaw;
            int pitch = mirror.pitch;
            BlockPos pos = mirror.pos;
            TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(pos);
            if (tileEntity instanceof TileEntityMirror) {
                TileEntityMirror tileEntityMirror = (TileEntityMirror) tileEntity;
                tileEntityMirror.setYaw(yaw);
                tileEntityMirror.setPitch(pitch);
            }
            return null;
        }
    }
}
