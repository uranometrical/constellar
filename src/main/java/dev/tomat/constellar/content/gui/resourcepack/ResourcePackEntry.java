package dev.tomat.constellar.content.gui.resourcepack;

import dev.tomat.common.utils.ColorUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import java.util.List;

public abstract class ResourcePackEntry implements GuiListExtended.IGuiListEntry {
    private static final IChatComponent IncompatibleTranslation = new ChatComponentTranslation("resourcePack.incompatible");
    private static final IChatComponent IncompatibleOldTranslation = new ChatComponentTranslation("resourcePack.incompatible.old");
    private static final IChatComponent IncompatibleNewTranslation = new ChatComponentTranslation("resourcePack.incompatible.new");

    protected final Minecraft mc;
    protected final ResourcePackScreen resourcePackScreen;
    public int ySlotPosition;

    public ResourcePackEntry(ResourcePackScreen resourcePackScreenIn)
    {
        mc = Minecraft.getMinecraft();
        resourcePackScreen = resourcePackScreenIn;
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean hovering) {
        PackCompatibility compatibility = getCompatibility();

        int overlayX = x - (ResourcePackUtils.ResourcePackEntryPadding / 2);
        int overlayY = y - (ResourcePackUtils.ResourcePackEntryPadding / 2);

        // draw red overlay if pack is incompatible
        if (compatibility != PackCompatibility.Compatible) {
            GuiUtils.resetColor();
            // draw red rectangle around pack
            GuiUtils.drawRectNormal(overlayX, overlayY,
                    listWidth + ResourcePackUtils.ResourcePackEntryPadding,
                    slotHeight + ResourcePackUtils.ResourcePackEntryPadding,
                    ColorUtils.colorToInt(120, 0, 0, 100)
            );
        }

        // hover gray rectangle overlay
        if (hovering) {
            GuiUtils.drawRectNormal(overlayX, overlayY,
                    listWidth + ResourcePackUtils.ResourcePackEntryPadding,
                    slotHeight + ResourcePackUtils.ResourcePackEntryPadding,
                    ColorUtils.colorToInt(40, 40, 40, 100)
            );
        }

        // bind the pack icon, reset the color, then draw the bound texture
        bindPackIcon();
        GuiUtils.resetColor();
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0,
                ResourcePackUtils.PackIconSize, ResourcePackUtils.PackIconSize,
                ResourcePackUtils.PackIconSize, ResourcePackUtils.PackIconSize
        );

        String packTitle = getPackTitle();
        String packDescription = getPackDescription();

