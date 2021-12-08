package dev.tomat.constellar.mixins.resources;

import dev.tomat.constellar.content.resources.ConstellarPackListEntry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.ResourcePackListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiScreenResourcePacks.class)
public class AddConstellarResourcePackMixin extends GuiScreen {
    @Shadow
    private List<ResourcePackListEntry> selectedResourcePacks;

    @Shadow
    private boolean changed;

    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo ci) {
        if (!changed)
            selectedResourcePacks.add(new ConstellarPackListEntry((GuiScreenResourcePacks) (Object) this));
    }
}
