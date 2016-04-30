package me.lordsaad.refraction;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import java.util.HashMap;

/**
 * Created by Saad on 4/8/2016.
 */
public class CraftingRecipes {

    public static HashMap<String, HashMap<Integer, ItemStack>> recipes = new HashMap<>();

    public static void initCrafting() {
        CraftingManager.getInstance().addRecipe(new ItemStack(ModBlocks.mirror),
                "QQQ", "GGG", " S ",
                'Q', new ItemStack(Items.quartz),
                'G', new ItemStack(Blocks.glass),
                'S', new ItemStack(Items.stick));
        HashMap<Integer, ItemStack> mirrorRecipe = new HashMap<>();
        mirrorRecipe.put(0, new ItemStack(Items.quartz));
        mirrorRecipe.put(1, new ItemStack(Items.quartz));
        mirrorRecipe.put(2, new ItemStack(Items.quartz));
        mirrorRecipe.put(3, new ItemStack(Blocks.glass));
        mirrorRecipe.put(4, new ItemStack(Blocks.glass));
        mirrorRecipe.put(5, new ItemStack(Blocks.glass));
        mirrorRecipe.put(7, new ItemStack(Items.stick));
        recipes.put(new ItemStack(ModBlocks.mirror).getDisplayName(), mirrorRecipe);

        CraftingManager.getInstance().addRecipe(new ItemStack(ModItems.screwdriver),
                "  I", " I ", "S  ",
                'I', new ItemStack(Items.iron_ingot),
                'S', new ItemStack(Items.stick));
        HashMap<Integer, ItemStack> screwdriverRecipe = new HashMap<>();
        screwdriverRecipe.put(2, new ItemStack(Items.iron_ingot));
        screwdriverRecipe.put(4, new ItemStack(Items.iron_ingot));
        screwdriverRecipe.put(6, new ItemStack(Items.stick));
        recipes.put(new ItemStack(ModItems.screwdriver).getDisplayName(), screwdriverRecipe);
    }
}
