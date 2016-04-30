package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Saad on 4/19/2016.
 */
public class PageBase extends GuiScreen {

    static int guiWidth = 146, guiHeight = 180;
    static int left, top;
    static ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Refraction.MODID, "textures/gui/book.png");

    @Override
    public void initGui() {
        super.initGui();
        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        fontRendererObj.setUnicodeFlag(true);
        fontRendererObj.setBidiFlag(true);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
