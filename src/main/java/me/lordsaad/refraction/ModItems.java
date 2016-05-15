package me.lordsaad.refraction;

import me.lordsaad.refraction.items.ItemLaserPen;
import me.lordsaad.refraction.items.ItemPhysicsBook;
import me.lordsaad.refraction.items.ItemScrewDriver;

/**
 * Created by Saad on 4/9/2016.
 */
public class ModItems {

    public static ItemScrewDriver screwdriver;
    public static ItemPhysicsBook physicsBook;
    public static ItemLaserPen laserPen;

    public static void init() {
        screwdriver = new ItemScrewDriver();
        physicsBook = new ItemPhysicsBook();
        laserPen = new ItemLaserPen();
    }

    public static void initModels() {
        screwdriver.initModel();
        physicsBook.initModel();
        laserPen.initModel();
    }
}
