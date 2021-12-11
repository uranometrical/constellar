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
                heightIn - 10 + ResourcePackUtils.ResourcePackEntryHeight, // todo: why is minecraft like this?
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
            height - 10, // todo: why is minecraft like this?
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
        int shownPackCount = ResourcePackUtils.getResourcePackEntryCountFromPanelHeight(height);

        if (scrollIndex < 0)
            scrollIndex = 0;

        if (scrollIndex > packCount - shownPackCount && packCount > shownPackCount)
            scrollIndex = packCount - shownPackCount;

        for (int packIterator = 0; packIterator < packCount; ++packIterator)
        {
            // top of the panel, plus the padding between the top of the top entry and the top of the panel,
            // plus the pack iterator multiplied by the height of a slot, minus the number of packs that have been
            // scrolled down by multiplied by the slot height
            int ySlotPosition = yPosition + ResourcePackUtils.ResourcePackPanelHeaderPadding + (packIterator * slotHeight) - (scrollIndex * slotHeight);
            int rawHeight = slotHeight - ResourcePackUtils.ResourcePackEntryPadding;

            getListEntry(packIterator).ySlotPosition = ySlotPosition;

            if (ySlotPosition > (ResourcePackUtils.ResourcePackPanelHeaderPadding + ResourcePackUtils.ResourcePackPanelTopPadding) && ySlotPosition < height)
                drawSlot(packIterator, xPosition, ySlotPosition, rawHeight, mouseX, mouseY);
        }
    }

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

        if (down) {
            if (getListEntry(lastIndex).ySlotPosition > (ResourcePackUtils.ResourcePackPanelTopPadding + height)) {
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

    public int getSlotIndexFromScreenCoords(int mouseX, int mouseY)
    {
        // if in the panel rectangle
        if (mouseX >= panelXPosition && mouseX < panelXPosition + ResourcePackUtils.ResourcePackEntryWidth)
        {
            // this seems to always be off by 1 pixel somewhere.. wtf?
            // todo: look into this please (tomat because i am DONE with resource packs)
            int index = ((mouseY - getTopEntryYPos()) / slotHeight) + scrollIndex;

            // check if in bounds
            if (index >= 0 && index < getSize() && mouseY >= getTopEntryYPos())
                return index;
        }

        return -1;
    }

    protected void drawSlot(int entryIndex, int xPosition, int yPosition, int height, int mouseX, int mouseY)
    {
        getListEntry(entryIndex).drawEntry(entryIndex, xPosition, yPosition, getListWidth(), height, mouseX, mouseY,
                getSlotIndexFromScreenCoords(mouseX, mouseY) == entryIndex);
    }

    public static int getTopEntryYPos() {
        return ResourcePackUtils.ResourcePackPanelTopPadding +
                ResourcePackUtils.ResourcePackPanelHeaderPadding +
                (ResourcePackUtils.ResourcePackEntryPadding / 2);
    }

    public boolean isMouseYWithinSlotBounds(int mouseY)
    {
        return
                mouseY >= top &&
                mouseY <= bottom &&
                mouseX >= left &&
                mouseX <= right;
    }
}
