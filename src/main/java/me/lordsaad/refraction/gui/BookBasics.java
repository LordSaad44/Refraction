package me.lordsaad.refraction.gui;

import com.google.common.io.Files;
import me.lordsaad.refraction.Utils;
import net.minecraft.client.gui.FontRenderer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;

/**
 * Created by Saad on 4/19/2016.
 */
public class BookBasics extends BookBase {

    static int guiWidth = 146, guiHeight = 180;
    static int left, top;

    @Override
    public void initGui() {
        super.initGui();
        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer renderer = fontRendererObj;
        renderer.setUnicodeFlag(true);
        renderer.setBidiFlag(true);

        List<String> txt = null;
        try {
            txt = Files.readLines(new File(getClass().getResource("assets/refraction/documentation/Basics.txt").getFile()), UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (txt != null) {
            int height = 0;
            for (String lines : txt) {
                switch (lines) {
                    case "/n":
                        height++;
                        break;
                    case "/b":
                        fontRendererObj.drawString("---------------------------------", left + 20, top + 15 +
                                (height * 8), 0, false);
                        height++;
                        break;
                    default:
                        for (String pads : Utils.padString(lines, 35)) {
                            fontRendererObj.drawString(pads, left + 20, top + 15 + (height * 8), 0, false);
                            height++;
                        }
                        break;
                }
            }
        }
        mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect((width / 2) - 65, (float) (top - 20), 20, 182, 133, 14);
        renderer.setUnicodeFlag(false);
        renderer.setBidiFlag(false);
        renderer.drawString("Physics Book", (width / 2) - 30, (float) (top - 20) + 4, 0, false);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
