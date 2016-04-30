package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Saad on 4/30/2016.
 */
public class Tippable extends PageBase {

    private static int IDs = 0;
    private static ResourceLocation SLIDERS = new ResourceLocation(Refraction.MODID, "textures/gui/sliders.png");
    private static LinkedHashMap<Integer, String> tipText = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, Float> tipX = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, Boolean> slideOut = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, HashMap<ItemStack, HashMap<Integer, ItemStack>>> tipRecipe = new LinkedHashMap<>();
    private static ArrayList<Integer> removeTip = new ArrayList<>();

    public static void setTip(String tip) {
        if (!tipText.containsValue(tip)) {
            IDs++;
            tipText.put(IDs, tip);
            tipX.put(IDs, 0F);
            slideOut.put(IDs, true);
        }
    }

    public static int setTip(ItemStack recipeOutput, HashMap<Integer, ItemStack> recipe, String tip) {
        if (!tipText.containsValue(tip)) {
            IDs++;
            tipText.put(IDs, tip);
            tipX.put(IDs, 0F);
            HashMap<ItemStack, HashMap<Integer, ItemStack>> temp = new HashMap<>();
            temp.put(recipeOutput, recipe);
            tipRecipe.put(IDs, temp);
            slideOut.put(IDs, true);
            return IDs;
        } else {
            for (Map.Entry<Integer, String> entry : tipText.entrySet()) {
                if (entry.getValue().equals(tip)) {
                    return entry.getKey();
                }
            }
        }
        return 0;
    }

    public static void clearTips() {
        tipX.keySet().forEach(Tippable::removeTip);
        IDs = -1;
    }

    public static int getActiveTip() {
        return IDs;
    }

    public static void removeTip(int ID) {
        slideOut.put(ID, false);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Remove finished tips
        for (int ID : removeTip) {
            if (tipX.containsKey(ID)) tipX.remove(ID);
            if (tipText.containsKey(ID)) tipText.remove(ID);
            if (tipRecipe.containsKey(ID)) tipRecipe.remove(ID);
        }
        removeTip.clear();

        for (int ID : tipText.keySet()) {

            // Calculate x for each tip
            float x = tipX.get(ID);
            if (slideOut.containsKey(ID))
                if (slideOut.get(ID)) {
                    if (x >= -144) x -= ((145 - Math.abs(x)) / 5);
                } else if (x <= 0) x += (Math.abs(x) / 5);
            if (x <= -144 && ID != ID + 1)
                if (tipX.containsKey(ID + 1))
                    if (tipX.get(ID + 1) <= -144) removeTip.add(ID);
            if ((int) x == 0) removeTip.add(ID);

            tipX.put(ID, x);

            // Render text tips
            if (tipText.containsKey(ID) && tipX.containsKey(ID)) {

                // render slider
                GlStateManager.color(1F, 1F, 1F, 1F);
                mc.renderEngine.bindTexture(SLIDERS);
                drawTexturedModalRect((float) (left + x / 1.13), (float) (height / 3.5), 0, 0, 133, 37);

                // render text
                ArrayList<String> lines = Utils.padString(tipText.get(ID), 31);
                for (String line : lines)
                    fontRendererObj.drawString(line.trim(), (float) (left + x / 1.13) + 5, (float) ((height / 3.5 + 3) + lines.indexOf(line) * 8), 0, false);
            }

            // Render recipe tips
            if (tipRecipe.containsKey(ID)) {

                // render slider
                GlStateManager.color(1F, 1F, 1F, 1F);
                mc.renderEngine.bindTexture(SLIDERS);
                drawTexturedModalRect((float) (left + x / 1.13), (float) (height / 2.5), 0, 37, 133, 68);

                // render recipe output item
                if (slideOut.get(ID) && ID == IDs) {
                    ItemStack output = null;
                    for (ItemStack tempStack : tipRecipe.get(ID).keySet()) output = tempStack;
                    if (output == null) return;
                    itemRender.renderItemAndEffectIntoGUI(output, (int) (left + x / 1.13) + 100, (int) (height / 2.5) + 26);

                    // render recipe items
                    HashMap<Integer, ItemStack> slots = tipRecipe.get(ID).get(output);
                    if (slots != null) {
                        int xSlot, ySlot;
                        for (Integer slot : slots.keySet()) {
                            if (slot == null) return;
                            switch (slot) {
                                case 0:
                                    xSlot = 0;
                                    ySlot = 0;
                                    break;
                                case 1:
                                    xSlot = 1;
                                    ySlot = 0;
                                    break;
                                case 2:
                                    xSlot = 2;
                                    ySlot = 0;
                                    break;
                                case 3:
                                    xSlot = 0;
                                    ySlot = 1;
                                    break;
                                case 4:
                                    xSlot = 1;
                                    ySlot = 1;
                                    break;
                                case 5:
                                    xSlot = 2;
                                    ySlot = 1;
                                    break;
                                case 6:
                                    xSlot = 0;
                                    ySlot = 2;
                                    break;
                                case 7:
                                    xSlot = 1;
                                    ySlot = 2;
                                    break;
                                case 8:
                                    xSlot = 2;
                                    ySlot = 2;
                                    break;
                                default:
                                    xSlot = 0;
                                    ySlot = 0;
                                    break;
                            }

                            if (slots.get(slot) != null)
                                itemRender.renderItemAndEffectIntoGUI(slots.get(slot), (int) ((left + x / 1.13) + 9 + xSlot * 18), (int) (height / 2.5) + 8 + ySlot * 18);
                        }
                    }
                }
            }
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
    }
}
