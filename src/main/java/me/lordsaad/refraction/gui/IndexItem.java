package me.lordsaad.refraction.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Saad on 5/2/2016.
 */
public class IndexItem {

    private int indexID, pageID;
    private ResourceLocation icon;
    private String text;
    private GuiButton button;

    public IndexItem(int indexID, int pageID, ResourceLocation icon, String text, GuiButton button) {
        this.indexID = indexID;
        this.pageID = pageID;
        this.icon = icon;
        this.text = text;
        this.button = button;
    }

    public int getIndexID() {
        return indexID;
    }

    public void setIndexID(int indexID) {
        this.indexID = indexID;
    }

    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GuiButton getButton() {
        return button;
    }

    public void setButton(GuiButton button) {
        this.button = button;
    }
}
