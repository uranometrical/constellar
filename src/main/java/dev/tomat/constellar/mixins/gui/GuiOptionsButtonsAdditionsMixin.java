package dev.tomat.constellar.mixins.gui;

import dev.tomat.constellar.gui.modules.GuiModulesScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(GuiOptions.class)
public abstract class GuiOptionsButtonsAdditionsMixin extends GuiScreen implements GuiYesNoCallback {
    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo ci) {
        Optional<GuiButton> doneButton = buttonList.stream().filter(x -> x.id == 200).findFirst();

        if (doneButton.isPresent()) {
            // buttonList.remove(doneButton.get());

            GuiButton doneButtonObj = doneButton.get();

            doneButtonObj.yPosition += 24;
            buttonList.add(new GuiButton(1000, width / 2 - 100, height / 6 + 168, I18n.format("modules.open")));
        }
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    protected void actionPerformed(GuiButton p_actionPerformed_1_, CallbackInfo ci) {
        if (p_actionPerformed_1_.enabled && p_actionPerformed_1_.id == 1000) {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(new GuiModulesScreen());
        }
    }
}
