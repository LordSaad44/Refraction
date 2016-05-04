package me.lordsaad.refraction.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

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
        initButtons();
    }

    private void initButtons() {
        buttonList.clear();
        Button BACK, NEXT, TOINDEX;
        buttonList.add(BACK = new Button(0, left, top * 3, 9, 18));
        buttonList.add(NEXT = new Button(1, left + 135, top * 3, 9, 18));
        buttonList.add(TOINDEX = new Button(2, left + 60, top * 3 + 10, 18, 18));

        ResourceLocation back = new ResourceLocation(Refraction.MODID, "textures/gui/arrows/back.png");
        ResourceLocation back_hover = new ResourceLocation(Refraction.MODID, "textures/gui/arrows/hover_back.png");
        ResourceLocation next = new ResourceLocation(Refraction.MODID, "textures/gui/arrows/next.png");
        ResourceLocation next_hover = new ResourceLocation(Refraction.MODID, "textures/gui/arrows/hover_next.png");
        ResourceLocation toIndex = new ResourceLocation(Refraction.MODID, "textures/gui/arrows/to_index.png");
        ResourceLocation toIndex_hover = new ResourceLocation(Refraction.MODID, "textures/gui/arrows/hover_to_index.png");
        regularTextures.put(TOINDEX, toIndex);
        regularTextures.put(BACK, back);
        regularTextures.put(NEXT, next);
        hoverTextures.put(TOINDEX, toIndex_hover);
        hoverTextures.put(BACK, back_hover);
        hoverTextures.put(NEXT, next_hover);
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
            default: {
                indexItems.stream().filter(item -> item.getButton() == button).forEach(item -> {
                    mc.thePlayer.openGui(Refraction.instance, item.getPageID(), mc.theWorld, (int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ);

                });
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);

        fontRendererObj.setUnicodeFlag(true);
        fontRendererObj.setBidiFlag(true);

        for (IndexItem item : indexItems) {
            GlStateManager.color(1F, 1F, 1F, 1F);
            int x = left + 20, y = top + 10 + ((item.getIndexID() - 4) * 15);
            mc.renderEngine.bindTexture(item.getIcon());

            item.getButton().xPosition = x;
            item.getButton().yPosition = y + 2;
            item.getButton().width = guiWidth;
            item.getButton().drawButton(mc, x, y);
            drawScaledCustomSizeModalRect(x, y, 0, 0, 15, 15, 15, 15, 15, 15);

            // TODO: tips not working here
            boolean inside = mouseX >= item.getButton().xPosition && mouseX < item.getButton().xPosition + item.getButton().width && mouseY >= item.getButton().yPosition && mouseY < item.getButton().yPosition + item.getButton().height;
            if (inside) {
                x += 3;
                fontRendererObj.drawString(" | " + ChatFormatting.ITALIC + item.getText().trim(), x + 17, y + fontRendererObj.FONT_HEIGHT / 2, 0);
                ID.put(item.getButton(), setTip(item.getTip().trim()));
            } else {
                if (ID.containsKey(item.getButton())) {
                    removeTip(ID.get(item.getButton()));
                    ID.remove(item.getButton());
                }
                fontRendererObj.drawString(" | " + item.getText().trim(), x + 17, y + fontRendererObj.FONT_HEIGHT / 2, 0);
            }
        }

        // redoing paging buttons because I'm not implementing everything GuiContent's drawScreen here.
       /* for (GuiButton button : buttonList) {
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
        }*/

        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect((width / 2) - 66, (float) (top - 20), 19, 182, 133, 14);
        fontRendererObj.setUnicodeFlag(false);
        fontRendererObj.setBidiFlag(false);
        fontRendererObj.drawString("Physics Book", (width / 2) - 30, (float) (top - 20) + 4, 0, false);
    }
}
