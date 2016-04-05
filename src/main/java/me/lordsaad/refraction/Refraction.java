package me.lordsaad.refraction;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Refraction.MODID, version = Refraction.VERSION, name = Refraction.MODNAME, useMetadata = true)
public class Refraction {
    public static final String MODID = "refraction";
    public static final String MODNAME = "Refraction";
    public static final String VERSION = "1.0";

    @SidedProxy
    public static CommonProxy proxy;


    @Mod.Instance
    public static Refraction instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    public static class CommonProxy {
        public void preInit(FMLPreInitializationEvent e) {
            Blocks.init();
        }

        public void init(FMLInitializationEvent e) {
        }

        public void postInit(FMLPostInitializationEvent e) {
        }
    }


    public static class ClientProxy extends CommonProxy {
        @Override
        public void preInit(FMLPreInitializationEvent e) {
            super.preInit(e);

            OBJLoader.instance.addDomain(MODID);
            Blocks.initModels();
        }
    }

    public static class ServerProxy extends CommonProxy {
    }
}
