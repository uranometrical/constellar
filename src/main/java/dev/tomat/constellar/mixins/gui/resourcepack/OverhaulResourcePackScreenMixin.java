/*package dev.tomat.constellar.mixins.gui.resourcepack;

import com.google.common.collect.Lists;
import dev.tomat.common.utils.ColorUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
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

    private GuiResourcePackAvailable visibleAvailableResourcePacks;
    private GuiResourcePackSelected visibleSelectedResourcePacks;

    @Inject(method = "initGui", at = @At("TAIL"))
    public void postInitGui(CallbackInfo ci) {
        // remove open folder button and done button
        this.buttonList.removeIf(button -> button.id == DoneButtonId || button.id == OpenFolderButtonId);

        visibleAvailableResourcePacksList = Lists.newArrayList();
        visibleSelectedResourcePacksList = Lists.newArrayList();


        // todo: add image button like language button in main menu
        // todo: with the small folder icon that runs as button 2
    }

    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // draw pano in main menu and normal bg in-game
        drawDefaultBackground();

        // draw the resource pack lists
        availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);

        int titleTopPadding = 8;
        // draw the title
        drawCenteredString(
                fontRendererObj,
                I18n.format("resourcePack.title"),
                width / 2,
                titleTopPadding,
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
        drawCenteredString(
                fontRendererObj,
                header,
                xPosition,
                GuiUtils.DefaultTitleTopPadding + padding,
                ColorUtils.White
        );
    }
}
*/