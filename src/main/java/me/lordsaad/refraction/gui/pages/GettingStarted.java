package me.lordsaad.refraction.gui.pages;

import me.lordsaad.refraction.Utils;
import me.lordsaad.refraction.gui.GuiContentPage;
import me.lordsaad.refraction.gui.GuiHandler;

/**
 * Created by Saad on 4/19/2016.
 */
public class GettingStarted extends GuiContentPage {

    @Override
    public void initGui() {
        super.initGui();
        String TEXT_RESOURCE = "/assets/refraction/documentation/getting_started.txt";
        pages = Utils.splitTextToPages(pages, getClass().getResourceAsStream(TEXT_RESOURCE), this);
        pageID = GuiHandler.basics_getting_started;
    }
}

