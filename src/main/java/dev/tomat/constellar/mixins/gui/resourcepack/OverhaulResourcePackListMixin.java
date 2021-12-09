/*package dev.tomat.constellar.mixins.gui.resourcepack;

import com.google.common.collect.Lists;
import dev.tomat.common.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiResourcePackList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.ResourcePackListEntry;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(GuiResourcePackList.class)
public abstract class OverhaulResourcePackListMixin extends GuiListExtended {
    protected List<ResourcePackListEntry> visibleElements;

    public OverhaulResourcePackListMixin(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        visibleElements = Lists.newArrayList();
    }

    @Override
    public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
        boolean visible = field_178041_q; // weird obfed name, I think this is visible
        if (visible)
        {
            mouseX = mouseXIn;
            mouseY = mouseYIn;
            bindAmountScrolled();

            GlStateManager.disableLighting();
            GlStateManager.color(1f, 1f, 1f, 1f);

            // background underneath everything
            int outerPadding = 4;
            int margin = 9;
            int backgroundX = left - outerPadding;
            int backgroundY = top - outerPadding;
            Gui.drawRect(
                    backgroundX,
                    backgroundY,
                    // list width, minus margin (9),
                    // list width - 5 + 8
                    backgroundX + getListWidth() - margin + (outerPadding * 2),
                    backgroundY + height + (outerPadding * 2),
                    ColorUtils.colorToInt(40, 40, 40, 100)
            );

            // x center of list, I think
            int k = left + (width / 2) - (getListWidth() / 2);
            int l = top + (4) - (int)amountScrolled;

            drawSelectionBox(k, l, mouseXIn, mouseYIn);
            GlStateManager.disableDepth();
        }
    }
    
    @Override
    protected void drawSelectionBox(int xPosition, int yPosition, int mouseX, int mouseY) {
        int packCount = getSize();

        for (int packIterator = 0; packIterator < packCount; ++packIterator)
        {
            int ySlotPosition = yPosition + (packIterator * slotHeight) + headerPadding;
            // headerPadding is 13
            int height = slotHeight - 4; // todo: unhardcode/unmagic

            //int top = yPosition + headerPadding;
            //if (ySlotPosition > top && ySlotPosition < top + (5 * slotHeight))
                drawSlot(packIterator, xPosition, ySlotPosition, height, mouseX, mouseY);
        }
    }

    // used for scrollbar
    @Override
    public void actionPerformed(GuiButton button) {
        int scrollUpId = 7;
        int scrollDownId = 8;

        if (button.enabled)
        {
            if (button.id == scrollUpId)
            {
                //this.amountScrolled -= (float)(this.slotHeight * 2 / 3);
                this.amountScrolled -= (float)(this.slotHeight);
                this.initialClickY = -2;
                this.bindAmountScrolled();
            }
            else if (button.id == scrollDownId)
            {
                //this.amountScrolled += (float)(this.slotHeight * 2 / 3);
                this.amountScrolled += (float)(this.slotHeight * 3);
                this.initialClickY = -2;
                this.bindAmountScrolled();
            }
        }
    }

    @Override
    public void handleMouseInput()
    {
        if (this.isMouseYWithinSlotBounds(this.mouseY))
        {
            int direction = Mouse.getEventDWheel();

            // if moving scroll-wheel
            if (direction != 0)
            {
                // if moving down, set direction to down
                if (direction > 0)
                {
                    direction = -1;
                }
                // else if moving up, set direction to up
                else {
                    direction = 1;
                }

                amountScrolled += (float)(direction * slotHeight);
            }
        }
    }
}
*/