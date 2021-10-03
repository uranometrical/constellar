package io.github.steviegt6.constellar.mixins.gui.panorama;

import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

// This works in tandem with PanoramaGuiScreenMixin to inject and utilize newly-created methods.
@Mixin(GuiScreen.class)
public interface PanoramaInvoker {
    void renderSkybox(float p_renderSkybox_3_);

    boolean canShowPanorama();
}
