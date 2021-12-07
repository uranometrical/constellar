package dev.tomat.constellar.content.gui.modules;

import dev.tomat.common.utils.MathUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import dev.tomat.constellar.content.gui.classy.ClassyGuiButton;
import dev.tomat.constellar.core.modules.IModule;
import dev.tomat.common.utils.ColorUtils;
import dev.tomat.constellar.mixins.gui.buttons.ClassyButtonMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

public class GuiModule extends GuiButton {
    public static final ResourceLocation NewButtonsTexture = new ResourceLocation("textures/gui/extra_buttons.png");
    public static final int PADDING = 4;
    public static final int PADDING_Y = 90;
    public static final int PADDING_X = 160; // for max X, not used in rendering
    public static final int WIDTH = 100;
    public static final int HEIGHT = 120;

    public final IModule Module;
    public int CountX;
    public int CountY;
    public boolean ToggleButtonHovered;
    public float ButtonWidthScale = 1f;

    public GuiModule(int buttonId, int windowWidth, int countX, int countY, IModule module) {
        super(buttonId, getX(windowWidth, countX), getY(countY), "");
        Module = module;
        CountX = countX;
        CountY = countY;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        // don't draw anything if the module gui isnt visible
        if (!visible)
            return;

        // xPosition and yPosition are the center of the module I think
        // adding to Y positions make them go DOWN... what the fuck???
        // for some reason the rect draws with the dimensions of 100, 110 when the provided dimensions are 100, 120.

        // magic number variables
        int someWeirdIssueThatICantBeBotheredWorkingOut = 10;
        int rectangleColor = ColorUtils.colorToInt(50, 50, 50, 100);
        int buttonSidePadding = 4;
        int buttonBottomPadding = 4;
        int moduleStatusButtonTopPadding = 6;
        int textTopPadding = 4;
        
        // draw base rectangle
        GuiUtils.drawRectNormal(xPosition, yPosition, WIDTH, HEIGHT, rectangleColor, GuiUtils.PosMode.CENTER);

        // set the icon color
        GlStateManager.color(1f, 1f, 1f);
        // bind the icon texture
        mc.getTextureManager().bindTexture(Module.getImageLocation());
        // draw the module icon
        drawTexturedModalRect(xPosition - WIDTH / 2, yPosition - HEIGHT / 2, 0, 0, WIDTH, HEIGHT - someWeirdIssueThatICantBeBotheredWorkingOut);

        // width of the button is the size of the rectangle minus 4 pixels of padding on each side
        int buttonWidth = WIDTH - (buttonSidePadding * 2);
        // default button height (20)
        int buttonHeight = GuiUtils.DefaultButtonHeight;
        // center the x position of the button
        int buttonXPos = xPosition - (WIDTH / 2);
        // middle of module, go down half module (the bottom), go up by the height of the button, go up by 8 pixels (the padding)
        int buttonYPos = (yPosition - someWeirdIssueThatICantBeBotheredWorkingOut) + (HEIGHT / 2) - buttonHeight - buttonBottomPadding;

        // set the bool if the mouse is hovering over the button
        // make sure this uses original button size and not the scaled button size
        ToggleButtonHovered = mouseX >= buttonXPos + buttonSidePadding &&
                mouseY >= buttonYPos &&
                mouseX < buttonXPos + buttonWidth + buttonSidePadding &&
                mouseY < buttonYPos + buttonHeight;

        // check if the mouse is hovered and if so
        // expand the button outwards on both horizontal directions
        // with lerp, so it looks like a smooth animation
        if (ToggleButtonHovered) {
            ButtonWidthScale = MathUtils.lerp(ButtonWidthScale, 1.05f, 0.25f);
        } else {
            ButtonWidthScale = MathUtils.lerp(ButtonWidthScale, 1f, 0.25f);
        }
        
        // button width scaling
        int buttonScaledWidth = (int)(buttonWidth * ButtonWidthScale);
        // button x position, go right by half of the scaled width minus the module width
        float buttonScaledX = buttonXPos + ((WIDTH - buttonScaledWidth) / 2f);

        // bind the new buttons texture to the texture manager
        mc.getTextureManager().bindTexture(NewButtonsTexture);
        // still don't know what blend does
        GuiUtils.blend();

        // get the state of mouse hover
        int hover = getHoverState(ToggleButtonHovered);

        // draw the button texture, scaled with hover
        // todo: un-magic 46, not that important tho. I think this is just the position on the spritesheet
        drawTexturedModalRect(buttonScaledX, (float)buttonYPos, 0, 46 + hover * buttonHeight, buttonScaledWidth, buttonHeight);
        // no idea what this does
        mouseDragged(mc, mouseX, mouseY);

        // draw the module status, ENABLED or DISABLED
        drawCenteredString(
                mc.fontRendererObj,
                Module.getModuleStatus().toString(),
                xPosition,
                // y pos of button, down 6 pixels to aim to center the text, due to afaik no way to get the size of the text
                buttonYPos + moduleStatusButtonTopPadding,
                // green or red
                Module.getModuleStatus().getColor()
        );

        // center of module, go up by 55, go down by 8
        int textYPos = yPosition - ((HEIGHT - someWeirdIssueThatICantBeBotheredWorkingOut) / 2) + textTopPadding;

        // draw the module name just below the top of the module
        drawCenteredString(
                mc.fontRendererObj,
                I18n.format(Module.getKey()),
                xPosition,
                textYPos,
                ColorUtils.White
        );
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (!ToggleButtonHovered)
            return super.mousePressed(mc, mouseX, mouseY);

        Module.toggle();

        displayString = Module.getModuleStatus().toString();

        GuiUtils.playButtonPressSound(mc.getSoundHandler());

        return super.mousePressed(mc, mouseX, mouseY);
    }

    public static int getX(int windowWidth, int countX) {
        int maxX = GuiModuleContainer.getMaxX(windowWidth);
        int width = windowWidth / 2;

        if (maxX % 2 == 0) {
            // TODO: Allow even numbers. I CBA to figure out the math RN.
            return 0;
        }
        else {
            int half = maxX / 2;
            int mult = maxX - countX;

            if (countX < half) {
                mult -= half + 1;
                return width - (PADDING * (mult)) - (WIDTH * (mult));
            }
            else if (countX > half) {
                mult -= half * 2;
                mult = Math.abs(mult) - 1;

                // TODO: Figure out what's wrong with my math (remove this)
                if (maxX < 5)
                    mult++;

                return width + (PADDING * (mult)) + (WIDTH * (mult));
            }
            else {
                return width;
            }
        }
    }

    public static int getY(int countY) {
        return PADDING_Y + (PADDING * countY) + (HEIGHT * countY);
    }
}
