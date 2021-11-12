package dev.tomat.constellar.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class BackgroundPanorama {
    public static BackgroundPanorama Instance;

    public static final ResourceLocation[] TitlePanoramaPaths = new ResourceLocation[] {
            new ResourceLocation("textures/gui/title/background/panorama_0.png"),
            new ResourceLocation("textures/gui/title/background/panorama_1.png"),
            new ResourceLocation("textures/gui/title/background/panorama_2.png"),
            new ResourceLocation("textures/gui/title/background/panorama_3.png"),
            new ResourceLocation("textures/gui/title/background/panorama_4.png"),
            new ResourceLocation("textures/gui/title/background/panorama_5.png")
    };

    public DynamicTexture Viewport = new DynamicTexture(256, 256);
    public ResourceLocation Background = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("background", Viewport);
    public int Width, Height = 100;
    public int Timer = 0;
    public float Z = 0f;

    public void drawPanorama(float timerOffset) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();

        GlStateManager.loadIdentity();
        Project.gluPerspective(120f, 1f, 0.05F, 10f);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();

        GlStateManager.loadIdentity();
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.rotate(180f, 1f, 0f, 0f);
        GlStateManager.rotate(90f, 0f, 0f, 1f);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        int amount = 8;

        for (int i = 0; i < amount * amount; ++i)
        {
            GlStateManager.pushMatrix();

            float x = ((float)(i % amount) / (float) amount - 0.5F) / 64f;
            float y = ((float)(i / amount) / (float) amount - 0.5F) / 64f;
            float z = 0f;

            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(MathHelper.sin(((float)Timer + timerOffset) / 400f) * 25f + 20f, 1f, 0f, 0f);
            GlStateManager.rotate(-((float)Timer + timerOffset) * 0.1F, 0f, 1f, 0f);

            for (int j = 0; j < 6; ++j)
            {
                GlStateManager.pushMatrix();

                if (j == 1)
                    GlStateManager.rotate(90f, 0f, 1f, 0f);

                if (j == 2)
                    GlStateManager.rotate(180f, 0f, 1f, 0f);

                if (j == 3)
                    GlStateManager.rotate(-90f, 0f, 1f, 0f);

                if (j == 4)
                    GlStateManager.rotate(90f, 1f, 0f, 0f);

                if (j == 5)
                    GlStateManager.rotate(-90f, 1f, 0f, 0f);

                Minecraft.getMinecraft().getTextureManager().bindTexture(TitlePanoramaPaths[j]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

                int alpha = 255 / (i + 1);

                worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, alpha).endVertex();
                worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, alpha).endVertex();
                worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, alpha).endVertex();
                worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, alpha).endVertex();
                tessellator.draw();

                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);

        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();

        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();

        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    public void rotateAndBlurSkybox() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(Background);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

        GlStateManager.disableAlpha();
        int amount = 3;

        for (int i = 0; i < amount; i++)
        {
            float alpha = 1f / (float)(i + 1);
            int width = Width;
            int height = Height;
            float uOffset = (float)(i - amount / 2) / 256f;

            worldrenderer.pos(width, height, Z).tex(0f + uOffset, 1.0D).color(1f, 1f, 1f, alpha).endVertex();
            worldrenderer.pos(width, 0.0D, Z).tex(1f + uOffset, 1.0D).color(1f, 1f, 1f, alpha).endVertex();
            worldrenderer.pos(0.0D, 0.0D, Z).tex(1f + uOffset, 0.0D).color(1f, 1f, 1f, alpha).endVertex();
            worldrenderer.pos(0.0D, height, Z).tex(0f + uOffset, 0.0D).color(1f, 1f, 1f, alpha).endVertex();
        }

        tessellator.draw();

        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    public void render(float timerOffset) {
        Minecraft.getMinecraft().getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);

        drawPanorama(timerOffset);

        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();

        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);

        float bounds = Width > Height ? 120f / (float)Width : 120f / (float)Height;
        float boundary1 = (float)Height * bounds / 256f;
        float boundary2 = (float)Width * bounds / 256f;
        int width = Width;
        int height = Height;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, height, Z).tex(0.5F - boundary1, 0.5F + boundary2).color(1f, 1f, 1f, 1f).endVertex();
        worldrenderer.pos(width, height, Z).tex(0.5F - boundary1, 0.5F - boundary2).color(1f, 1f, 1f, 1f).endVertex();
        worldrenderer.pos(width, 0.0D, Z).tex(0.5F + boundary1, 0.5F - boundary2).color(1f, 1f, 1f, 1f).endVertex();
        worldrenderer.pos(0.0D, 0.0D, Z).tex(0.5F + boundary1, 0.5F + boundary2).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.draw();
    }
}
