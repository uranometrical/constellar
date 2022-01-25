package dev.tomat.constellar.content.gui.resourcepack.entries;

import dev.tomat.constellar.content.gui.resourcepack.PackCompatibility;
import dev.tomat.constellar.content.gui.resourcepack.ResourcePackEntry;
import dev.tomat.constellar.content.gui.resourcepack.ResourcePackScreen;
import dev.tomat.constellar.content.gui.resourcepack.ResourcePackUtils;
import dev.tomat.constellar.content.resources.ConstellarPack;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ResourcePackEntryConstellar extends ResourcePackEntry {
    private static final Logger logger = LogManager.getLogger();

    private final IResourcePack packInstance;
    private final ResourceLocation resourcePackIcon;

    public ResourcePackEntryConstellar(ResourcePackScreen resourcePackScreenIn) {
        super(resourcePackScreenIn);
        packInstance = new ConstellarPack();
        DynamicTexture packImage = TextureUtil.missingTexture;

        try
        {
            packImage = new DynamicTexture(packInstance.getPackImage());
        }
        catch (IOException ioException)
        {
            logger.error("Couldn't load default pack image", ioException);
        }
        resourcePackIcon = mc.getTextureManager().getDynamicTextureLocation("texturepackicon", packImage);
    }

    protected String getPackTitle() {
        return packInstance.getPackName();
    }

    protected String getPackDescription() {
        return ResourcePackUtils.getMetadataDescription(packInstance, mc.getResourcePackRepository(), logger);
    }

    protected void bindPackIcon() {
        mc.getTextureManager().bindTexture(resourcePackIcon);
    }

    protected PackCompatibility getCompatibility() {
        return PackCompatibility.Compatible;
    }

    protected boolean isAvailable() {
        return false;
    }

    protected boolean isSelected() {
        return false;
    }

    protected boolean canMovePackUp() {
        return false;
    }

    protected boolean canMovePackDown() {
        return false;
    }

    protected boolean isPackMovable() {
        return false;
    }
}
