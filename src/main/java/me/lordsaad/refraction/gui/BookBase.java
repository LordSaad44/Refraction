package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.Utils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Saad on 4/19/2016.
 */
public class BookBase extends GuiScreen {

    static int guiWidth = 146, guiHeight = 180;
    static int left, top;
    static ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Refraction.MODID, "textures/gui/book.png");
    private LinkedHashMap<String, Double> tipLocations = new LinkedHashMap<>();
    private LinkedHashMap<String, Boolean> tipComplete = new LinkedHashMap<>();
    private ArrayList<String> remove = new ArrayList<>();
    private String nextTip, lastTip;
    private boolean isIndex = true;

    @Override
    public void initGui() {
        super.initGui();
        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;
        //if (currentPage == null) currentPage = new BookIndex();
        //setIndex(true);
    }

    public boolean isIndex() {
        return isIndex;
    }

    public void setIndex(boolean isIndex) {
        buttonList.clear();
        this.isIndex = isIndex;

    }

    public void setTip(String tip) {
        if (!tipLocations.containsKey(tip)) {
            tipLocations.put(tip, 0d);
            tipComplete.put(tip, false);
            nextTip = tip;
        }
    }

    public void renderTips() {
        FontRenderer renderer = fontRendererObj;
        renderer.setUnicodeFlag(true);

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
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        renderTips();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
