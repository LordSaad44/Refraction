package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Saad on 4/19/2016.
 */
public class BookBasics extends BookBase {

    private GuiButton BACK, NEXT, TOINDEX;
    private HashMap<Integer, ArrayList<String>> pageLines = new HashMap<>();
    private int currentPage;

    @Override
    public void initGui() {
        super.initGui();
        initButtons();
        splitTextToPages();
    }

    private void initButtons() {
        buttonList.clear();
        buttonList.add(BACK = new GuiButtonCategory(0, left, top * 3, 9, 18));
        buttonList.add(NEXT = new GuiButtonCategory(1, width / 2, top * 3, 9, 18));
        buttonList.add(TOINDEX = new GuiButtonCategory(2, 0, 199, 18, 18));
    }

    private void splitTextToPages() {
        List<String> txt = new ArrayList<>();
        InputStream in = getClass().getResourceAsStream("/assets/refraction/documentation/Basics.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                txt.add(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int height = 0, pagenb = 0;
        ArrayList<String> page = new ArrayList<>();
        for (String lines : txt) {
            if (height <= 10) {
                switch (lines) {
                    case "/n":
                        height++;
                        break;
                    case "/b":
                        page.add("----------------------");
                        //fontRendererObj.drawString("----------------------", left + 20, top + 15 + (height * 8), 0,
                        // false);
                        height++;
                        break;
                    default:
                        for (String pads : Utils.padString(lines, 30)) {
                            page.add(pads);
                            //fontRendererObj.drawString(pads, left + 17, top + 15 + (height * 8), 0, false);
                            height++;
                        }
                        break;
                }
                mc.thePlayer.sendChatMessage(height + "");
            } else {
                pageLines.put(pagenb, page);
                height = 0;
                pagenb++;
            }
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        fontRendererObj.setUnicodeFlag(true);
        fontRendererObj.setBidiFlag(true);

        int height = 0;
        for (String lines : pageLines.get(currentPage)) {
            fontRendererObj.drawString(lines, left + 17, top + 15 + (height * 8), 0, false);
            height++;
        }

        BACK.drawButton(mc, left, top * 3);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top * 3, 0, 180, 9, 18);

        NEXT.drawButton(mc, width / 2, top * 3);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(width / 2, top * 3, 0, 189, 9, 18);

        TOINDEX.drawButton(mc, width / 2, top * 3 + 10);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect((float) (width / 1.5), top * 3 + 10, 0, 199, 18, 18);


        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect((width / 2) - 65, (float) (top - 20), 20, 182, 133, 14);
        fontRendererObj.setUnicodeFlag(false);
        fontRendererObj.setBidiFlag(false);
        fontRendererObj.drawString("Physics Book", (width / 2) - 30, (float) (top - 20) + 4, 0, false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
