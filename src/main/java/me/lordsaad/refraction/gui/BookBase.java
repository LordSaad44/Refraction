package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Saad on 4/13/2016.
 */
public class BookBase extends GuiScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Refraction.MODID, "textures/gui/book.png");

    int guiWidth = 146;
    int guiHeight = 180;
    int left, top;
    boolean isIndex = true;

    HashMap<String, Integer> tips = new HashMap<>();
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
    public void updateScreen() {
        if (!tips.isEmpty()) {
            for (String tip : tips.keySet()) {
                int tipLoc = tips.get(tip);
                if (tipLoc > tip.length() * -10) {
                    tipLoc--;
                    tips.put(tip, tipLoc);
                    mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
                    drawTexturedModalRect(left + tipLoc, top + 70, 20, 182, 133, 14);
                } else {
                    remove.add(tip);
                }
            }
        }
        if (!remove.isEmpty()) {
            for (String del : remove)
                tips.remove(del);
            remove.clear();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
        if (isIndex) {
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0: {
                        boolean inside = mouseX >= BASICS.xPosition && mouseX < BASICS.xPosition + BASICS.width && mouseY >= BASICS.yPosition && mouseY < BASICS.yPosition + BASICS.height;
                        if (inside) {
                            if (tips.isEmpty()) {
                                tips.put("lorem ipsum dolor", 0);
                                tips.put("sit amet potato", 0);
                            }
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
        fontRenderer.drawString("Physics Book", (width / 2) - 30, 74, 0, false);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
