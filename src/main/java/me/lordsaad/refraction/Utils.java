package me.lordsaad.refraction;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

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
}
