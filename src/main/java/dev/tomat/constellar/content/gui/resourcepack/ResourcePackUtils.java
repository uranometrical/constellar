package dev.tomat.constellar.content.gui.resourcepack;

import com.google.gson.JsonParseException;
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
    public static final int ResourcePackEntryHeight = PackIconSize + ResourcePackEntryPadding;
    public static final int ResourcePackPanelHeaderPadding = 18;
    public static final int DefaultTextureSize = 256;
    public static final int EntryMaxTextWidth = 157;
    public static final String TooLongDisplay = "...";
    public static final int PackTitleTopPadding = 1;
    public static final int PackDescriptionLineCount = 2;
    public static final int ResourcePackEntryWidth = 210;
    public static final int ResourcePackPanelBottomPadding = 54;
    public static final int ResourcePackPanelHeaderTopPadding = 26;

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
}
