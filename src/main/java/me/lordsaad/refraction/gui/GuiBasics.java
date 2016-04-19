package me.lordsaad.refraction.gui;

/**
 * Created by Saad on 4/15/2016.
 */
public class GuiBasics extends BookIndex {

    @Override
    public void initGui() {
        super.initGui();
        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;
    }
}
