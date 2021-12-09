package dev.tomat.constellar.content.gui.resourcepack;

import dev.tomat.common.utils.ColorUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.util.List;

public abstract class ResourcePackPanel extends GuiListExtended {
    protected final Minecraft mc;
    protected final List<ResourcePackEntry> resourcePacks;

    public ResourcePackPanel(Minecraft mcIn, int widthIn, int heightIn, List<ResourcePackEntry> resourcePacksIn)
    {
        super(
                mcIn, widthIn, heightIn,
                ResourcePackUtils.PackIconSize,
                heightIn - ResourcePackUtils.ResourcePackPanelBottomPadding + ResourcePackUtils.ResourcePackEntryPadding,
                ResourcePackUtils.ResourcePackEntryHeight
        );

        mc = mcIn;
        resourcePacks = resourcePacksIn;
        setHasListHeader(true, (int)((float)mcIn.fontRendererObj.FONT_HEIGHT * 1.5f));
    }

    protected void drawHeader(String header, int xPosition) {
        mc.fontRendererObj.drawStringWithShadow(
                header,
                xPosition - mc.fontRendererObj.getStringWidth(header) / 2f,
                GuiUtils.DefaultTitleTopPadding + ResourcePackUtils.ResourcePackPanelHeaderPadding,
                ColorUtils.White
        );
    }

    public List<ResourcePackEntry> getResourcePacks()
    {
        return this.resourcePacks;
    }

    protected int getSize()
    {
        return this.getResourcePacks().size();
    }

    public ResourcePackEntry getListEntry(int index)
    {
        return this.getResourcePacks().get(index);
    }

    public int getListWidth()
    {
        return this.width;
    }

    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
            mouseX = mouseXIn;
            mouseY = mouseYIn;
            bindAmountScrolled();

            GlStateManager.disableLighting();
            GlStateManager.color(1f, 1f, 1f, 1f);

            // panel background
            int backgroundX = left - (ResourcePackUtils.ResourcePackPanelPadding / 2);
            int backgroundY = top - (ResourcePackUtils.ResourcePackPanelPadding / 2);
            Gui.drawRect(backgroundX, backgroundY,
                    backgroundX + ResourcePackUtils.ResourcePackEntryWidth + ResourcePackUtils.ResourcePackPanelPadding,
                    backgroundY + height + ResourcePackUtils.ResourcePackPanelPadding,
                    ColorUtils.colorToInt(40, 40, 40, 100)
            );

            // x center of list, I think
            int k = left + (width / 2) - (ResourcePackUtils.ResourcePackEntryWidth / 2);
            int l = top + (ResourcePackUtils.ResourcePackPanelPadding / 2) - (int)amountScrolled;

            drawSelectionBox(k, l, mouseXIn, mouseYIn);
            GlStateManager.disableDepth();
    }

    protected void drawSelectionBox(int xPosition, int yPosition, int mouseX, int mouseY) {
        int packCount = getSize();

        for (int packIterator = 0; packIterator < packCount; ++packIterator)
        {
            int ySlotPosition = yPosition + (packIterator * slotHeight) + ResourcePackUtils.ResourcePackPanelHeaderPadding;
            int height = slotHeight - ResourcePackUtils.ResourcePackEntryPadding;

            //int top = yPosition + headerPadding;
            //if (ySlotPosition > top && ySlotPosition < top + (5 * slotHeight))
            drawSlot(packIterator, xPosition, ySlotPosition, height, mouseX, mouseY);
        }
    }
/*
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
    }*/

    public void handleMouseInput()
    {
        if (this.isMouseYWithinSlotBounds(this.mouseY))
        {
            int direction = Mouse.getEventDWheel();
            int scrollAmount = 0;

            if (direction != 0) {
                if (direction > 0) {
                    getListEntry(scrollAmount).visible = false;
                }
                else {
                    getListEntry(scrollAmount).visible = true;
                }
            }


            /*
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
            }*/
        }
    }
}
