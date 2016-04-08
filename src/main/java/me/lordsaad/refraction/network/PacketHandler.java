package me.lordsaad.refraction.network;

import me.lordsaad.refraction.Refraction;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Saad on 4/8/2016.
 */
public class PacketHandler {
    private static int packetID = 0;
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Refraction.MODID);

    public PacketHandler() {
    }

    public static int incrementID() {
        return packetID++;
    }

    public static void registerMessages() {
        INSTANCE.registerMessage(PacketMirror.Handler.class, PacketMirror.class, incrementID(), Side.CLIENT);
    }

}
