package dev.tomat.constellar.mixins.gui.resourcepack;

import dev.tomat.common.utils.ColorUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.resources.ResourcePackListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResourcePackListEntry.class)
public abstract class ResourcePackEntryMixin implements GuiListExtended.IGuiListEntry {
    @Inject(method = "drawEntry", at = @At("HEAD"))
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, CallbackInfo ci) {
        if (isSelected) {
            GuiUtils.drawRectNormal(x + (listWidth / 2), y, listWidth, 32, ColorUtils.colorToInt(80, 80, 80, 100), GuiUtils.PosMode.CENTER);
        }
    }
}
