package me.lordsaad.refraction;

import me.lordsaad.refraction.blocks.BlockMirror;

/**
 * Created by Saad on 3/24/2016.
 */
public class Blocks {

    public static BlockMirror mirror;

    public static void init() {
        mirror = new BlockMirror();
    }
}
