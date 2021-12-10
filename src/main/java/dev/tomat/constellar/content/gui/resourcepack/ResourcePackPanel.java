package dev.tomat.constellar.content.gui.resourcepack;

import dev.tomat.common.utils.ColorUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.util.List;

public abstract class ResourcePackPanel extends GuiListExtended {
    protected final Minecraft mc;
    protected final List<ResourcePackEntry> resourcePackEntries;
    protected int scrollIndex = 0;
    protected int panelXPosition;

    public ResourcePackPanel(Minecraft mcIn, int widthIn, int heightIn, int xPos, List<ResourcePackEntry> resourcePacksIn)
    {
        super(
                mcIn, widthIn, heightIn,
                ResourcePackUtils.PackIconSize,
                heightIn - ResourcePackUtils.ResourcePackPanelBottomPadding + ResourcePackUtils.ResourcePackEntryPadding,
                ResourcePackUtils.ResourcePackEntryHeight
        );

        panelXPosition = xPos;
        mc = mcIn;
        resourcePackEntries = resourcePacksIn;
        setHasListHeader(true, (int)((float)mcIn.fontRendererObj.FONT_HEIGHT * 1.5f));
    }

    public List<ResourcePackEntry> getResourcePackEntries()
    {
        return resourcePackEntries;
    }

    protected int getSize()
    {
        return getResourcePackEntries().size();
    }

    public ResourcePackEntry getListEntry(int index)
    {
        return getResourcePackEntries().get(index);
    }

    public int getListWidth()
    {
        return width;
    }

    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
            mouseX = mouseXIn;
            mouseY = mouseYIn;
            bindAmountScrolled();

            GlStateManager.disableLighting();
            GuiUtils.resetColor();

            // panel background
            int backgroundX = panelXPosition - (ResourcePackUtils.ResourcePackPanelPadding / 2);
            int backgroundY = ResourcePackUtils.ResourcePackPanelTopPadding;
            GuiUtils.drawRectNormal(backgroundX, backgroundY,
                    ResourcePackUtils.ResourcePackEntryWidth + ResourcePackUtils.ResourcePackPanelPadding,
                    height,
                    ColorUtils.colorToInt(40, 40, 40, 100)
            );

            // the y of the first icon in the list, virtually
            int firstIconY = ResourcePackUtils.ResourcePackPanelTopPadding + (ResourcePackUtils.ResourcePackPanelPadding / 2);

            drawHeader(getPanelHeader(), panelXPosition + (ResourcePackUtils.ResourcePackEntryWidth / 2));
            drawSelectionBox(panelXPosition, firstIconY, mouseXIn, mouseYIn);
            GlStateManager.disableDepth();
    }

    protected void drawSelectionBox(int xPosition, int yPosition, int mouseX, int mouseY) {
        int packCount = getSize();

        if (scrollIndex < 0)
            scrollIndex = 0;

        // todo: 11 is the amount of packs shown on screen. unhardcode this in the morning. fuck me.
        if (scrollIndex > packCount - 12 && packCount > 12)
            scrollIndex = packCount - 12;

        for (int packIterator = 0; packIterator < packCount; ++packIterator)
        {
            //System.out.println("pack iterator: " + packIterator);
            int ySlotPosition = yPosition + (packIterator * slotHeight) - (slotHeight * scrollIndex) + ResourcePackUtils.ResourcePackPanelHeaderPadding;
            int height = slotHeight - ResourcePackUtils.ResourcePackEntryPadding;

            //System.out.println(ySlotPosition);

            getListEntry(packIterator).ySlotPosition = ySlotPosition;

            //System.out.println("ypos: " + yPosition);
            if (ySlotPosition > ResourcePackUtils.ResourcePackPanelHeaderPadding && ySlotPosition < this.height + ResourcePackUtils.ResourcePackPanelTopPadding)
                drawSlot(packIterator, xPosition, ySlotPosition, height, mouseX, mouseY);
            //int top = yPosition + headerPadding;
            //if (ySlotPosition > top && ySlotPosition < top + (5 * slotHeight))
            //drawSlot(packIterator, xPosition, ySlotPosition, height, mouseX, mouseY);
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

    public abstract String getPanelHeader();

    protected void drawHeader(String header, int centerXPos) {
        mc.fontRendererObj.drawStringWithShadow(
                header,
                centerXPos - mc.fontRendererObj.getStringWidth(header) / 2f,
                GuiUtils.DefaultTitleTopPadding + ResourcePackUtils.ResourcePackPanelHeaderPadding,
                ColorUtils.White
        );
    }

    public void handleMouseInput()
    {
        if (isMouseYWithinSlotBounds(mouseY))
        {
            int direction = Mouse.getEventDWheel();

            if (direction != 0) {
                scroll(direction <= 0);
            }
        }
    }

    private void scroll(boolean down) {
        int lastIndex = (getSize() - 1);

        //System.out.println(getListEntry(lastIndex).ySlotPosition);
        //System.out.println(getListEntry(0).ySlotPosition);

        if (down) {
            if (getListEntry(lastIndex).ySlotPosition > (ResourcePackUtils.ResourcePackPanelTopPadding + height - ResourcePackUtils.PackIconSize)) {
                scrollIndex += 1;
            }
        }
        else {
            if (getListEntry(0).ySlotPosition < ResourcePackUtils.ResourcePackPanelTopPadding) {
                if (scrollIndex != getSize())
                    scrollIndex -= 1;
            }
        }
    }
}
