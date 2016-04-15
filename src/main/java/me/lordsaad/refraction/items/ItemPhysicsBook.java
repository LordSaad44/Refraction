package me.lordsaad.refraction.items;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.gui.GuiHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Saad on 4/9/2016.
 */
public class ItemPhysicsBook extends Item {

    public ItemPhysicsBook() {
        setRegistryName("physbook");
        setUnlocalizedName("Physics GuiHandler");
        GameRegistry.registerItem(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        playerIn.openGui(Refraction.instance, GuiHandler.INDEX, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
    }
}