package me.lordsaad.refraction.documentation;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

/**
 * Created by Saad on 4/13/2016.
 */
public class BookBase extends GuiScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Refraction.MODID, "textures/gui/book.png");

    int guiWidth = 146, guiHeight = 180, left = width / 2 - guiWidth / 2, top = height / 2 - guiHeight / 2;

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
