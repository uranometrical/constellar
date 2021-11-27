package dev.tomat.constellar.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Objects;
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
            return TextureUtil.readBufferedImage(Objects.requireNonNull(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath())));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getPackName() {
        return "Constellar Default Resources";
    }
}
