package dev.tomat.constellar.content.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public abstract class UiModule extends StandardModule {
    protected Minecraft mc;
    protected int xPosition;
    protected int yPosition;

    public UiModule(String key, String description, ResourceLocation identifier, int defaultX, int defaultY, int width, int height) {
        super(key, description, identifier);
        mc = Minecraft.getMinecraft();
        // todo: serilization or somethijng
        xPosition = defaultX;
        yPosition = defaultY;
    }

    // todo: completely configurable
    public void draw(int xPos, int yPos, int width, int height) {
    }
}
