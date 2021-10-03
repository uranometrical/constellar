package io.github.steviegt6.constellar;

import net.minecraft.util.ResourceLocation;

public class ConstellarMain {
    public static final String ClientNameReadable = "Constellar";
    public static final String ClientNameTechnical = "constellar";
    public static final String ClientVersion = "0.1.0-alpha";
    public static boolean MainMenuLoaded = false;
    public static ResourceLocation backgroundTexture;
    public static int panoramaTimer;
    public static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]
            {
                    new ResourceLocation("textures/gui/title/background/panorama_0.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_1.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_2.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_3.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_4.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_5.png")
            };
}
