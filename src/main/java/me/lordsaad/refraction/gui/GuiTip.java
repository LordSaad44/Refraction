package me.lordsaad.refraction.gui;

import me.lordsaad.refraction.Refraction;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Saad on 4/17/2016.
 */
public class GuiTip {

    private static final ResourceLocation SLIDER_TEXTURES = new ResourceLocation(Refraction.MODID, "textures/gui/sliders.png");

    private static LinkedHashMap<String, Integer> tips = new LinkedHashMap<>();

    public static void setTip(ArrayList<String> tipList) {
        if (tips.isEmpty()) {
            for (String tip : tipList) {
                tips.put(tip, 0);
            }
        }
    }

    public static void setTip(String tip) {

    }

    public static LinkedHashMap<String, Integer> getTips() {
        return tips;
    }
}
