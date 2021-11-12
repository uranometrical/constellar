package dev.tomat.constellar.mixins.gui.buttons;

import dev.tomat.constellar.gui.LockIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLockIconButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiLockIconButton.class)
public abstract class ClassLockMixin extends GuiButton {
    public final ResourceLocation Buttons = new ResourceLocation("textures/gui/buttons.png");

    @Shadow private boolean field_175231_o;

    public ClassLockMixin(int buttonId, int x, int y, String buttonText) {
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
            LockIcon guilockiconbutton$icon;

            if (field_175231_o)
            {
                if (!this.enabled)
                {
                    guilockiconbutton$icon = LockIcon.LOCKED_DISABLED;
                }
                else if (flag)
                {
                    guilockiconbutton$icon = LockIcon.LOCKED_HOVER;
                }
                else
                {
                    guilockiconbutton$icon = LockIcon.LOCKED;
                }
            }
            else if (!this.enabled)
            {
                guilockiconbutton$icon = LockIcon.UNLOCKED_DISABLED;
            }
            else if (flag)
            {
                guilockiconbutton$icon = LockIcon.UNLOCKED_HOVER;
            }
            else
            {
                guilockiconbutton$icon = LockIcon.UNLOCKED;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, guilockiconbutton$icon.func_178910_a(), guilockiconbutton$icon.func_178912_b(), this.width, this.height);
        }
    }
}
