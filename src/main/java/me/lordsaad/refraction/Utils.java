package me.lordsaad.refraction;

import me.lordsaad.refraction.gui.PageBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Saad on 4/9/2016.
 */
public class Utils {

    public static Vec3d getVectorForRotation3d(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double) (f1 * f2), (double) f3, (double) (f * f2));
    }

    public static ArrayList<String> padString(String string, int stringSize) {
        ArrayList<String> lines = new ArrayList<>();
        for (String line : WordUtils.wrap(string, stringSize).split("\n")) lines.add(line.trim());
        return lines;
    }

    public static HashMap<Integer, ArrayList<String>> splitTextToPages(HashMap<Integer, ArrayList<String>> pages,
                                                                       InputStream stream, PageBase base) {
        List<String> txt = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                txt.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int pagenb = 0;
        for (String line : txt) {

            pages.putIfAbsent(pagenb, new ArrayList<>());
            if (pages.get(pagenb).size() >= 18) {
                pagenb++;
                pages.putIfAbsent(pagenb, new ArrayList<>());
            }

            if (line.contains("/n")) pages.get(pagenb).add(" ");

            else if (line.contains("/b")) pages.get(pagenb).add("-----------------------------");

            else if (line.contains("/p")) {
                pagenb++;
                pages.putIfAbsent(pagenb, new ArrayList<>());
            } else if (line.contains("/r")) {
                pages.get(pagenb).add(line);
            } else {
                ArrayList<String> pads = Utils.padString(line, 30);
                for (String padded : pads) {
                    if (pages.get(pagenb).size() < 18) {
                        pages.get(pagenb).add(padded);
                    } else {
                        pagenb++;
                        pages.putIfAbsent(pagenb, new ArrayList<>());
                        pages.get(pagenb).add(padded);
                    }
                }
            }
        }
        return pages;
    }

    public static void drawConnection(double playerX, double playerY, double playerZ, BlockPos pos1, BlockPos pos2, Color color) {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        double x = pos1.getX() - playerX;
        double y = pos1.getY() - playerY;
        double z = pos1.getZ() - playerZ;

        GL11.glLineWidth(3);

        GlStateManager.disableTexture2D();

        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), 0.7f);

        VertexBuffer vb = Tessellator.getInstance().getBuffer();

        vb.setTranslation(x, y, z);

        vb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);

        vb.pos(pos2.getX() - pos1.getX(), pos2.getY() - pos1.getY(), pos2.getZ() - pos1.getZ()).endVertex();
        vb.pos(0, 0, 0).endVertex();

        Tessellator.getInstance().draw();

        vb.setTranslation(0, 0, 0);

        GlStateManager.enableTexture2D();

        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

}
