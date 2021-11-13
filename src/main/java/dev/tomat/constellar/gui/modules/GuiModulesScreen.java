package dev.tomat.constellar.gui.modules;

import dev.tomat.constellar.ConstellarMain;
import dev.tomat.constellar.modules.IModule;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiModulesScreen extends GuiScreen {
    public GuiModuleContainer ModulesList = new GuiModuleContainer();
    public GuiButton Left;
    public GuiButton Right;

    @Override
    public void initGui() {
        super.initGui();

        ModulesList.init();

        for (IModule module : ConstellarMain.Modules.getModules())
            addModule(module);

        ModulesList.updatePage(this, 0);

        buttonList.add(new GuiButton(200, width / 2 - 100, height - 40, I18n.format("gui.done")));
        buttonList.add(Left = new GuiButton(201, 30, height / 2, 20, 20, "<"));
        buttonList.add(Right = new GuiButton(202, width - 30, height / 2, 20, 20, ">"));

        Left.enabled = false;
        Right.enabled = false;
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

        switch (button.id) {
            case 200:
                mc.displayGuiScreen(new GuiOptions(null, mc.gameSettings));
                break;

            case 201:
                if (ModulesList.Page > 0)
                    ModulesList.updatePage(this, ModulesList.Page - 1);
                break;

            case 202:
                if (ModulesList.Page < ModulesList.Pages.size() - 1)
                    ModulesList.updatePage(this, ModulesList.Page + 1);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        Left.enabled = ModulesList.Page > 0;
        Right.enabled = ModulesList.Page < ModulesList.Pages.size() - 1;

        drawCenteredString(fontRendererObj, I18n.format("modules.title") + " " + ModulesList.getPageDisplay(), width / 2, 16, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
