package me.lordsaad.refraction;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Saad on 4/8/2016.
 */
public class CraftingRecipes {

    public static void initCrafting() {
        GameRegistry.addRecipe(new ItemStack(ModBlocks.mirror),
                "QQQ", "GGG", " S ",
                'Q', new ItemStack(Items.quartz),
                'G', new ItemStack(Blocks.glass),
                'S', new ItemStack(Items.stick));
    }
}
