package dev.tomat.constellar.gui.modules;

import dev.tomat.constellar.gui.GuiUtils;
import dev.tomat.constellar.modules.IModule;
import dev.tomat.constellar.utilities.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiModule extends GuiButton {
    public static final ResourceLocation Buttons = new ResourceLocation("textures/gui/extra_buttons.png");
    public static final int PADDING = 5;
    public static final int PADDING_Y = 90;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 120;

    public final IModule Module;
    public int CountX;
    public int CountY;
    public boolean ToggleButtonHovered;

    public GuiModule(int buttonId, int windowWidth, int countX, int countY, IModule module) {
        super(buttonId, getX(windowWidth, countX), getY(countY), "");
        Module = module;
        CountX = countX;
        CountY = countY;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!visible)
            return;

        GuiUtils.drawRectNormal(xPosition, yPosition, WIDTH, HEIGHT, ColorUtils.colorToInt(50, 50, 50, 100), GuiUtils.PosMode.CENTER);

        int buttonWidth = WIDTH - 8;
        int buttonXPos = xPosition - (buttonWidth / 2);
        int buttonYPos = yPosition + (HEIGHT / 2) - 34;

        ToggleButtonHovered = mouseX >= buttonXPos && mouseY >= buttonYPos && mouseX < buttonXPos + buttonWidth && mouseY < buttonYPos + height;

        mc.getTextureManager().bindTexture(Buttons);

        int hover = getHoverState(ToggleButtonHovered);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);

        drawTexturedModalRect(buttonXPos, buttonYPos, 0, 46 + hover * 20, buttonWidth / 2, height);
        drawTexturedModalRect(buttonXPos + buttonWidth / 2, buttonYPos, 200 - buttonWidth / 2, 46 + hover * 20, buttonWidth / 2, height);
        mouseDragged(mc, mouseX, mouseY);

        drawCenteredString(
                mc.fontRendererObj,
                Module.getModuleStatus().toString(),
                buttonXPos + buttonWidth / 2,
                buttonYPos + (height - 8) / 2,
                Module.getModuleStatus().getColor()
        );

        int textYPos = yPosition - (HEIGHT / 2);

        drawCenteredString(
                mc.fontRendererObj,
                I18n.format(Module.getKey()),
                buttonXPos + buttonWidth / 2,
                textYPos + (height - 8) / 2,
                ColorUtils.colorToInt(255, 255, 255, 255)
        );

    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (!ToggleButtonHovered)
            return super.mousePressed(mc, mouseX, mouseY);

        Module.toggle();

        displayString = Module.getModuleStatus().toString();

        return super.mousePressed(mc, mouseX, mouseY);
    }

    public static int getX(int windowWidth, int countX) {
        int width = windowWidth / 2;

        switch (countX) {
            case 0:
                return width - (PADDING * 2) - (WIDTH * 2);

            case 1:
                return width - PADDING - WIDTH;

            case 2:
                return width;

            case 3:
                return width + PADDING + WIDTH;

            case 4:
                return width + (PADDING * 2) + (WIDTH * 2);
        }

        return 0;
    }

    public static int getY(int countY) {
        return PADDING_Y + (PADDING * countY) + (HEIGHT * countY);
    }
}
