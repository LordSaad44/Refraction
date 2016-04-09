package me.lordsaad.refraction;

import me.lordsaad.refraction.items.ItemScrewDriver;

/**
 * Created by Saad on 4/9/2016.
 */
public class ModItems {

    public static ItemScrewDriver screwdriver;

    public static void init() {
        screwdriver = new ItemScrewDriver();
    }

    public static void initModels() {
        screwdriver.initModel();
    }
}
