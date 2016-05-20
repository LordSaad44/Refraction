package me.lordsaad.refraction.entity.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Saad on 5/8/2016.
 */
public class EntityFXSparkle extends EntityFX {

    public double colorR = 0;
    public double colorG = 0;
    public double colorB = 0;
    public int lifetime = 8;
    public ResourceLocation texture = new ResourceLocation("refraction:textures/entity/sparkle.png");
    Random random = new Random();

    public EntityFXSparkle(World worldIn, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
        super(worldIn, x, y, z, 0, 0, 0);
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        if (this.colorR > 1.0)
            this.colorR = this.colorR / 255.0;
        if (this.colorG > 1.0)
            this.colorG = this.colorG / 255.0;
        if (this.colorB > 1.0)
            this.colorB = this.colorB / 255.0;
        particleAlpha = 0.5F;
        this.particleMaxAge = 10;
        this.xSpeed = vx;
        this.ySpeed = vy;
        this.zSpeed = vz;
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
        this.setParticleTexture(sprite);
    }

    @Override
    public boolean func_187111_c() {
        return true;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.xSpeed *= 0.3;
        this.ySpeed *= 0.3;
        this.zSpeed *= 0.3;
        // if (random.nextInt(4) >= 2 && this.particleAge > 0) this.particleAge--;
        float lifeCoeff = ((float) this.particleMaxAge - (float) this.particleAge) / (float) this.particleMaxAge;
        this.particleRed = (float) colorR * (1.0f - lifeCoeff) + lifeCoeff;
        this.particleGreen = (float) colorG * (1.0f - lifeCoeff) + lifeCoeff;
        this.particleBlue = (float) colorB * (1.0f - lifeCoeff) + lifeCoeff;
        this.particleAlpha = lifeCoeff;
        this.particleScale = lifeCoeff;
    }
}