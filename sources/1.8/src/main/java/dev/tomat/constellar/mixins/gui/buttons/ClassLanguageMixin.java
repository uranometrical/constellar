package dev.tomat.constellar.mixins.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiButtonLanguage.class)
public abstract class ClassLanguageMixin extends GuiButton {
    public final ResourceLocation Buttons = new ResourceLocation("textures/gui/extra_buttons.png");

    public ClassLanguageMixin(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    /**
     * @author Tomat
     */
    @Overwrite
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(Buttons);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = 106;

            if (flag)
            {
                i += this.height;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, i, this.width, this.height);
        }
    }
}
