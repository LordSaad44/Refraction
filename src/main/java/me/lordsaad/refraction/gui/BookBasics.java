package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextComponentString;

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
        buttonList.add(NEXT = new GuiButtonCategory(1, left + 135, top * 3, 9, 18));
        buttonList.add(TOINDEX = new GuiButtonCategory(2, left + 60, top * 3 + 10, 18, 18));
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

        ArrayList<String> page = new ArrayList<>();
        ArrayList<String> trailingText = new ArrayList<>();
        ArrayList<String> stupidBackupTrailingText = new ArrayList<>();
        int pagenb = 0;
        boolean onPage = true;

        // TRAILING TEXT //
        if (!stupidBackupTrailingText.isEmpty()) {
            for (String trails : stupidBackupTrailingText) {
                if (page.size() < 18) {
                    page.add(trails);
                } else {
                    mc.thePlayer.addChatComponentMessage(new TextComponentString("Page text is trailing. " +
                            "Please break the doc text with /p (to force a new page)"));
                }
            }
        }
        if (!trailingText.isEmpty()) {
            for (String trails : trailingText) {
                if (page.size() < 18) {
                    page.add(trails);
                } else {
                    stupidBackupTrailingText.add(trails);
                }
            }
        }
        // TRAILING TEXT //

        for (String lines : txt) {
            if (page.size() < 18 && onPage) {
                switch (lines) {
                    case "/n":
                        page.add(" ");
                        break;
                    case "/b":
                        page.add("----------------------");
                        break;
                    case "/p":
                        onPage = false;
                    default:
                        for (String padded : Utils.padString(lines, 30)) {
                            if (page.size() < 18) {
                                page.add(padded);
                            } else {
                                trailingText.add(padded);
                            }
                        }
                        break;
                }
            } else {
                pageLines.put(pagenb, page);
                pagenb++;
                onPage = true;
                page.clear();
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 2) {
            mc.thePlayer.openGui(Refraction.instance, GuiHandler.INDEX, mc.theWorld, (int) mc.thePlayer.posX, (int)
                    mc.thePlayer.posY, (int) mc.thePlayer.posZ);
        }
        if (button.id == 1) {
            if (pageLines.size() + 1 > currentPage)
                currentPage++;
        }
        if (button.id == 0) {
            if (currentPage > 0) {
                currentPage--;
            } else {
                mc.thePlayer.openGui(Refraction.instance, GuiHandler.INDEX, mc.theWorld, (int) mc.thePlayer.posX, (int)
                        mc.thePlayer.posY, (int) mc.thePlayer.posZ);
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
            fontRendererObj.drawString(lines, left + 17, top + 13 + (height * 8), 0, false);
            height++;
        }

        GlStateManager.color(1F, 1F, 1F, 1F);

        BACK.drawButton(mc, left, top * 3);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top * 3, 0, 180, 10, 18);

        NEXT.drawButton(mc, left + 135, top * 3);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left + 135, top * 3, 10, 180, 10, 18);

        TOINDEX.drawButton(mc, left + 60, top * 3 + 10);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left + 60, top * 3 + 10, 0, 199, 18, 18);


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
