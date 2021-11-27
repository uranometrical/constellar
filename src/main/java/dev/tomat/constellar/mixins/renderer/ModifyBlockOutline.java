package dev.tomat.constellar.mixins.renderer;

import dev.tomat.constellar.Constellar;
import dev.tomat.constellar.core.modules.IModule;
import dev.tomat.constellar.core.modules.ModuleNotFoundException;
import dev.tomat.constellar.core.modules.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.time.Instant;


@Mixin(RenderGlobal.class)
public class ModifyBlockOutline {
    @Shadow private WorldClient theWorld;

    /**
     * @author Metacinnabar
     */
    @Overwrite
    public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int execute, float partialTicks) throws ModuleNotFoundException {
        IModule blockOutlineModule = Constellar.Modules.getModule(ModuleType.BlockOutline);

        if (execute == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            GlStateManager.enableBlend();
            // wtf do these magic numbers mean
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

            if (!blockOutlineModule.isDisabled()) {

                // todo: module specific settings
                // chroma boolean button
                float alpha = 1f; // slider
                float width = 10f; // slider
                int chromaSpeed = 1; // slider

                // I barely know how this works tbh lol
                long precision = (10000L / chromaSpeed);
                float hue = System.currentTimeMillis() % precision / (float)precision;

                // todo: module specific settings
                float saturation = 0.8f; // slider
                float brightness = 0.8f; // slider

                Color color = Color.getHSBColor(hue, saturation, brightness);
                GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);
                GL11.glLineWidth(width);
            }
            else
            {
                GlStateManager.color(0f, 0f, 0f, 0.4f);
                GL11.glLineWidth(2.0F);
            }

            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);

            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            Block block = this.theWorld.getBlockState(blockpos).getBlock();

            // bool to render outside border?
            if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockpos))
            {
                block.setBlockBoundsBasedOnState(this.theWorld, blockpos);
                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
                RenderGlobal.drawSelectionBoundingBox(block.getSelectedBoundingBox(this.theWorld, blockpos).offset(-d0, -d1, -d2).expand(0020000000949949026D, 0020000000949949026D, 0020000000949949026D));
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }
}
