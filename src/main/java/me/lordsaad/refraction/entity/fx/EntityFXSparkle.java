package me.lordsaad.refraction.entity.fx;

import me.lordsaad.refraction.Refraction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Saad on 5/8/2016.
 */
public class EntityFXSparkle extends EntityFX {

    public double colorR = 0;
    public double colorG = 0;
    public double colorB = 0;
    public int lifetime = 8;

    public ResourceLocation texture = new ResourceLocation(Refraction.MODID, "entity/sparkle.png");

    public EntityFXSparkle(World worldIn, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
        super(worldIn, x, y, z, 0, 0, 0);
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        if (this.colorR > 1.0) this.colorR = this.colorR / 255.0;
        if (this.colorG > 1.0) this.colorG = this.colorG / 255.0;
        if (this.colorB > 1.0) this.colorB = this.colorB / 255.0;
        this.setRBGColorF(1, 1, 1);
        this.particleMaxAge = 8;
        this.particleGravity = 0.0f;
        this.xSpeed = (vx - x) / (double) this.particleMaxAge;
        this.ySpeed = (vy - y) / (double) this.particleMaxAge;
        this.zSpeed = (vz - z) / (double) this.particleMaxAge;
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
        this.setParticleTexture(sprite);
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        float lifeCoeff = ((float) this.particleMaxAge - (float) this.particleAge) / (float) this.particleMaxAge;
        this.particleRed = (float) colorR * (1.0f - lifeCoeff) + lifeCoeff;
        this.particleGreen = (float) colorG * (1.0f - lifeCoeff) + lifeCoeff;
        this.particleBlue = (float) colorB * (1.0f - lifeCoeff) + lifeCoeff;
        this.particleAlpha = lifeCoeff;
    }
}