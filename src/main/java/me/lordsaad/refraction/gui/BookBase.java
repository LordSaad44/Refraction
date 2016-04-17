package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Saad on 4/13/2016.
 */
public class BookBase extends GuiScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Refraction.MODID, "textures/gui/book.png");

    public static int guiWidth = 146, guiHeight = 180;
    public static int left, top;
    boolean isIndex = true;
    boolean hovering_basics, hovering_items, hovering_beam, hovering_energy;

    LinkedHashMap<String, Integer> tips = new LinkedHashMap<>();
    ArrayList<String> remove = new ArrayList<>();
    GuiButton BASICS, ITEMS, BEAM_MANIPULATION, ENERGY;

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
        if (!tips.isEmpty()) {
            if (hovering_energy || hovering_basics || hovering_beam || hovering_items) {
                for (int i = 0; i < tips.size(); i++) {
                    String tip = (new ArrayList<>(tips.keySet()).get(i));
                    if (tip.length() * 5 + 10 <= 145) {
                        int tipLoc = tips.get(tip);
                        int distance = Math.abs(tipLoc - (tip.length() * -5));
                        if (Math.abs(tipLoc) <= tip.length() * 5) {
                            tipLoc -= distance / 5;
                            tips.put(tip, tipLoc);

                            GlStateManager.color(1F, 1F, 1F, 1F);
                            mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
                            drawTexturedModalRect(left + tipLoc, top + 70 + i * 13, 19, 202, 164, 14);

                            FontRenderer fontRenderer = fontRendererObj;
                            fontRenderer.setUnicodeFlag(true);
                            fontRenderer.setBidiFlag(true);
                            fontRenderer.drawString(tip, left + tipLoc + 5, top + 72 + i * 13, 0, false);
                        }
                    }
                }
            } else {
                for (int i = 0; i < tips.size(); i++) {
                    String tip = (new ArrayList<>(tips.keySet()).get(i));
                    int tipLoc = tips.get(tip);
                    int distance = Math.abs(tipLoc - (tip.length()));
                    tipLoc += distance / 5;
                    tips.put(tip, tipLoc);

                    GlStateManager.color(1F, 1F, 1F, 1F);
                    mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
                    drawTexturedModalRect(left + tipLoc, top + 70 + i * 13, 19, 202, tip.length() * 5 + 10, 14);

                    FontRenderer fontRenderer = fontRendererObj;
                    fontRenderer.setUnicodeFlag(true);
                    fontRenderer.setBidiFlag(true);
                    fontRenderer.drawString(tip, left + tipLoc + 5, top + 72 + i * 13, 0, false);

                    if (Math.abs(tipLoc) >= tip.length() * 6) tips.remove(tip);
                    if (tipLoc < 0) tips.remove(tip);
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
                            if (tips.isEmpty()) {
                                tips.put("Learn the basics", 0);
                                tips.put("of light manipulation", 0);
                                tips.put("and how everything works", 0);
                            }
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
                            if (tips.isEmpty()) {
                                tips.put("Read what each item and", 0);
                                tips.put("block in this mod does", 0);
                            }
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
                            if (tips.isEmpty()) {
                                tips.put("Learn how to accurate", 0);
                                tips.put("Manipulate light beams", 0);
                            }
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
                            if (tips.isEmpty()) {
                                tips.put("Learn how to create,", 0);
                                tips.put("transport, manipulate,", 0);
                                tips.put("and store energy", 0);
                            }
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
