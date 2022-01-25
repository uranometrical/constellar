package dev.tomat.constellar.content.gui.resourcepack.entries;

import dev.tomat.constellar.content.gui.resourcepack.PackCompatibility;
import dev.tomat.constellar.content.gui.resourcepack.ResourcePackEntry;
import dev.tomat.constellar.content.gui.resourcepack.ResourcePackScreen;
import dev.tomat.constellar.content.gui.resourcepack.ResourcePackUtils;
import net.minecraft.client.resources.ResourcePackRepository;

public class ResourcePackEntryFound extends ResourcePackEntry {

    private final ResourcePackRepository.Entry resourcePack;

    public ResourcePackEntryFound(ResourcePackScreen resourcePackScreenIn, ResourcePackRepository.Entry resourcePackIn)
    {
        super(resourcePackScreenIn);
        resourcePack = resourcePackIn;
    }

    protected void bindPackIcon()
    {
        resourcePack.bindTexturePackIcon(mc.getTextureManager());
    }

    protected PackCompatibility getCompatibility()
    {
        // not going to bother to "deobf"
        return ResourcePackUtils.intToCompatibility(resourcePack.func_183027_f());
    }

    protected String getPackDescription()
    {
        return resourcePack.getTexturePackDescription();
    }

    protected String getPackTitle()
    {
        return resourcePack.getResourcePackName();
    }

    public ResourcePackRepository.Entry getResourcePack()
    {
        return resourcePack;
    }
}
