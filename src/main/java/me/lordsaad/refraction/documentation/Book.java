package me.lordsaad.refraction.documentation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Saad on 4/13/2016.
 */
public class Book implements IGuiHandler {
    public static int INDEX = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == INDEX) {
            return new Index();
        }
        return null;
    }
}
