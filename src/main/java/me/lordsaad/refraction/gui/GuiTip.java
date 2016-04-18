package me.lordsaad.refraction.gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Saad on 4/17/2016.
 */
public class GuiTip extends BookBase {

    public static void addTip(String tip) {
        tipLocations.put(tip, 0d);
        tipTimes.put(tip, 64);
    }

    public static LinkedHashMap<String, Double> getTipLocations() {
        return tipLocations;
    }

    public static ArrayList<String> getIndexedTips() {
        ArrayList<String> indexed = new ArrayList<>();
        indexed.addAll(tipLocations.keySet());
        return indexed;
    }
}
