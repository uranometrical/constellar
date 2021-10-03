package io.github.steviegt6.constellar.mixins.gui.panorama;

import net.minecraft.util.ResourceLocation;

public class PanoramaContainer {
    public static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]
            {
                    new ResourceLocation("textures/gui/title/background/panorama_0.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_1.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_2.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_3.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_4.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_5.png")
            };

    // Keep these fields static for consistency's sake.
    public static int ourPanoramaTimer;

    public static ResourceLocation ourBackgroundTexture;
}
