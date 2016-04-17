package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Saad on 4/13/2016.
 */
public class BookBase extends GuiScreen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Refraction.MODID, "textures/gui/book.png");
    private static final ResourceLocation SLIDER_TEXTURES = new ResourceLocation(Refraction.MODID, "textures/gui/sliders.png");

    static int guiWidth = 146, guiHeight = 180;
    static int left, top, tipLoc = 0;
    private boolean isIndex = true;
    private boolean hovering_basics, hovering_items, hovering_beam, hovering_energy;

    private GuiButton BASICS, ITEMS, BEAM_MANIPULATION, ENERGY;

    @Override
    public void initGui() {
        super.initGui();
        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;
        setIndex(true);
    }

    private void initIndexButtons() {
        buttonList.add(BASICS = new GuiButtonCategory(0, left + 25, top + 20, 25, 25, "BASICS"));
        buttonList.add(ITEMS = new GuiButtonCategory(1, left + 55, top + 20, 25, 25, "ITEMS"));
        buttonList.add(BEAM_MANIPULATION = new GuiButtonCategory(2, left + 90, top + 20, 25, 25, "BEAM_MANIPULATION"));
        buttonList.add(ENERGY = new GuiButtonCategory(3, left + 25, top + 60, 25, 25, "ENERGY"));
    }

    public boolean isIndex() {
        return isIndex;
    }

    public void setIndex(boolean isIndex) {
        buttonList.clear();
        this.isIndex = isIndex;
        if (isIndex) initIndexButtons();
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            setIndex(false);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!GuiTip.getTips().isEmpty()) {
            int y1 = 0, y2 = 0;
            if (GuiTip.getTips().size() == 1) {
                y1 = 0;
                y2 = 12;
            } else if (GuiTip.getTips().size() == 2) {
                y1 = 14;
                y2 = 37;
            } else if (GuiTip.getTips().size() == 3) {
                y1 = 39;
                y2 = 72;
            } else if (GuiTip.getTips().size() == 4) {
                y1 = 74;
                y2 = 118;
            } else if (GuiTip.getTips().size() == 5) {
                y1 = 120;
                y2 = 174;
            } else if (GuiTip.getTips().size() == 6) {
                y1 = 176;
                y2 = 240;
            }

            if (hovering_energy || hovering_basics || hovering_beam || hovering_items) {
                if (tipLoc > -145) {
                    tipLoc -= (145 - tipLoc) / 10;

                    GlStateManager.color(1F, 1F, 1F, 1F);
                    mc.renderEngine.bindTexture(SLIDER_TEXTURES);
                    drawTexturedModalRect(tipLoc, (height + top) / 2, 0, y1, 145, y2);

                    mc.thePlayer.addChatComponentMessage(new TextComponentString(tipLoc + ""));
                    for (String tip : GuiTip.getTips()) {
                        FontRenderer fontRenderer = fontRendererObj;
                        fontRenderer.setUnicodeFlag(true);
                        fontRenderer.setBidiFlag(true);
                        fontRenderer.drawString(tip, tipLoc + 5, top + 72, 0, false);
                    }
                }
            } else {
                if (tipLoc < 0) {
                    tipLoc += (tipLoc - 145) / 10;

                    GlStateManager.color(1F, 1F, 1F, 1F);
                    mc.renderEngine.bindTexture(SLIDER_TEXTURES);
                    drawTexturedModalRect(left + tipLoc, top + 70, 0, y1, 145, y2);

                    mc.thePlayer.addChatComponentMessage(new TextComponentString(tipLoc + ""));
                    for (String tip : GuiTip.getTips()) {
                        FontRenderer fontRenderer = fontRendererObj;
                        fontRenderer.setUnicodeFlag(true);
                        fontRenderer.setBidiFlag(true);
                        fontRenderer.drawString(tip, left + tipLoc + 5, top + 72, 0, false);
                    }
                    if (tipLoc == 145 || tipLoc == 0) GuiTip.getTips().clear();
                }
            }
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
        if (isIndex) {
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0: {
                        boolean inside = mouseX >= BASICS.xPosition && mouseX < BASICS.xPosition + BASICS.width && mouseY >= BASICS.yPosition && mouseY < BASICS.yPosition + BASICS.height;
                        if (inside) {
                            hovering_basics = true;
                            GuiTip.setTip("Learn the basics of light manipulation and how everything works");
                            BASICS.drawButton(mc, left + 55, top + 20);
                            mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                            drawScaledCustomSizeModalRect(left + 25, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        } else {
                            hovering_basics = false;
                            BASICS.drawButton(mc, left + 55, top + 20);
                            mc.renderEngine.bindTexture(Refraction.icons.get(i));
                            drawScaledCustomSizeModalRect(left + 25, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        }
                        break;
                    }
                    case 1: {
                        boolean inside = mouseX >= ITEMS.xPosition && mouseX < ITEMS.xPosition + ITEMS.width && mouseY >= ITEMS.yPosition && mouseY < ITEMS.yPosition + ITEMS.height;
                        if (inside) {

                            ArrayList<String> tips = new ArrayList<>();
                            tips.add("Read what each item and");
                            tips.add("block in this mod does");
                            GuiTip.setTip(tips);

                            hovering_items = true;
                            ITEMS.drawButton(mc, left + 55, top + 20);
                            mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                            drawScaledCustomSizeModalRect(left + 55, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        } else {
                            hovering_items = false;
                            ITEMS.drawButton(mc, left + 55, top + 20);
                            mc.renderEngine.bindTexture(Refraction.icons.get(i));
                            drawScaledCustomSizeModalRect(left + 55, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        }
                        break;
                    }
                    case 2: {
                        boolean inside = mouseX >= BEAM_MANIPULATION.xPosition && mouseX < BEAM_MANIPULATION.xPosition + BEAM_MANIPULATION.width && mouseY >= BEAM_MANIPULATION.yPosition && mouseY < BEAM_MANIPULATION.yPosition + BEAM_MANIPULATION.height;
                        if (inside) {
                            ArrayList<String> tips = new ArrayList<>();
                            tips.add("Learn how to accurate");
                            tips.add("Manipulate light beams");
                            GuiTip.setTip(tips);
                            hovering_beam = true;
                            BEAM_MANIPULATION.drawButton(mc, left + 90, top + 20);
                            mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                            drawScaledCustomSizeModalRect(left + 90, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        } else {
                            hovering_beam = false;
                            BEAM_MANIPULATION.drawButton(mc, left + 90, top + 20);
                            mc.renderEngine.bindTexture(Refraction.icons.get(i));
                            drawScaledCustomSizeModalRect(left + 90, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        }
                        break;
                    }
                    case 3: {
                        boolean inside = mouseX >= ENERGY.xPosition && mouseX < ENERGY.xPosition + ENERGY.width && mouseY >= ENERGY.yPosition && mouseY < ENERGY.yPosition + ENERGY.height;
                        if (inside) {

                            ArrayList<String> tips = new ArrayList<>();
                            tips.add("Learn how to create,");
                            tips.add("transport, manipulate,");
                            tips.add("and store energy");
                            GuiTip.setTip(tips);

                            hovering_energy = true;
                            ENERGY.drawButton(mc, left + 25, top + 60);
                            mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                            drawScaledCustomSizeModalRect(left + 25, top + 60, 0, 0, 25, 25, 25, 25, 25, 25);
                        } else {
                            hovering_energy = false;
                            ENERGY.drawButton(mc, left + 25, top + 20);
                            mc.renderEngine.bindTexture(Refraction.icons.get(i));
                            drawScaledCustomSizeModalRect(left + 25, top + 60, 0, 0, 25, 25, 25, 25, 25, 25);
                        }
                        break;
                    }
                }
            }
        }

        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect((width / 2) - 65, 70, 20, 182, 133, 14);
        FontRenderer fontRenderer = fontRendererObj;
        fontRenderer.setUnicodeFlag(false);
        fontRenderer.setBidiFlag(false);
        fontRenderer.drawString("Physics Book", (width / 2) - 30, 74, 0, false);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
