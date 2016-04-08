package me.lordsaad.refraction.tesrs;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.blocks.BlockMirror;
import me.lordsaad.refraction.tileentities.TileEntityMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.lwjgl.opengl.GL11;

/**
 * Created by Saad on 4/4/2016.
 */
public class TESRMirror extends TileEntitySpecialRenderer<TileEntityMirror> {

    private IModel model;
    private IBakedModel bakedModel;

    public TESRMirror() {
    }

    private IBakedModel getBakedModel() {
        try {
            model = ModelLoaderRegistry.getModel(new ResourceLocation(Refraction.MODID, "block/mirror_pad.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bakedModel == null) {
            bakedModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM,
                    location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
        }
        return bakedModel;
    }

    @Override
    public void renderTileEntityAt(TileEntityMirror tileEntityMirror, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        renderpad(tileEntityMirror);

        GlStateManager.popMatrix();
    }

    private void renderpad(TileEntityMirror te) {
        GlStateManager.pushMatrix();

        EnumFacing facing = getWorld().getBlockState(te.getPos()).getValue(BlockMirror.FACING);

        switch (facing) {
            case EAST:
                GlStateManager.translate(0, 0, 0);
                break;
            case WEST:
                GlStateManager.rotate(180, 0, 1, 0);
                GlStateManager.translate(-1, 0, -1);
                break;
            case NORTH:
                GlStateManager.rotate(90, 0, 1, 0);
                GlStateManager.translate(-1, 0, 0);
                break;
            case SOUTH:
                GlStateManager.rotate(-90, 0, 1, 0);
                GlStateManager.translate(0, 0, -1);
        }

        GlStateManager.translate(0.5, 0.7, 0.5);
        GlStateManager.rotate(te.getAngle(), 0, 0, 1);

        RenderHelper.disableStandardItemLighting();
        bindTexture(TextureMap.locationBlocksTexture);
        if (Minecraft.isAmbientOcclusionEnabled())
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        else
            GlStateManager.shadeModel(GL11.GL_FLAT);

        World world = te.getWorld();
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                world,
                getBakedModel(),
                world.getBlockState(te.getPos()),
                te.getPos(),
                Tessellator.getInstance().getBuffer(), true);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}
