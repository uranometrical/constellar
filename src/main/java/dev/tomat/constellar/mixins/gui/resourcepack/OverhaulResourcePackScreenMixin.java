package dev.tomat.constellar.mixins.gui.resourcepack;

import dev.tomat.common.utils.ColorUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        // draw pano in main menu and normal bg in-game
        drawDefaultBackground();

        // draw the resource pack lists
        availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);

        // draw the title
        drawCenteredString(
                fontRendererObj,
                I18n.format("resourcePack.title"),
                width / 2,
                GuiUtils.DefaultTitleTopPadding,
                ColorUtils.White
        );

        int headerPadding = 104;
        drawListHeader(I18n.format("resourcePack.available.title"), this.width / 2 - headerPadding);
        drawListHeader(I18n.format("resourcePack.selected.title"), this.width / 2 + headerPadding);
        // draw GuiButtons
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void drawListHeader(String header, int xPosition) {
        int padding = 18;
        mc.fontRendererObj.drawString(
                header,
                xPosition - mc.fontRendererObj.getStringWidth(header) / 2,
                GuiUtils.DefaultTitleTopPadding + padding,
                ColorUtils.White
        );
    }
}