        if ((mc.gameSettings.touchscreen || hovering) && isPackMovable()) {
            if (compatibility == PackCompatibility.IncompatibleOld) {
                packTitle = IncompatibleTranslation.getFormattedText();
                packDescription = IncompatibleOldTranslation.getFormattedText();
            }
            else if (compatibility == PackCompatibility.IncompatibleNew) {
                packTitle = IncompatibleTranslation.getFormattedText();
                packDescription = IncompatibleNewTranslation.getFormattedText();
            }

            // bind the new texture and reset the color
            mc.getTextureManager().bindTexture(ResourcePackUtils.ResourcePackTextures);
            GuiUtils.resetColor();

            int entryX = mouseX - x;

            // if on selected pack side
            if (isSelected()) {
                if (canMovePackUp()) {
                    // move pack up button hover
                    // check if mouse is on the left side of the icon
                    if (entryX <= ResourcePackUtils.ArrowSize) {
                        Gui.drawModalRectWithCustomSizedTexture(
                                x, y + (ResourcePackUtils.ArrowSize / 2),
                                ResourcePackUtils.ArrowSize * 2, 0,
                                ResourcePackUtils.ArrowSize, ResourcePackUtils.ArrowSize,
                                ResourcePackUtils.DefaultTextureSize, ResourcePackUtils.DefaultTextureSize
                        );
                    }
                    // move pack up button
                    // if hovering over any part of the entry, show the non-hovering version of the icon
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(
                                x, y + (ResourcePackUtils.ArrowSize / 2),
                                ResourcePackUtils.ArrowSize * 3, 0,
                                ResourcePackUtils.ArrowSize, ResourcePackUtils.ArrowSize,
                                ResourcePackUtils.DefaultTextureSize, ResourcePackUtils.DefaultTextureSize
                        );
                    }
                }
                if (canMovePackDown()) {
                    // move pack down button hover
                    // check if mouse is hovering over the icon, then check if mouse is on the right side of the icon
                    if (entryX < slotHeight && entryX > ResourcePackUtils.ArrowSize) {
                        Gui.drawModalRectWithCustomSizedTexture(
                                x + ResourcePackUtils.ArrowSize, y + (ResourcePackUtils.ArrowSize / 2),
                                ResourcePackUtils.ArrowSize * 2, ResourcePackUtils.ArrowSize,
                                ResourcePackUtils.ArrowSize, ResourcePackUtils.ArrowSize,
                                ResourcePackUtils.DefaultTextureSize, ResourcePackUtils.DefaultTextureSize
                        );
                    }
                    // move pack down button
                    // if hovering over any part of the entry, show the non-hovering version of the icon
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(
                                x + ResourcePackUtils.ArrowSize, y + (ResourcePackUtils.ArrowSize / 2),
                                ResourcePackUtils.ArrowSize * 3, ResourcePackUtils.ArrowSize,
                                ResourcePackUtils.ArrowSize, ResourcePackUtils.ArrowSize,
                                ResourcePackUtils.DefaultTextureSize, ResourcePackUtils.DefaultTextureSize
                        );
                    }
                }
            }
        }

        int incompatibleTextWidth = mc.fontRendererObj.getStringWidth(packTitle);

        if (incompatibleTextWidth > ResourcePackUtils.EntryMaxTextWidth)
        {
            packTitle = mc.fontRendererObj.trimStringToWidth(packTitle,
                    ResourcePackUtils.EntryMaxTextWidth - mc.fontRendererObj.getStringWidth(ResourcePackUtils.TooLongDisplay)
            ) + ResourcePackUtils.TooLongDisplay;
        }

        mc.fontRendererObj.drawStringWithShadow(
                packTitle,
                (float)(x + ResourcePackUtils.PackIconSize + (ResourcePackUtils.ResourcePackEntryPadding / 2)),
                (float)(y + ResourcePackUtils.PackTitleTopPadding),
                ColorUtils.White
        );

        List<String> packDescriptionLines = mc.fontRendererObj.listFormattedStringToWidth(packDescription, ResourcePackUtils.EntryMaxTextWidth);

        for (int line = 0; line < ResourcePackUtils.PackDescriptionLineCount && line < packDescriptionLines.size(); ++line)
        {
            mc.fontRendererObj.drawStringWithShadow(
                    packDescriptionLines.get(line),
                    (float)(x + ResourcePackUtils.PackIconSize + (ResourcePackUtils.ResourcePackEntryPadding / 2)),
                    (float)(y + (ResourcePackUtils.PackTitleTopPadding * 3) + (GuiUtils.TextHeight * (line + 1))),
                    ColorUtils.colorToInt(128, 128, 128, 255)
            );
        }
    }

    protected abstract PackCompatibility getCompatibility();

    protected abstract String getPackDescription();

    protected abstract String getPackTitle();

    protected abstract void bindPackIcon();

    protected boolean isPackMovable()
    {
        return true;
    }

    protected boolean isAvailable()
    {
        return !resourcePackScreen.hasResourcePackEntry(this);
    }

    protected boolean isSelected()
    {
        return resourcePackScreen.hasResourcePackEntry(this);
    }

    protected boolean canMovePackUp()
    {
        List<ResourcePackEntry> currentList = resourcePackScreen.getListContaining(this);
        int slotIndex = currentList.indexOf(this);
        return slotIndex > 0 && (currentList.get(slotIndex - 1)).isPackMovable();
    }

    protected boolean canMovePackDown()
    {
        List<ResourcePackEntry> currentList = resourcePackScreen.getListContaining(this);
        int slotIndex = currentList.indexOf(this);
        return slotIndex >= 0 && slotIndex < currentList.size() - 1 && (currentList.get(slotIndex + 1)).isPackMovable();
    }

    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int x, int y) {
        if (isPackMovable() && x <= ResourcePackUtils.ResourcePackEntryWidth + ResourcePackUtils.ResourcePackEntryPadding) {
            if (isAvailable()) {
                resourcePackScreen.markChanged();
                PackCompatibility compatibility = getCompatibility();

                if (compatibility != PackCompatibility.Compatible) {
                    String incompatibleTitle = I18n.format("resourcePack.incompatible.confirm.title");
                    String incompatibleConfirm = I18n.format("resourcePack.incompatible.confirm." +
                            (compatibility == PackCompatibility.IncompatibleNew ? "new" : "old"));

                    mc.displayGuiScreen(new GuiYesNo((result, id) -> {
                        List<ResourcePackEntry> currentList = resourcePackScreen.getListContaining(this);
                        mc.displayGuiScreen(resourcePackScreen);

                        if (result) {
                            // remove from current list
                            currentList.remove(this);
                            // add to the top of the list
                            resourcePackScreen.getSelectedResourcePackPanel().add(0, this);
                        }
                    }, incompatibleTitle, incompatibleConfirm, 0));
                }
                else {
                    // remove the pack from available packs
                    resourcePackScreen.getListContaining(this).remove(this);
                    // add the pack to the top
                    resourcePackScreen.getSelectedResourcePackPanel().add(0, this);
                }

                return true;
            }

            if (x > ResourcePackUtils.PackIconSize && isSelected()) {
                // unselect the pack
                resourcePackScreen.getListContaining(this).remove(this);
                // add the pack to the available packs list
                resourcePackScreen.getAvailableResourcePackPanel().add(0, this);
                // reload
                resourcePackScreen.markChanged();
                return true;
            }

            if (x <= ResourcePackUtils.PackIconSize && y <= ResourcePackUtils.PackIconSize) {
                List<ResourcePackEntry> selectedPacksList = resourcePackScreen.getListContaining(this);

                // move pack up
                if (x <= ResourcePackUtils.ArrowSize && canMovePackUp()) {
                    // get the current index of the pack that wants to move up
                    int currentPackIndex = selectedPacksList.indexOf(this);
                    // remove it from the list
                    selectedPacksList.remove(this);
                    // add it back, at the index it was before, minus one to move upwards
                    selectedPacksList.add(currentPackIndex - 1, this);
                    // reload
                    resourcePackScreen.markChanged();
                    return true;
                }

                // move pack down
                if (x > ResourcePackUtils.ArrowSize && canMovePackDown()) {
                    // get the current index of the pack that wants to move down
                    int currentPackIndex = selectedPacksList.indexOf(this);
                    // remove it from the list
                    selectedPacksList.remove(this);
                    // add it back, at the index it was before, plus one to move downwards
                    selectedPacksList.add(currentPackIndex + 1, this);
                    // reload
                    resourcePackScreen.markChanged();
                    return true;
                }
            }
        }

        return false;
    }

    // todo: deobf
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
    }

    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
    }
}
