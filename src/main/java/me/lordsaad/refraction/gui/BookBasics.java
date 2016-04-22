package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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

    private static HashMap<Integer, ArrayList<String>> pages;
    private static int currentPage = 0;
    private GuiButton BACK, NEXT, TOINDEX;

    @Override
    public void initGui() {
        super.initGui();
        pages = new HashMap<>();
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

        int pagenb = 0;
        for (String line : txt) {
            pages.putIfAbsent(pagenb, new ArrayList<>());

            if (pages.get(pagenb).size() >= 15) pagenb++;

            if (line.contains("/n"))
                pages.get(pagenb).add(" ");

            else if (line.contains("/b"))
                pages.get(pagenb).add("-----------------------------");

            else if (line.contains("/p")) {
                pagenb++;
                pages.putIfAbsent(pagenb, new ArrayList<>());

            } else if (line.contains("/r:")) {
                Item item = Item.getByNameOrId(line.substring(line.indexOf("/r:")));
                if (item != null) {
                    setRecipeTip(line.split(";")[1], new ItemStack(item));
                }
            } else {
                ArrayList<String> pads = Utils.padString(line, 30);
                for (String padded : pads) {
                    if (pads.size() < 15) {
                        pages.get(pagenb).add(padded);
                    } else {
                        pagenb++;
                        pages.putIfAbsent(pagenb, new ArrayList<>());
                        pages.get(pagenb).add(padded);
                    }
                }
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                if (currentPage > 0) {
                    currentPage--;
                    mc.thePlayer.openGui(Refraction.instance, GuiHandler.BASICS, mc.theWorld, (int) mc.thePlayer.posX, (int)
                            mc.thePlayer.posY, (int) mc.thePlayer.posZ);
                } else {
                    mc.thePlayer.openGui(Refraction.instance, GuiHandler.INDEX, mc.theWorld, (int) mc.thePlayer.posX, (int)
                            mc.thePlayer.posY, (int) mc.thePlayer.posZ);
                    currentPage = 0;
                }
                break;
            }
            case 1: {
                if (pages.size() + 1 > currentPage) {
                    currentPage++;
                    mc.thePlayer.openGui(Refraction.instance, GuiHandler.BASICS, mc.theWorld, (int) mc.thePlayer.posX, (int)
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
        super.drawScreen(mouseX, mouseY, partialTicks);

        fontRendererObj.setUnicodeFlag(true);

        int height = 0;
        if (pages.containsKey(currentPage))
            for (String line : pages.get(currentPage)) {
                fontRendererObj.drawString(line, left + 17, top + 13 + (height * 8), 0, false);
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
