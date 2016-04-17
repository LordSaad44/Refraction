package me.lordsaad.refraction.gui;

import com.google.common.base.Splitter;

import java.util.ArrayList;

/**
 * Created by Saad on 4/17/2016.
 */
public class GuiTip {

    private static ArrayList<String> tips = new ArrayList<>();

    public static void setTip(ArrayList<String> tipList) {
        if (tips.isEmpty()) {
            tips.addAll(tipList);
        }
    }

    public static void setTip(String tip) {
        if (tips.isEmpty()) {
            if (tip.length() > 50) {
                for (String lines : Splitter.fixedLength(50).split(tip)) {
                    tips.add(lines);
                }
            }
        }
    }

    public static ArrayList<String> getTips() {
        return tips;
    }
}
