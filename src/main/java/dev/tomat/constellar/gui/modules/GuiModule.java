package dev.tomat.constellar.gui.modules;

import dev.tomat.constellar.gui.GuiUtils;
import dev.tomat.constellar.modules.IModule;
import dev.tomat.constellar.utilities.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiModule extends Gui {
    public static class ModuleButton extends GuiButton {
        public static final ResourceLocation Buttons = new ResourceLocation("textures/gui/extra_buttons.png");

        public final IModule Module;

        public ModuleButton(int buttonId, int x, int y, IModule module) {
            super(buttonId, x, y, WIDTH - 4, 20, "null");

            Module = module;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

            if (visible)
            {
                mc.getTextureManager().bindTexture(Buttons);

                int hover = getHoverState(hovered);

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);

                drawTexturedModalRect(xPosition, yPosition, 0, 46 + hover * 20, width / 2, height);
                drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + hover * 20, width / 2, height);
                mouseDragged(mc, mouseX, mouseY);

                drawCenteredString(mc.fontRendererObj, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, Module.getModuleStatus().getColor());
            }
        }

        @Override
        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            Module.toggle();

            displayString = Module.getModuleStatus().toString();

            return super.mousePressed(mc, mouseX, mouseY);
        }
    }

    public static final int PADDING = 5;
    public static final int PADDING_Y = 30;
    public static final int WIDTH = 75;
    public static final int HEIGHT = 100;

    public final IModule Module;
    public final GuiButton Button;
    public int CountX;
    public int CountY;
    public int XPos;
    public int YPos;

    public GuiModule(int buttonId, int windowWidth, int countX, int countY, IModule module) {
        Module = module;
        CountX = countX;
        CountY = countY;
        Button = new ModuleButton(buttonId, XPos = getX(windowWidth, countX), (YPos = getY(countY)) - 20, module);
    }

    public void updatePositions(int width) {
        XPos = getX(width, CountX);
        YPos = getY(CountY);

        Button.xPosition = XPos;
        Button.yPosition = YPos - 20;
    }

    public void draw() {
        GuiUtils.drawRectNormal(XPos, YPos, WIDTH, HEIGHT, ColorUtils.colorToInt(50, 50, 50, 100), GuiUtils.PosMode.CENTER);
    }

    public static int getX(int windowWidth, int countX) {
        int width = windowWidth / 2;

        switch (countX) {
            case 0:
                return WIDTH - (PADDING * 2) - (WIDTH * 2);

            case 1:
                return width - PADDING - WIDTH;

            case 2:
                return width;

            case 3:
                return width - PADDING + WIDTH;

            case 4:
                return WIDTH + (PADDING * 2) + (WIDTH * 2);
        }

        return 0;
    }

    public static int getY(int countY) {
        return PADDING_Y - (PADDING * countY) - (HEIGHT * countY);
    }
}
