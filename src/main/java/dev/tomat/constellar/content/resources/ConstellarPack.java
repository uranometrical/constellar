package dev.tomat.constellar.content.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

public class ConstellarPack implements IResourcePack {
    @Override
    public InputStream getInputStream(ResourceLocation resourceLocation) {
        return getResourceStream(resourceLocation);
    }

    @Override
    public boolean resourceExists(ResourceLocation resourceLocation) {
        return getResourceStream(resourceLocation) != null;
    }

    private InputStream getResourceStream(ResourceLocation p_getResourceStream_1_) {
        return ConstellarPack.class.getResourceAsStream("/assets/" + p_getResourceStream_1_.getResourceDomain() + "/" + p_getResourceStream_1_.getResourcePath());
    }

    @Override
    public Set<String> getResourceDomains() {
        return Collections.singleton("constellar");
    }

    @Override
    public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer iMetadataSerializer, String s) {
        return AbstractResourcePack.readMetadata(iMetadataSerializer, getResourceStream(new ResourceLocation("constellar", "pack.mcmeta")), s);
    }

    @Override
    public BufferedImage getPackImage() {
        try {
            return TextureUtil.readBufferedImage(getResourceStream(new ResourceLocation("constellar", "pack.png")));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getPackName() {
        return EnumChatFormatting.GRAY + "Default " + EnumChatFormatting.LIGHT_PURPLE + "Constellar " + EnumChatFormatting.GRAY + "Resources";
    }
}