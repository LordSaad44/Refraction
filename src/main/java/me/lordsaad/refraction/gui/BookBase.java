package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.Utils;
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

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Refraction.MODID, "textures/gui/book.png");
    static int guiWidth = 146, guiHeight = 180;
    static int left, top;
    private LinkedHashMap<String, Double> tipLocations = new LinkedHashMap<>();
    private LinkedHashMap<String, Boolean> tipComplete = new LinkedHashMap<>();
    private ArrayList<String> remove = new ArrayList<>();
    private String nextTip, lastTip;
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

    public void addTip(String tip) {
        if (!tipLocations.containsKey(tip)) {
            tipLocations.put(tip, 0d);
            tipComplete.put(tip, false);
            nextTip = tip;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            setIndex(false);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer renderer = fontRendererObj;
        renderer.setUnicodeFlag(true);
        renderer.setBidiFlag(true);

        if (!tipLocations.isEmpty()) {
            for (String tip : tipLocations.keySet()) {

                double tipLoc = tipLocations.get(tip);
                double distance = 145 - Math.abs(tipLoc);

                if (distance < 0.1) {
                    tipComplete.put(tip, true);
                    if (lastTip == null || !lastTip.equals(tip))
                        lastTip = tip;

                    if (!nextTip.equals(tip) && tipComplete.get(nextTip))
                        remove.add(tip);

                } else if (distance >= 0.1) {
                    tipLoc -= distance / 5;
                    tipLocations.put(tip, tipLoc);
                }

                GlStateManager.color(1F, 1F, 1F, 1F);
                mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
                drawTexturedModalRect((float) (left + tipLoc / 1.13), (float) (height / 2.5), 19, 211, 133, 37);

                ArrayList<String> lines = Utils.padString(tip, 31);
                for (String line : lines)
                    renderer.drawString(line.trim(), (float) (left + tipLoc / 1.13) + 5, (float) ((height / 2.5 + 3) + lines.indexOf(line) * 8), 0, false);
            }

            for (String rem : remove) {
                tipLocations.remove(rem);
                tipComplete.remove(rem);
            }
            remove.clear();
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

                            addTip("Learn the basics of light manipulation and how everything works.");

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

                            addTip("Read about what each item and block in this mod does.");

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

                            addTip("Learn how to accurately manipulate light beams.");

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

                            addTip("Learn how to create, transport, manipulate, and store energy.");

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
        drawTexturedModalRect((width / 2) - 65, (float) (top - 20), 20, 182, 133, 14);
        renderer.setUnicodeFlag(false);
        renderer.setBidiFlag(false);
        renderer.drawString("Physics Book", (width / 2) - 30, (float) (top - 20) + 4, 0, false);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
