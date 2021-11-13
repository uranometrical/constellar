package dev.tomat.constellar.gui.modules;

import dev.tomat.constellar.modules.IModule;
import dev.tomat.constellar.modules.impl.KeystrokesModule;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiModulesScreen extends GuiScreen {
    public GuiModuleContainer ModulesList = new GuiModuleContainer();

    @Override
    public void initGui() {
        super.initGui();

        ModulesList.init();

        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());
        addModule(new KeystrokesModule());

        ModulesList.updatePage(this, 0);

        buttonList.add(new GuiButton(200, width / 2 - 100, height - 40, I18n.format("gui.done")));
    }

    public void addModule(IModule module) {
        ModulesList.add(module, width, height);
    }

    public void addButton(GuiButton button) {
        buttonList.add(button);
    }

    public void removeButton(GuiButton button) {
        buttonList.remove(button);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (!button.enabled)
            return;

        if (button.id == 200){
            mc.displayGuiScreen(new GuiOptions(null, mc.gameSettings));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawCenteredString(fontRendererObj, I18n.format("modules.title") + " " + ModulesList.getPageDisplay(), width / 2, 16, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
