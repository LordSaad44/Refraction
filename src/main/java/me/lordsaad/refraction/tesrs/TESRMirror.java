package me.lordsaad.refraction.tesrs;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.tileentities.TileEntityMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
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
        if (bakedModel == null) {
            try {
                model = ModelLoaderRegistry.getModel(new ResourceLocation(Refraction.MODID, "block/mirror_pad.obj"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            bakedModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM,
                    location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
        }
        return bakedModel;
    }

    @Override
    public void renderTileEntityAt(TileEntityMirror tileEntityMirror, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();

        GlStateManager.rotate(tileEntityMirror.getAngle(), 0, 0, 1);

        bindTexture(TextureMap.locationBlocksTexture);

        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                getWorld(),
                getBakedModel(),
                getWorld().getBlockState(tileEntityMirror.getPos()),
                tileEntityMirror.getPos(),
                tessellator.getBuffer(),
                true);
        tessellator.draw();

        GlStateManager.popMatrix();
    }
}
