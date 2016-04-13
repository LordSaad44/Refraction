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
public class Index extends GuiScreen {

    private GuiButton section1;
    int guiWidth = 146;
    int guiHeight = 180;

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(section1 = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "section 1"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == section1) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null)
                this.mc.setIngameFocus();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation(Refraction.MODID, "gui/physicsbook/book_main_template.png"));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
