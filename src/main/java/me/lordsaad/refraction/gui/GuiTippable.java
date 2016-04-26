package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.CraftingRecipes;
import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static me.lordsaad.refraction.gui.PageBasics.recipes;

/**
 * Created by Saad on 4/24/2016.
 */
public class GuiTippable extends PageBase {
    static String currentTextTip = "null", lastTextTip = "null", nextTextTip = "null";
    static ItemStack currentRecipeTip = new ItemStack(Blocks.barrier), lastRecipeTip = new ItemStack(Blocks.barrier), nextRecipeTip = new ItemStack(Blocks.barrier);
    private static ResourceLocation SLIDERS = new ResourceLocation(Refraction.MODID, "textures/gui/sliders.png");
    private static LinkedHashMap<String, Double> textTipLocations = new LinkedHashMap<>();
    private static LinkedHashMap<ItemStack, Double> recipeTipLocations = new LinkedHashMap<>();

    private static ArrayList<String> removeTextTips = new ArrayList<>();
    private static ArrayList<ItemStack> removeRecipeTips = new ArrayList<>();

    static void removeRecipeTips() {
        removeRecipeTips.addAll(recipeTipLocations.keySet());
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    void removeTextTips() {
        removeTextTips.addAll(textTipLocations.keySet());
    }

    void setTextTip(String tip) {
        if (!textTipLocations.containsKey(tip)) {
            textTipLocations.put(tip, 0d);
            nextTextTip = tip;
        }
    }

    void setRecipeTip(String comment, ItemStack stack) {
        if (!recipeTipLocations.containsKey(stack)) {
            recipeTipLocations.put(stack, 0d);
            nextRecipeTip = stack;
            setTextTip(comment);
        }
    }

    private void renderTextTips() {
        fontRendererObj.setUnicodeFlag(true);

        if (!textTipLocations.isEmpty()) {
            for (String tip : textTipLocations.keySet()) {
                double tipLoc = textTipLocations.get(tip);
                double distance = 145 - Math.abs(tipLoc);

                if (distance < 0.1) {
                    if (nextTextTip.equals(tip)) currentTextTip = tip;
                    if (!currentTextTip.equals(tip)) lastTextTip = tip;

                    if (!nextTextTip.equals(tip) && !currentTextTip.equals(tip) && lastTextTip.equals(tip)) {
                        removeTextTips.add(tip);
                    }

                } else if (distance >= 0.1) {
                    tipLoc -= distance / 5;
                    textTipLocations.put(tip, tipLoc);
                    if (!nextTextTip.equals(tip)) nextTextTip = tip;
                }

                GlStateManager.color(1F, 1F, 1F, 1F);
                mc.renderEngine.bindTexture(SLIDERS);
                drawTexturedModalRect((float) (left + tipLoc / 1.13), (float) (height / 3.5), 0, 0, 133, 37);

                ArrayList<String> lines = Utils.padString(tip, 31);
                for (String line : lines)
                    fontRendererObj.drawString(line.trim(), (float) (left + tipLoc / 1.13) + 5, (float) ((height / 3.5 + 3) + lines.indexOf(line) * 8), 0, false);
            }

            for (String rem : removeTextTips) {
                textTipLocations.remove(rem);
                if (currentTextTip.equals(rem)) currentRecipeTip = new ItemStack(Blocks.barrier);
                if (nextTextTip.equals(rem)) nextRecipeTip = new ItemStack(Blocks.barrier);
                if (lastTextTip.equals(rem)) lastRecipeTip = new ItemStack(Blocks.barrier);
            }
            removeTextTips.clear();
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
    }

    public void renderRecipeTips() {
        if (!recipeTipLocations.isEmpty()) {
            for (ItemStack tip : recipeTipLocations.keySet()) {
                double tipLoc = recipeTipLocations.get(tip);
                double distance = 145 - Math.abs(tipLoc);

                if (distance < 0.1) {
                    if (nextRecipeTip.equals(tip) && !lastRecipeTip.equals(tip) && !currentRecipeTip.equals(tip))
                        currentRecipeTip = tip;

                    if (!currentRecipeTip.equals(tip) && !lastRecipeTip.equals(tip) && nextRecipeTip.equals(tip))
                        lastRecipeTip = tip;

                    if (!nextRecipeTip.equals(tip) && !currentRecipeTip.equals(tip) && lastRecipeTip.equals(tip)) {
                        removeRecipeTips.add(tip);
                    }

                } else if (distance >= 0.1) {
                    tipLoc -= distance / 5;
                    recipeTipLocations.put(tip, tipLoc);
                }

                GlStateManager.color(1F, 1F, 1F, 1F);
                mc.renderEngine.bindTexture(SLIDERS);
                if (recipeTipLocations.containsKey(tip)) {
                    drawTexturedModalRect((float) (left + tipLoc / 1.13), (float) (height / 2.5), 0, 37, 133, 68);
                    itemRender.renderItemAndEffectIntoGUI(tip, (int) (left + tipLoc / 1.13) + 100, (int) (height / 2.5) + 26);

                    int x = 0, y = 0;
                    if (CraftingRecipes.recipes.containsKey(tip.getDisplayName())) {
                        for (int craftingPosition : CraftingRecipes.recipes.get(tip.getDisplayName()).keySet()) {
                            ItemStack stack = CraftingRecipes.recipes.get(tip.getDisplayName()).get(craftingPosition);
                            switch (craftingPosition) {
                                case 0:
                                    x = 0;
                                    y = 0;
                                    break;
                                case 1:
                                    x = 1;
                                    y = 0;
                                    break;
                                case 2:
                                    x = 2;
                                    y = 0;
                                    break;
                                case 3:
                                    x = 0;
                                    y = 1;
                                    break;
                                case 4:
                                    x = 1;
                                    y = 1;
                                    break;
                                case 5:
                                    x = 2;
                                    y = 1;
                                    break;
                                case 6:
                                    x = 0;
                                    y = 2;
                                    break;
                                case 7:
                                    x = 1;
                                    y = 2;
                                    break;
                                case 8:
                                    x = 2;
                                    y = 2;
                                    break;
                            }

                            if (stack != null)
                                itemRender.renderItemAndEffectIntoGUI(stack, (int) (left + tipLoc / 1.13) + 9 + x * 18, (int) (height / 2.5) + 8 + y * 18);
                        }
                    }
                }
            }

            for (ItemStack rem : removeRecipeTips) {
                recipeTipLocations.remove(rem);
                if (currentRecipeTip.getDisplayName().equals(rem.getDisplayName()))
                    currentRecipeTip = new ItemStack(Blocks.barrier);
                if (nextRecipeTip.getDisplayName().equals(rem.getDisplayName()))
                    nextRecipeTip = new ItemStack(Blocks.barrier);
                if (lastRecipeTip.getDisplayName().equals(rem.getDisplayName()))
                    lastRecipeTip = new ItemStack(Blocks.barrier);
                if (recipes.contains(PageBasics.currentPage, rem)) recipes.remove(PageBasics.currentPage, rem);
            }
            removeRecipeTips.clear();
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderTextTips();
        renderRecipeTips();
    }
}