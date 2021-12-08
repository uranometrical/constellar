package dev.tomat.constellar.mixins.gui.resourcepack;

import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiScreenResourcePacks.class)
public class OverhaulResourcePackScreenMixin extends GuiScreen {
    private static final int DoneButtonId = 1;
    private static final int OpenFolderButtonId = 2;

    @Shadow private GuiResourcePackAvailable availableResourcePacksList;
    @Shadow private GuiResourcePackSelected selectedResourcePacksList;

    @Inject(method = "initGui", at = @At("TAIL"))
    public void postInitGui(CallbackInfo ci) {
        // remove open folder button and done button
        this.buttonList.removeIf(button -> button.id == DoneButtonId || button.id == OpenFolderButtonId);

        // todo: add image button like language button in main menu
        // todo: with the small folder icon that runs as button 2
    }

    /**
     * @author Metacinnabar
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawBackground(0);
        availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        //this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
        //this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), this.width / 2 - 77, this.height - 26, 8421504);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
