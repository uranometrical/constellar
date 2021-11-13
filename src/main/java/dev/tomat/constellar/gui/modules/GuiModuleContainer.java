package dev.tomat.constellar.gui.modules;

import dev.tomat.constellar.modules.IModule;

import java.util.ArrayList;
import java.util.List;

public class GuiModuleContainer {
    public List<List<GuiModule>> Pages = new ArrayList<>();
    private int xModules;
    private int yModules;
    private int idModules;
    public int Page;
    public int PageToAddTo;

    public void init() {
        xModules = 0;
        yModules = 0;
        idModules = 0;
        Pages = new ArrayList<>();
        Pages.add(new ArrayList<>());
        Page = 0;
        PageToAddTo = 0;
    }

    public void add(IModule module, int width, int height) {
        int maxX = getMaxX(width);
        int maxY = getMaxY(height);

        Pages.get(PageToAddTo).add(new GuiModule(idModules++, width, xModules, yModules, module));

        xModules++;

        if (xModules >= maxX)
        {
            xModules = 0;
            yModules++;

            if (yModules >= maxY) {
                yModules = 0;
                PageToAddTo++;
                Pages.add(new ArrayList<>());
            }
        }
    }

    public void updatePage(GuiModulesScreen screen, int pageToGoTo) {
        for (GuiModule module : Pages.get(Page))
            screen.removeButton(module);

        Page = pageToGoTo;

        for (GuiModule module : Pages.get(Page))
            screen.addButton(module);
    }

    public String getPageDisplay() {
        return "(" + (Page + 1) + "/" + (Pages.size()) + ")";
    }

    public static int getMaxX(int width) {
        int max = (width - GuiModule.PADDING_X) / (GuiModule.WIDTH + GuiModule.PADDING);

        // TODO: See: GuiModule.getX
        if (max % 2 == 0)
            max--;

        return Math.min(max, 5);
    }

    public static int getMaxY(int height) {
        int max = (height - GuiModule.PADDING_Y) / (GuiModule.HEIGHT + GuiModule.PADDING);

        return Math.min(max, 5);
    }
}
