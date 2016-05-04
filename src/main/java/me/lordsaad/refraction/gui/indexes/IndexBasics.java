package me.lordsaad.refraction.gui.indexes;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.gui.Button;
import me.lordsaad.refraction.gui.GuiHandler;
import me.lordsaad.refraction.gui.GuiSubIndex;
import me.lordsaad.refraction.gui.IndexItem;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * Created by Saad on 5/2/2016.
 */
public class IndexBasics extends GuiSubIndex {

    private int ID = 3;

    @Override
    public void initGui() {
        super.initGui();
        indexItems = new ArrayList<>();

        Button screwdriver = new Button(++ID, 0, 0, 10, 10);
        buttonList.add(screwdriver);
        indexItems.add(new IndexItem(ID,
                GuiHandler.basics_getting_started,
                new ResourceLocation(Refraction.MODID, "textures/items/screwdriver.png"),
                "Getting started", "Learn the very basics of the mod and learn how and where to start.",
                screwdriver));
        Button mirror = new Button(++ID, 0, 0, 10, 10);
        buttonList.add(mirror);
        indexItems.add(new IndexItem(ID,
                GuiHandler.INDEX,
                new ResourceLocation(Refraction.MODID, "textures/items/mirroricon.png"),
                "Light Manipulation", "Learn all the ins and outs of manipulating light and the many kinds of mirrors.",
                mirror));
    }
}
