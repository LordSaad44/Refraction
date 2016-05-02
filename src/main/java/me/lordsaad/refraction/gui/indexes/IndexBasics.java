package me.lordsaad.refraction.gui.indexes;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.gui.GuiHandler;
import me.lordsaad.refraction.gui.GuiSubIndex;
import me.lordsaad.refraction.gui.IndexItem;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * Created by Saad on 5/2/2016.
 */
public class IndexBasics extends GuiSubIndex {

    private int ID = -1;

    @Override
    public void initGui() {
        super.initGui();
        indexItems = new ArrayList<>();

        GuiButton button = new GuiButton(ID, 0, 0, 10, 10, "");
        buttonList.add(button);
        indexItems.add(new IndexItem(++ID, GuiHandler.basics_getting_started, new ResourceLocation(Refraction.MODID, "textures/items/screwdriver.png"), "Learn how to get started with the mod.", button));
    }
}
