package me.lordsaad.refraction;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.lordsaad.refraction.gui.GuiContentPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    public static void drawItemStack(final ItemStack itemStack, final int x, final int y) {
        if (itemStack != null && itemStack.getItem() != null) {
            GlStateManager.enableRescaleNormal();
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(itemStack, x, y);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
        }
    }

    public static ArrayList<String> padString(String string, int stringSize) {
        ArrayList<String> lines = new ArrayList<>();
        if (string != null)
            for (String line : WordUtils.wrap(string, stringSize).split("\n")) lines.add(line.trim());
        return lines;
    }

    public static HashMap<Integer, ArrayList<String>> splitTextToPages(HashMap<Integer, ArrayList<String>> pages, InputStream stream, GuiContentPage page) {
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
                int requiredPage = Integer.parseInt(line.substring(line.indexOf("/r:") + 3).split(";")[0]);
                String itemName = line.substring(line.indexOf("/r:") + 3).split(";")[1];
                String comment = line.substring(line.indexOf("/r:") + 3).split(";")[2];
                Item item = Item.getByNameOrId(itemName);

                HashMap<Item, String> temp = new HashMap<>();
                temp.put(item, comment);
                GuiContentPage.recipes.put(requiredPage, temp);
            } else {
                if (line.startsWith("*")) {
                    line = line.substring(line.indexOf("*") + 1);
                    ArrayList<String> pads = Utils.padString(line, 30);
                    for (String padded : pads) {
                        if (pages.get(pagenb).size() < 18) {
                            pages.get(pagenb).add(ChatFormatting.ITALIC + padded);
                        } else {
                            pagenb++;
                            pages.putIfAbsent(pagenb, new ArrayList<>());
                            pages.get(pagenb).add(ChatFormatting.ITALIC + padded);
                        }
                    }
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
        }
        return pages;
    }

    public static void drawConnection(BlockPos pos1, BlockPos pos2, Color color) {
        GlStateManager.pushMatrix();

        GL11.glLineWidth(1);

        GlStateManager.disableTexture2D();

        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), 0.7f);

        GlStateManager.translate(0.5, 0.7, 0.5);

        VertexBuffer vb = Tessellator.getInstance().getBuffer();

        vb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);

        vb.pos(pos2.getX() - pos1.getX(), pos2.getY() - pos1.getY(), pos2.getZ() - pos1.getZ()).endVertex();
        vb.pos(0, 0, 0).endVertex();

        Tessellator.getInstance().draw();

        GlStateManager.enableTexture2D();

        GlStateManager.popMatrix();
    }

}
