package dev.tomat.constellar.gui.modules;

import com.google.common.collect.Lists;
import dev.tomat.constellar.modules.impl.KeystrokesModule;
import dev.tomat.constellar.modules.IModule;
import dev.tomat.constellar.modules.ModuleStatus;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.List;

// todo: module saving
// todo: module profiles
// todo: module settings
// bug: modules double when screen resized
// todo: description on hover
// bug: single clicking doesnt enable a module and i have to drag or double click to enable it
// bug: playing sound on module enable doesnt work
// todo: remove enabled/disabled from module name
// todo: info text down bottom explaining hover for desc click to enable/disable
public class GuiModulesScreen extends GuiScreen {
    private GuiModulesList list;
    private final List<IModule> modules = Lists.newArrayList();

    public void initGui() {
        this.list = new GuiModulesList();
        modules.add(new KeystrokesModule("keystrokes1", "displays button presses"));
        modules.add(new KeystrokesModule("keystrokes2", "displays button presses"));


        // todo: remove magic numbers
        buttonList.add(new GuiButton(200, width / 2 - 100, this.height - 38, I18n.format("gui.done")));
        this.list.registerScrollButtons(7, 8);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.enabled) {
            if (button instanceof IModule) {
                this.list.actionPerformed(button);
            }

            if (button.id == 200) {
                mc.displayGuiScreen(new GuiOptions(null, mc.gameSettings));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("modules.title"), this.width / 2, 16, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public class GuiModulesList extends GuiSlot {
        public GuiModulesList() {
            // todo: remove magic numbers
            super(
                    GuiModulesScreen.this.mc,
                    GuiModulesScreen.this.width,
                    GuiModulesScreen.this.height,
                    32, GuiModulesScreen.this.height - 65 + 4, 18
            );
        }

        protected int getSize() {
            return GuiModulesScreen.this.modules.size();
        }

        @Override
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            GuiModulesScreen.this.modules.get(slotIndex).setModuleStatus(
                    GuiModulesScreen.this.modules.get(slotIndex).isDisabled() ?
                            ModuleStatus.ENABLED :
                            ModuleStatus.DISABLED);

            //if (isDoubleClick) {
                // open module settings page
            //}
        }

        @Override
        public void actionPerformed(GuiButton button) {
            super.actionPerformed(button);
            if (button.enabled) {
                button.playPressSound(this.mc.getSoundHandler());
            }
        }

        @Override
        protected boolean isSelected(int slotIndex) {
            return !GuiModulesScreen.this.modules.get(slotIndex).isDisabled();
        }

        protected int getContentHeight() {
            // todo: remove magic number
            return this.getSize() * 18;
        }

        protected void drawBackground() {
            GuiModulesScreen.this.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
            // todo: remove magic numbers + localization
            GuiModulesScreen.this.drawCenteredString(GuiModulesScreen.this.fontRendererObj,
                    GuiModulesScreen.this.modules.get(entryID).getKey() + " (" + GuiModulesScreen.this.modules.get(entryID).getModuleStatus() + ")",
                    this.width / 2, p_180791_3_ + 1, 16777215
            );
        }
    }
}
