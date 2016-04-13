package me.lordsaad.refraction.items;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.documentation.Book;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
        setUnlocalizedName("Physics Book");
        GameRegistry.registerItem(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(Refraction.instance, Book.INDEX, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            return EnumActionResult.SUCCESS;
        } else return EnumActionResult.PASS;
    }
}
