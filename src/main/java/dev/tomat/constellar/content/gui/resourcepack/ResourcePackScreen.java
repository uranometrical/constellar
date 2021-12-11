package dev.tomat.constellar.content.gui.resourcepack;

import com.google.common.collect.Lists;
import dev.tomat.common.utils.ColorUtils;
import dev.tomat.common.utils.FileUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import dev.tomat.constellar.content.gui.resourcepack.buttons.OpenFolderButton;
import dev.tomat.constellar.content.gui.resourcepack.buttons.RefreshButton;
import dev.tomat.constellar.content.gui.resourcepack.buttons.ResourcePackButtonId;
import dev.tomat.constellar.content.gui.resourcepack.entries.ResourcePackEntryConstellar;
import dev.tomat.constellar.content.gui.resourcepack.entries.ResourcePackEntryDefault;
import dev.tomat.constellar.content.gui.resourcepack.entries.ResourcePackEntryFound;
import dev.tomat.constellar.content.gui.resourcepack.panels.AvailableResourcePackPanel;
import dev.tomat.constellar.content.gui.resourcepack.panels.SelectedResourcePackPanel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ResourcePackScreen extends GuiScreen {

    private static final Logger logger = LogManager.getLogger();

    protected final GuiScreen parentScreen;

    protected List<ResourcePackEntry> availableResourcePacks;
    protected List<ResourcePackEntry> selectedResourcePacks;

    protected AvailableResourcePackPanel availableResourcePackPanel;
    protected SelectedResourcePackPanel selectedResourcePackPanel;

    protected boolean changed = false;

    public ResourcePackScreen(GuiScreen parentScreenIn)
    {
        parentScreen = parentScreenIn;
    }

    public void initGui()
    {
        int buttonYPos = height - ResourcePackUtils.ResourcePackButtonsBottomPadding - GuiUtils.DefaultButtonHeight;

        // button id, x, y, text
        buttonList.add(new GuiOptionButton(ResourcePackButtonId.Done,
                (width / 2) - (GuiUtils.DefaultGuiOptionsButtonWidth / 2),
                buttonYPos,
                I18n.format("gui.done")
        ));

        buttonList.add(new OpenFolderButton(ResourcePackButtonId.OpenFolder,
                (width / 2) - (GuiUtils.DefaultGuiOptionsButtonWidth / 2) - ResourcePackUtils.ResourcePackDoneButtonSidePadding - GuiUtils.DefaultButtonHeight,
                buttonYPos
        ));

        buttonList.add(new RefreshButton(ResourcePackButtonId.Refresh,
                (width / 2) + (GuiUtils.DefaultGuiOptionsButtonWidth / 2) + ResourcePackUtils.ResourcePackDoneButtonSidePadding,
                buttonYPos
        ));

        if (!changed)
        {
            initPacks();
        }

        initPanels();
    }

    private void initPacks() {
        availableResourcePacks = Lists.newArrayList();
        selectedResourcePacks = Lists.newArrayList();

        ResourcePackRepository resourcePackRepository = mc.getResourcePackRepository();
        resourcePackRepository.updateRepositoryEntriesAll();

        List<ResourcePackRepository.Entry> resourcePacks = Lists.newArrayList(resourcePackRepository.getRepositoryEntriesAll());
        resourcePacks.removeAll(resourcePackRepository.getRepositoryEntries());

        for (ResourcePackRepository.Entry resourcePack : resourcePacks)
        {
            availableResourcePacks.add(new ResourcePackEntryFound(this, resourcePack));
        }

        for (ResourcePackRepository.Entry resourcePack : Lists.reverse(resourcePackRepository.getRepositoryEntries()))
        {
            selectedResourcePacks.add(new ResourcePackEntryFound(this, resourcePack));
        }

        selectedResourcePacks.add(new ResourcePackEntryConstellar(this));
        selectedResourcePacks.add(new ResourcePackEntryDefault(this));
    }

    private void initPanels() {
        // pack coords based on top left of panel, without width padding
        availableResourcePackPanel = new AvailableResourcePackPanel(mc,
                ResourcePackUtils.ResourcePackEntryWidth,
                // use epic helper method
                ResourcePackUtils.getResourcePackPanelHeight(height),
                // same xPos as the xPos of the icon. left.
                (width / 2) - (ResourcePackUtils.PaddingBetweenResourcePackPanels / 2) - (ResourcePackUtils.ResourcePackPanelPadding / 2) - ResourcePackUtils.ResourcePackEntryWidth,
                availableResourcePacks
        );
        // basically x with padding
        availableResourcePackPanel.setSlotXBoundsFromLeft((width / 2) - (ResourcePackUtils.PaddingBetweenResourcePackPanels / 2) - (ResourcePackUtils.ResourcePackPanelPadding) - ResourcePackUtils.ResourcePackEntryWidth);

        selectedResourcePackPanel = new SelectedResourcePackPanel(mc,
                ResourcePackUtils.ResourcePackEntryWidth,
                // use epic helper method
                ResourcePackUtils.getResourcePackPanelHeight(height),
                // same xPos as the xPos of the icon. left.
                // whole screen, half screen, then add half the padding between the sides, then add the panel padding (4) to get the icon's X
                (width / 2) + (ResourcePackUtils.PaddingBetweenResourcePackPanels / 2) + (ResourcePackUtils.ResourcePackPanelPadding / 2),
                selectedResourcePacks
        );
        // basically just x with padding lol
        selectedResourcePackPanel.setSlotXBoundsFromLeft((width / 2) + (ResourcePackUtils.PaddingBetweenResourcePackPanels / 2));
    }

    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        selectedResourcePackPanel.handleMouseInput();
        availableResourcePackPanel.handleMouseInput();
    }

    public boolean hasResourcePackEntry(ResourcePackEntry resourcePack)
    {
        return selectedResourcePacks.contains(resourcePack);
    }

    public List<ResourcePackEntry> getListContaining(ResourcePackEntry resourcePack)
    {
        return hasResourcePackEntry(resourcePack) ? selectedResourcePacks : availableResourcePacks;
    }

    public List<ResourcePackEntry> getAvailableResourcePackPanel()
    {
        return availableResourcePacks;
    }

    public List<ResourcePackEntry> getSelectedResourcePackPanel()
    {
        return selectedResourcePacks;
    }

    protected void actionPerformed(GuiButton button) {
        if (!button.enabled)
            return;

        if (button.id == ResourcePackButtonId.Done) {
            if (changed) {
                List<ResourcePackRepository.Entry> packs = Lists.newArrayList();

                for (ResourcePackEntry selectedPack : selectedResourcePacks) {
                    if (selectedPack instanceof ResourcePackEntryFound) {
                        packs.add(((ResourcePackEntryFound) selectedPack).getResourcePack());
                    }
                }

                Collections.reverse(packs);

                mc.getResourcePackRepository().setRepositories(packs);
                mc.gameSettings.resourcePacks.clear();
                mc.gameSettings.incompatibleResourcePacks.clear();

                for (ResourcePackRepository.Entry pack : packs) {
                    mc.gameSettings.resourcePacks.add(pack.getResourcePackName());

                    // compatibility
                    if (pack.func_183027_f() != ResourcePackUtils.compatibilityToInt(PackCompatibility.Compatible)) {
                        mc.gameSettings.incompatibleResourcePacks.add(pack.getResourcePackName());
                    }
                }

                mc.gameSettings.saveOptions();
                mc.refreshResources();
            }

            // return to screen beforehand todo: make sure to make this configurable
            mc.displayGuiScreen(parentScreen);
        }
        else if (button.id == ResourcePackButtonId.OpenFolder)  {
            FileUtils.OpenFile(mc.getResourcePackRepository().getDirResourcepacks(), logger);
        }
        else if (button.id == ResourcePackButtonId.Refresh) {
            initPacks();
            initPanels();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        availableResourcePackPanel.mouseClicked(mouseX, mouseY, mouseButton);
        selectedResourcePackPanel.mouseClicked(mouseX, mouseY, mouseButton);
    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        // draw pano in main menu and normal bg in-game
        drawDefaultBackground();

        // draw both panels
        availableResourcePackPanel.drawScreen(mouseX, mouseY, partialTicks);
        selectedResourcePackPanel.drawScreen(mouseX, mouseY, partialTicks);

        // draw title
        drawCenteredString(fontRendererObj, I18n.format("resourcePack.title"), width / 2, ResourcePackUtils.ResourcePacksScreenTitleTopPadding, ColorUtils.White);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void markChanged()
    {
        changed = true;
    }
}
