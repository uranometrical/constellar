package dev.tomat.constellar.gui.modules;

import dev.tomat.constellar.modules.IModule;
import dev.tomat.constellar.modules.impl.KeystrokesModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiModulesScreen extends GuiScreen {
    public List<GuiModule> ModulesList = new ArrayList<>();

    private int xModules;
    private int yModules;
    private int idModules;

    @Override
    public void initGui() {
        super.initGui();

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

        buttonList.add(new GuiButton(200, width / 2 - 100, height - 40, I18n.format("gui.done")));
    }

    public void addModule(IModule module) {
        GuiButton button = new GuiModule(idModules++, width, xModules, yModules, module).Button;

        ModulesList.add(new GuiModule(idModules++, width, xModules, yModules, module));

        xModules++;

        if (xModules > 4)
        {
            xModules = 0;
            yModules++;
        }

        buttonList.add(button);
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

        for (GuiModule module : ModulesList)
            module.draw();

        drawCenteredString(fontRendererObj, I18n.format("modules.title"), width / 2, 16, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.setWorldAndResolution(mc, width, height);

        for (GuiModule module : ModulesList)
            module.updatePositions(width);
    }
}
