package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by Saad on 4/13/2016.
 */
public class BookBase extends GuiScreen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Refraction.MODID, "textures/gui/book.png");
    private static final ResourceLocation SLIDER_TEXTURES = new ResourceLocation(Refraction.MODID, "textures/gui/sliders.png");
    public static LinkedHashMap<String, Double> tipLocations = new LinkedHashMap<>();
    public static LinkedHashMap<String, Integer> tipTimes = new LinkedHashMap<>();
    static int guiWidth = 146, guiHeight = 180;
    static int left, top;
    static int getNextTip = 0;
    private boolean isIndex = true;

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
        if (!tipLocations.isEmpty()) {

            String tip = GuiTip.getIndexedTips().get(getNextTip);
            int y1 = 0, y2 = 0;
            if (tip.length() <= 38) {
                y1 = 0;
                y2 = 13;
            } else if (tip.length() <= 76) {
                y1 = 14;
                y2 = 38;
            } else if (tip.length() <= 114) {
                y1 = 39;
                y2 = 73;
            } else if (tip.length() <= 152) {
                y1 = 74;
                y2 = 119;
            } else if (tip.length() <= 190) {
                y1 = 120;
                y2 = 175;
            } else if (tip.length() <= 228) {
                y1 = 176;
                y2 = 241;
            }

            double tipLoc = tipLocations.get(tip);
            double distance = 145 - Math.abs(tipLoc);
            if (distance < 0.1) distance = 0;
            mc.thePlayer.addChatComponentMessage(new TextComponentString(tipLoc + " - " + distance));

            tipLoc -= distance / 5;
            tipLocations.put(tip, tipLoc);

            GlStateManager.color(1F, 1F, 1F, 1F);
            mc.renderEngine.bindTexture(SLIDER_TEXTURES);
            drawTexturedModalRect((float) (left + tipLoc), height / 2, 0, y1, 145, y2);

            FontRenderer fontRenderer = fontRendererObj;
            fontRenderer.setUnicodeFlag(true);
            fontRenderer.setBidiFlag(true);
            fontRenderer.drawString(tip.replaceAll("(.{38})", "$1\n"), (float) (left + tipLoc) + 3, height / 2 + 2, 0, false);

            if (distance == 0) {
                if (getNextTip - 1 < 0) {
                    if (tipLocations.size() > 1) {
                        tipLocations.remove(tip);
                        tipTimes.remove(tip);
                        getNextTip++;
                    }
                } else {
                    tipLocations.remove(tip);
                    tipTimes.remove(tip);
                    getNextTip--;
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

                            GuiTip.addTip("Learn the basics of light manipulation and how everything works.");

                            BASICS.drawButton(mc, left + 55, top + 20);
                            mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                            drawScaledCustomSizeModalRect(left + 25, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        } else {
                            BASICS.drawButton(mc, left + 55, top + 20);
                            mc.renderEngine.bindTexture(Refraction.icons.get(i));
                            drawScaledCustomSizeModalRect(left + 25, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        }
                        break;
                    }
                    case 1: {
                        boolean inside = mouseX >= ITEMS.xPosition && mouseX < ITEMS.xPosition + ITEMS.width && mouseY >= ITEMS.yPosition && mouseY < ITEMS.yPosition + ITEMS.height;
                        if (inside) {

                            GuiTip.addTip("Read about what each item and block in this mod does.");

                            ITEMS.drawButton(mc, left + 55, top + 20);
                            mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                            drawScaledCustomSizeModalRect(left + 55, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        } else {
                            ITEMS.drawButton(mc, left + 55, top + 20);
                            mc.renderEngine.bindTexture(Refraction.icons.get(i));
                            drawScaledCustomSizeModalRect(left + 55, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        }
                        break;
                    }
                    case 2: {
                        boolean inside = mouseX >= BEAM_MANIPULATION.xPosition && mouseX < BEAM_MANIPULATION.xPosition + BEAM_MANIPULATION.width && mouseY >= BEAM_MANIPULATION.yPosition && mouseY < BEAM_MANIPULATION.yPosition + BEAM_MANIPULATION.height;
                        if (inside) {

                            GuiTip.addTip("Learn how to accurately manipulate light beams.");

                            BEAM_MANIPULATION.drawButton(mc, left + 90, top + 20);
                            mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                            drawScaledCustomSizeModalRect(left + 90, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        } else {
                            BEAM_MANIPULATION.drawButton(mc, left + 90, top + 20);
                            mc.renderEngine.bindTexture(Refraction.icons.get(i));
                            drawScaledCustomSizeModalRect(left + 90, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                        }
                        break;
                    }
                    case 3: {
                        boolean inside = mouseX >= ENERGY.xPosition && mouseX < ENERGY.xPosition + ENERGY.width && mouseY >= ENERGY.yPosition && mouseY < ENERGY.yPosition + ENERGY.height;
                        if (inside) {

                            GuiTip.addTip("Learn how to create, transport, manipulate, and store energy.");

                            ENERGY.drawButton(mc, left + 25, top + 60);
                            mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                            drawScaledCustomSizeModalRect(left + 25, top + 60, 0, 0, 25, 25, 25, 25, 25, 25);
                        } else {
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
