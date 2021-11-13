package dev.tomat.constellar.gui.modules;

import dev.tomat.constellar.modules.IModule;

import java.util.ArrayList;
import java.util.List;

public class GuiModuleContainer {
    public List<GuiModule> Modules = new ArrayList<>();
    private int xModules;
    private int yModules;
    private int idModules;
    public int Page;

    public void init() {
        xModules = 0;
        yModules = 0;
        idModules = 0;
        Modules = new ArrayList<>();
    }

    public void add(GuiModulesScreen screen, IModule module, int width) {
        // TODO: Paged button lists.
        GuiModule button = new GuiModule(idModules++, width, xModules, yModules, module);

        Modules.add(new GuiModule(idModules++, width, xModules, yModules, module));

        xModules++;

        if (xModules > 4)
        {
            xModules = 0;
            yModules++;
        }

        screen.addButton(button);
    }
}
