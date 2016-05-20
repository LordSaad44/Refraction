package me.lordsaad.refraction.tesrs;

import me.lordsaad.refraction.Refraction;
import me.lordsaad.refraction.Utils;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.lwjgl.opengl.GL11;

import java.awt.*;

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

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        renderpad(tileEntityMirror);

        GlStateManager.popMatrix();
    }

    private void renderpad(TileEntityMirror te) {
        GlStateManager.pushMatrix();

        // RAYTRACE //
        Vec3d centervec = new Vec3d(te.getPos().getX() + 0.5, te.getPos().getY() + 0.8, te.getPos().getZ() + 0.5);
        Vec3d lookvec = Utils.getVectorForRotation3d(te.getBeamPitch(), te.getBeamYaw());
        Vec3d startvec = centervec.add(lookvec);

        //player.addChatComponentMessage(new TextComponentString(player.rotationPitch + " - " + player.rotationYaw));

        Vec3d end = startvec.add(new Vec3d(lookvec.xCoord * 100, lookvec.yCoord * 100, lookvec.zCoord * 100));
        RayTraceResult result = te.getWorld().rayTraceBlocks(startvec, end, true, false, true);

        Utils.drawConnection(te.getPos(), result.getBlockPos(), Color.WHITE);
        // RAYTRACE //

        // ORIENT PAD //
        EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockMirror.FACING);

        switch (facing) {
            case NORTH:
                GlStateManager.translate(0.5, 0.5, 0.4);
                break;
            case SOUTH:
                GlStateManager.translate(0.5, 0.5, 0.6);
                break;
            case EAST:
                GlStateManager.translate(0.6, 0.5, 0.5);
                break;
            case WEST:
                GlStateManager.translate(0.4, 0.5, 0.5);
                break;
            case DOWN:
                GlStateManager.translate(0.5, 0.4, 0.5);
                break;
            case UP:
                GlStateManager.translate(0.5, 0.6, 0.5);
                break;
        }

        GlStateManager.rotate(te.getPadYaw(), 0, 0, 1);
        GlStateManager.rotate(te.getPadPitch(), 1, 0, 0);

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
