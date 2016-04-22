package me.lordsaad.refraction.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.SoundEvents;

/**
 * Created by Saad on 4/15/2016.
 */
public class GuiButtonCategory extends GuiButton {

    public GuiButtonCategory(int buttonId, float x, float y, float width, float height) {
        super(buttonId, (int) x, (int) y, (int) width, (int) height, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.block_lever_click, 1.0F));
    }
}
