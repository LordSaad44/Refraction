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

    public PacketMirror() {
    }

    private int angle;
    private BlockPos pos;

    public PacketMirror(int angle, BlockPos pos) {
        this.angle = angle;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        angle = byteBuf.readInt();
        pos = new BlockPos(byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt());
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(angle);
        byteBuf.writeInt(pos.getX());
        byteBuf.writeInt(pos.getY());
        byteBuf.writeInt(pos.getZ());

    }

    public static class Handler implements IMessageHandler<PacketMirror, IMessage> {

        @Override
        public IMessage onMessage(PacketMirror mirror, MessageContext messageContext) {
            int angle = mirror.angle;
            BlockPos pos = mirror.pos;
            TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(pos);
            if (tileEntity instanceof TileEntityMirror) {
                TileEntityMirror tileEntityMirror = (TileEntityMirror) tileEntity;
                tileEntityMirror.setAngle(angle);
            }
            return null;
        }
    }
}
