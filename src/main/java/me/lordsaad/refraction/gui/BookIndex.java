package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

/**
 * Created by Saad on 4/13/2016.
 */
public class BookIndex extends BookBase {

    private GuiButton BASICS, ITEMS, BEAM_MANIPULATION, ENERGY;

    @Override
    public void initGui() {
        super.initGui();
        initIndexButtons();
    }

    private void initIndexButtons() {
        buttonList.add(BASICS = new GuiButtonCategory(0, left + 25, top + 20, 25, 25));
        buttonList.add(ITEMS = new GuiButtonCategory(1, left + 55, top + 20, 25, 25));
        buttonList.add(BEAM_MANIPULATION = new GuiButtonCategory(2, left + 90, top + 20, 25, 25));
        buttonList.add(ENERGY = new GuiButtonCategory(3, left + 25, top + 60, 25, 25));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            setIndex(false);
            mc.thePlayer.openGui(Refraction.instance, GuiHandler.BASICS, mc.theWorld, (int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: {
                    boolean inside = mouseX >= BASICS.xPosition && mouseX < BASICS.xPosition + BASICS.width && mouseY >= BASICS.yPosition && mouseY < BASICS.yPosition + BASICS.height;
                    if (inside) {

                        addTip("Learn the basics of light manipulation and how everything works.");

                        BASICS.drawButton(mc, left + 25, top + 20);
                        mc.renderEngine.bindTexture(Refraction.hovered_icons.get(i));
                        drawScaledCustomSizeModalRect(left + 25, top + 20, 0, 0, 25, 25, 25, 25, 25, 25);
                    } else {
                        BASICS.drawButton(mc, left + 25, top + 20);
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


        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect((width / 2) - 65, (float) (top - 20), 20, 182, 133, 14);
        fontRendererObj.setUnicodeFlag(false);
        fontRendererObj.drawString("Physics Book", (width / 2) - 30, (float) (top - 20) + 4, 0, false);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
