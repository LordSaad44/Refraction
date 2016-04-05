package me.lordsaad.refraction;

import me.lordsaad.refraction.blocks.BlockMirror;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Saad on 3/24/2016.
 */
public class Blocks {

    public static BlockMirror mirror;

    public static void init() {
        mirror = new BlockMirror();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        mirror.initModel();
    }

}
