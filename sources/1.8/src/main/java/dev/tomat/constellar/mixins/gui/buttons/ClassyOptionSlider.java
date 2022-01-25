package dev.tomat.constellar.mixins.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiOptionSlider.class)
public abstract class ClassyOptionSlider extends GuiButton {
    @Shadow public boolean dragging;
    @Shadow private float sliderValue;
    @Shadow private GameSettings.Options options;
    public final ResourceLocation Buttons = new ResourceLocation("textures/gui/buttons.png");

    public ClassyOptionSlider(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    /**
     * @author Tomat
     */
    @Overwrite
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (visible)
        {
            if (dragging)
            {
                sliderValue = (float)(mouseX - (xPosition + 4)) / (float)(width - 8);
                sliderValue = MathHelper.clamp_float(sliderValue, 0f, 1f);
                float f = options.denormalizeValue(sliderValue);
                mc.gameSettings.setOptionFloatValue(options, f);
                sliderValue = options.normalizeValue(f);
                displayString = mc.gameSettings.getKeyBinding(options);
            }

            mc.getTextureManager().bindTexture(Buttons);
            GlStateManager.color(1f, 1f, 1f, 1f);
            drawTexturedModalRect(xPosition + (int)(sliderValue * (float)(width - 8)), yPosition, 0, 66, 4, 20);
            drawTexturedModalRect(xPosition + (int)(sliderValue * (float)(width - 8)) + 4, yPosition, 196, 66, 4, 20);
        }
    }
}
