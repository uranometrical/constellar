package dev.tomat.constellar.content.gui.resourcepack;

import com.google.gson.JsonParseException;
import dev.tomat.constellar.content.gui.GuiUtils;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ResourcePackUtils {
    public static final ResourceLocation ResourcePackTextures = new ResourceLocation("constellar", "textures/gui/resource_packs.png");
    public static final int PackIconSize = 32;
    public static final int ArrowSize = 16;
    public static final int ResourcePackEntryPadding = 4;
    public static final int ResourcePackPanelPadding = 8;

    /**
     * Full height of a pack entry, including the padding. Usually 36.
     */
    public static final int ResourcePackEntryHeight = PackIconSize + ResourcePackEntryPadding;

    public static final int ResourcePackPanelHeaderPadding = 16;
    public static final int DefaultTextureSize = 256;
    public static final int EntryMaxTextWidth = 157;
    public static final String TooLongDisplay = "...";
    public static final int PackTitleTopPadding = 1;
    public static final int PackDescriptionLineCount = 2;
    public static final int ResourcePackEntryWidth = 190;

    /**
     * Padding between the bottom of the panel and the bottom of the screen. Usually 44.
     * Space for the button at its default height (20), with 12 pixel padding on the top and bottom of it.
     */
    public static final int ResourcePackPanelBottomPadding = (12 * 2) + GuiUtils.DefaultButtonHeight;

    /**
     * Padding between the top of the screen and top of the panel. Usually 26.
     * 8 pixels of padding on the top and bottom of the 10 pixel title text.
     */
    public static final int ResourcePackPanelTopPadding = (8 * 2) + GuiUtils.TextHeight;

    public static final int PaddingBetweenResourcePackPanels = 16;
    public static final int ResourcePacksScreenTitleTopPadding = 8;

    public static PackCompatibility intToCompatibility(int i) {
        if (i < 1) {
            return PackCompatibility.IncompatibleOld;
        }
        else if (i > 1) {
            return PackCompatibility.IncompatibleNew;
        }

        return PackCompatibility.Compatible;
    }

    public static String getMetadataDescription(IResourcePack packInstance, ResourcePackRepository resourcePackRepository, Logger logger) {
        try
        {
            PackMetadataSection metadata = packInstance.getPackMetadata(resourcePackRepository.rprMetadataSerializer, "pack");

            if (metadata != null)
                return metadata.getPackDescription().getFormattedText();
        }
        catch (JsonParseException | IOException jsonParseException)
        {
            logger.error("Couldn't load metadata info", jsonParseException);
        }

        return EnumChatFormatting.RED + "Missing " + "pack.mcmeta" + " :(";
    }

    public static int getResourcePackEntryCount(int screenHeight) {
        int height = screenHeight - ResourcePackPanelTopPadding - ResourcePackPanelHeaderPadding - ResourcePackPanelBottomPadding;
        return Math.floorDiv(height, ResourcePackEntryHeight);
    }

    public static int getResourcePackPanelHeight(int screenHeight) {
        return ResourcePackPanelHeaderPadding + (getResourcePackEntryCount(screenHeight) * ResourcePackEntryHeight) + (ResourcePackPanelPadding / 2);
    }

    public static int getResourcePackEntryCountFromPanelHeight(int panelHeight) {
        return ((panelHeight - ResourcePackPanelHeaderPadding) / ResourcePackEntryHeight);
    }
}
