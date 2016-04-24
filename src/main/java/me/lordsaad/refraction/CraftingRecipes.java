package me.lordsaad.refraction;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Saad on 4/8/2016.
 */
public class CraftingRecipes {

    public static HashMap<ItemStack, ArrayList<ItemStack>> recipes = new HashMap<>();

    public static void initCrafting() {
        ArrayList<ItemStack> items = new ArrayList<>();
        ShapedRecipes MIRROR = CraftingManager.getInstance().addRecipe(new ItemStack(ModBlocks.mirror),
                "QQQ", "GGG", " S ",
                'Q', new ItemStack(Items.quartz),
                'G', new ItemStack(Blocks.glass),
                'S', new ItemStack(Items.stick));

        for (ItemStack stack : MIRROR.recipeItems) items.add(stack);
        recipes.put(MIRROR.getRecipeOutput(), items);
        items.clear();

        ShapedRecipes SCREWDRIVER = CraftingManager.getInstance().addRecipe(new ItemStack(ModItems.screwdriver),
                "  I", " I ", "S  ",
                'I', new ItemStack(Items.iron_ingot),
                'S', new ItemStack(Items.stick));

        for (ItemStack stack : SCREWDRIVER.recipeItems) items.add(stack);
        recipes.put(SCREWDRIVER.getRecipeOutput(), items);
        items.clear();
    }
}
