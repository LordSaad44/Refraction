package me.lordsaad.refraction;

import me.lordsaad.refraction.gui.GuiHandler;
import me.lordsaad.refraction.network.PacketHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.PacketLoggingHandler;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Mod(modid = Refraction.MODID, version = Refraction.VERSION, name = Refraction.MODNAME, useMetadata = true)
public class Refraction {
    public static final String MODID = "refraction";
    public static final String MODNAME = "Refraction";
    public static final String VERSION = "1.0";
    public static PacketLoggingHandler packetHandler;
    public static ArrayList<ResourceLocation> icons = new ArrayList<>();
    public static ArrayList<ResourceLocation> hovered_icons = new ArrayList<>();
    public static ArrayList<ResourceLocation> pressed_icons = new ArrayList<>();

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
        CraftingRecipes.initCrafting();

        icons.add(new ResourceLocation(MODID, "textures/gui/icons/light-bulb.png"));
        icons.add(new ResourceLocation(MODID, "textures/gui/icons/laser-blast.png"));
        icons.add(new ResourceLocation(MODID, "textures/gui/icons/ringed-beam.png"));
        icons.add(new ResourceLocation(MODID, "textures/gui/icons/sun-radiations.png"));

        pressed_icons.add(new ResourceLocation(MODID, "textures/gui/icons/light-bulb-pressed.png"));
        pressed_icons.add(new ResourceLocation(MODID, "textures/gui/icons/laser-blast-pressed.png"));
        pressed_icons.add(new ResourceLocation(MODID, "textures/gui/icons/ringed-beam-pressed.png"));
        pressed_icons.add(new ResourceLocation(MODID, "textures/gui/icons/sun-radiations-pressed.png"));

        hovered_icons.add(new ResourceLocation(MODID, "textures/gui/icons/light-bulb-hover.png"));
        hovered_icons.add(new ResourceLocation(MODID, "textures/gui/icons/laser-blast-hover.png"));
        hovered_icons.add(new ResourceLocation(MODID, "textures/gui/icons/ringed-beam-hover.png"));
        hovered_icons.add(new ResourceLocation(MODID, "textures/gui/icons/sun-radiations-hover.png"));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    ///// SERVER  /////
    public static class CommonProxy {
        public void preInit(FMLPreInitializationEvent e) {
            PacketHandler.registerMessages();
            ModItems.init();
            ModBlocks.init();
        }

        public void init(FMLInitializationEvent e) {
            NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        }

        public void postInit(FMLPostInitializationEvent e) {
        }
    }

    ///// CLIENT  /////
    public static class ClientProxy extends CommonProxy {
        @Override
        public void preInit(FMLPreInitializationEvent e) {
            super.preInit(e);
            MinecraftForge.EVENT_BUS.register(this);

            OBJLoader.instance.addDomain(MODID);
            ModItems.initModels();
            ModBlocks.initModels();
        }

        @SubscribeEvent
        public void onTextureStitchEvent(TextureStitchEvent.Pre event) {
            event.map.registerSprite(new ResourceLocation(MODID + ":blocks/mirrorface"));
        }
    }

    public static class ServerProxy extends CommonProxy {
    }
}
