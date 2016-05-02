package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Saad on 5/2/2016.
 */
public class GuiSubIndex extends GuiContentPage {

    public ArrayList<IndexItem> indexItems;

    @Override
    public void initGui() {
        super.initGui();
        indexItems = new ArrayList<>();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                if (currentPage > 0) {
                    currentPage--;
                    mc.thePlayer.openGui(Refraction.instance, pageID, mc.theWorld, (int) mc.thePlayer.posX, (int)
                            mc.thePlayer.posY, (int) mc.thePlayer.posZ);
                } else {
                    mc.thePlayer.openGui(Refraction.instance, GuiHandler.INDEX, mc.theWorld, (int) mc.thePlayer.posX, (int)
                            mc.thePlayer.posY, (int) mc.thePlayer.posZ);
                    currentPage = 0;
                }
                break;
            }
            case 1: {
                if (pages.size() > currentPage) {
                    currentPage++;
                    mc.thePlayer.openGui(Refraction.instance, pageID, mc.theWorld, (int) mc.thePlayer.posX, (int)
                            mc.thePlayer.posY, (int) mc.thePlayer.posZ);
                }
                break;
            }
            case 2: {
                mc.thePlayer.openGui(Refraction.instance, GuiHandler.INDEX, mc.theWorld, (int) mc.thePlayer.posX, (int)
                        mc.thePlayer.posY, (int) mc.thePlayer.posZ);
                currentPage = 0;
                break;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        fontRendererObj.setUnicodeFlag(true);
        fontRendererObj.setBidiFlag(true);

        GlStateManager.color(1F, 1F, 1F, 1F);

        for (IndexItem item : indexItems) {
            item.getButton().xPosition = left + 20;
            item.getButton().yPosition = top + item.getIndexID() * 10;
            mc.renderEngine.bindTexture(item.getIcon());
            item.getButton().drawButton(mc, left + 20, top + item.getIndexID() * 10);
            drawScaledCustomSizeModalRect(left + 10, top + item.getIndexID() * 10, 0, 0, 10, 10, 10, 10, 10, 10);
        }

        // redoing paging buttons because I'm not implementing everything GuiContent's drawScreen here.
        for (GuiButton button : buttonList) {
            boolean inside = mouseX >= button.xPosition && mouseX < button.xPosition + button.width && mouseY >= button.yPosition && mouseY < button.yPosition + button.height;
            if (inside) mc.renderEngine.bindTexture(hoverTextures.get(button));
            else mc.renderEngine.bindTexture(regularTextures.get(button));
            switch (button.id) {
                case 0:
                    button.drawButton(mc, left, top * 3);
                    drawScaledCustomSizeModalRect(left, top * 3, 0, 0, 10, 19, 10, 19, 10, 19);
                    break;
                case 1:
                    button.drawButton(mc, left + 135, top * 3);
                    drawScaledCustomSizeModalRect(left + 135, top * 3, 0, 0, 10, 19, 10, 19, 10, 19);
                    break;
                case 2:
                    button.drawButton(mc, left + 60, top * 3 + 10);
                    drawScaledCustomSizeModalRect(left + 60, top * 3 + 10, 0, 0, 19, 19, 19, 19, 19, 19);
                    break;
            }
        }

        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect((width / 2) - 66, (float) (top - 20), 19, 182, 133, 14);
        fontRendererObj.setUnicodeFlag(false);
        fontRendererObj.setBidiFlag(false);
        fontRendererObj.drawString("Physics Book", (width / 2) - 30, (float) (top - 20) + 4, 0, false);
    }
}
