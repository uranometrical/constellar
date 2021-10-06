package io.github.steviegt6.constellar.gui.modules;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;

public class GuiModuleList extends GuiScreen implements GuiYesNoCallback {
    protected String GuiTitle;

    @Override
    public void initGui() {
        GuiTitle = I18n.format("modules.title");

        buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done")));
    }

    @Override
    public void confirmClicked(boolean p_confirmClicked_1_, int p_confirmClicked_2_) {
        mc.displayGuiScreen(this);
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        drawDefaultBackground();

        // I don't know what 16777215 is but GuiOptions uses it, so I mean
        drawCenteredString(fontRendererObj, GuiTitle, width / 2, 15, 16777215);

        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) {
        if (p_actionPerformed_1_.enabled && p_actionPerformed_1_.id == 200) {
            mc.displayGuiScreen(new GuiOptions(null, mc.gameSettings));
        }
    }
}
