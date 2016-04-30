package me.lordsaad.refraction.gui;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.lordsaad.refraction.CraftingRecipes;
import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Saad on 4/19/2016.
 */
public class PageBasics extends Tippable {

    public static int currentPage = 0;
    public static HashMap<Integer, ArrayList<String>> pages;
    static Table<Integer, Item, String> BADrecipes = HashBasedTable.create();
    private static HashMap<Integer, HashMap<Item, String>> recipes = new HashMap<>();
    private static int activeTipID;
    private GuiButton BACK, NEXT, TOINDEX;

    @Override
    public void initGui() {
        super.initGui();
        pages = new HashMap<>();
        initButtons();
        String TEXT_RESOURCE = "/assets/refraction/documentation/Basics.txt";
        pages = Utils.splitTextToPages(pages, getClass().getResourceAsStream(TEXT_RESOURCE), this);
    }

    private void initButtons() {
        buttonList.clear();
        buttonList.add(BACK = new GuiButtonCategory(0, left, top * 3, 9, 18));
        buttonList.add(NEXT = new GuiButtonCategory(1, left + 135, top * 3, 9, 18));
        buttonList.add(TOINDEX = new GuiButtonCategory(2, left + 60, top * 3 + 10, 18, 18));
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
                if (pages.size() + 1 >= currentPage) {
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
        fontRendererObj.setBidiFlag(true);

        int height = 0;
        if (pages.containsKey(currentPage)) {
            for (String line : pages.get(currentPage)) {
                if (line.contains("/r:")) {
                    int requiredPage = Integer.parseInt(line.substring(line.indexOf("/r:") + 3).split(";")[0]);
                    String itemName = line.substring(line.indexOf("/r:") + 3).split(";")[1];
                    String comment = line.substring(line.indexOf("/r:") + 3).split(";")[2];
                    Item item = Item.getByNameOrId(itemName);

                    if (!recipes.containsKey(requiredPage)) {
                        HashMap<Item, String> temp = new HashMap<>();
                        temp.put(item, comment);
                        recipes.put(requiredPage, temp);
                    }
                } else {
                    fontRendererObj.drawString(line, left + 17, top + 13 + (height * 8), 0, false);
                    height++;
                }
            }
        }

        if (recipes.containsKey(currentPage)) {
            for (Item item : recipes.get(currentPage).keySet()) {
                HashMap<Integer, ItemStack> recipe = CraftingRecipes.recipes.get(new ItemStack(item).getDisplayName());
                activeTipID = setTip(new ItemStack(item), recipe, recipes.get(currentPage).get(item));
            }
        } else removeTip(activeTipID);

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
