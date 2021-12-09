/*package dev.tomat.constellar.mixins.gui.resourcepack;

import dev.tomat.common.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ResourcePackListEntry.class)
public abstract class ResourcePackEntryMixin implements GuiListExtended.IGuiListEntry {
    private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
    private static final IChatComponent incompatibleTranslation = new ChatComponentTranslation("resourcePack.incompatible");
    private static final IChatComponent incompatibleOldTranslation = new ChatComponentTranslation("resourcePack.incompatible.old");
    private static final IChatComponent incompatibleNewTranslation = new ChatComponentTranslation("resourcePack.incompatible.new");

    @Shadow @Final protected GuiScreenResourcePacks resourcePacksGUI;
    @Shadow @Final protected Minecraft mc;

    private static final ResourceLocation NEW_RESOURCEPACK_TEXTURE = new ResourceLocation("constellar", "textures/gui/resource_packs.png");
    private static final int ICON_SIZE = 32;

    @Inject(method = "drawEntry", at = @At("HEAD"), cancellable = true)
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, CallbackInfo ci) {
        // texture info for custom sprites
        int folderSize = 32;
        int arrowSize = 16;
        int refreshSize = 8;

        // paddings and margins
        int outerPadding = 2;
        int rightMargin = 9;

        int selectionRectWidth = listWidth + (outerPadding * 2) - rightMargin;
        int selectionRectHeight = slotHeight + (outerPadding * 2);

        // hover overlay
        if (isSelected) {
            int overlayX = x - outerPadding;
            int overlayY = y - outerPadding;
            Gui.drawRect(
                    overlayX,
                    overlayY,
                    overlayX + selectionRectWidth,
                    overlayY + selectionRectHeight,
                    ColorUtils.colorToInt(40, 40, 40, 100)
            );
        }

        // render red background if incompatible
        if (incompatiblePack() != 1)
        {
            // reset color
            GlStateManager.color(1f, 1f, 1f, 1f);
            // draw red rectangle around pack
            Gui.drawRect(x - outerPadding, y - outerPadding, x + listWidth - rightMargin + outerPadding, y + slotHeight + outerPadding, ColorUtils.colorToInt(120, 0, 0, 100));
        }

        // load the texture pack icon ready to draw
        loadPackIcon();

        // reset color
        GlStateManager.color(1f, 1f, 1f, 1f);

        // texture pack icon texture
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);

        String packTitle = getPackTitle();
        String packDescription = getPackDescription();

        // if hovering over the resource pack
        // todo: test if anything breaks with touchscreen (I, Metacinnabar, can do this)
        if (mc.gameSettings.touchscreen || isSelected)
        {
            // bind the new resource pack textures
            mc.getTextureManager().bindTexture(NEW_RESOURCEPACK_TEXTURE);

            // reset color
            GlStateManager.color(1f, 1f, 1f, 1f);

            int entryX = mouseX - x;

            if (incompatiblePack() < 1)
            {
                packTitle = incompatibleTranslation.getFormattedText();
                packDescription = incompatibleOldTranslation.getFormattedText();
            }
            else if (incompatiblePack() > 1)
            {
                packTitle = incompatibleTranslation.getFormattedText();
                packDescription = incompatibleNewTranslation.getFormattedText();
            }

            // if on selected pack side
            if (isSelected())
            {
                if (canMovePackUp())
                {
                    // move pack up button hover
                    // check if mouse is on the left side of the icon
                    if (entryX <= arrowSize)
                    {
                        Gui.drawModalRectWithCustomSizedTexture(
                                x, y + (arrowSize / 2),
                                arrowSize * 2, 0,
                                arrowSize, arrowSize,
                                256, 256 // todo: const
                        );
                    }
                    // move pack up button
                    // if hovering over any part of the entry, show the non-hovering version of the icon
                    else
                    {
                        Gui.drawModalRectWithCustomSizedTexture(
                                x, y + (arrowSize / 2),
                                arrowSize * 3, 0,
                                arrowSize, arrowSize,
                                256, 256 // todo: const
                        );
                    }
                }
                if (canMovePackDown())
                {
                    // move pack down button hover
                    // check if mouse is hovering over the icon, then check if mouse is on the right side of the icon
                    if (entryX < slotHeight && entryX > arrowSize)
                    {
                        Gui.drawModalRectWithCustomSizedTexture(
                                x + arrowSize, y + (arrowSize / 2),
                                arrowSize * 2, arrowSize,
                                arrowSize, arrowSize,
                                256, 256 // todo: const
                        );
                    }
                    // move pack down button
                    // if hovering over any part of the entry, show the non-hovering version of the icon
                    else
                    {
                        Gui.drawModalRectWithCustomSizedTexture(
                                x + arrowSize, y + (arrowSize / 2),
                                arrowSize * 3, arrowSize,
                                arrowSize, arrowSize,
                                256, 256 // todo: const
                        );
                    }
                }
            }
        }

        int maxTextWidth = 157;

        int incompatibleTextWidth = this.mc.fontRendererObj.getStringWidth(packTitle);

        if (incompatibleTextWidth > maxTextWidth)
        {
            packTitle = this.mc.fontRendererObj.trimStringToWidth(packTitle, maxTextWidth - this.mc.fontRendererObj.getStringWidth("...")) + "...";
        }

        int padding = 2;

        // draw pack name
        this.mc.fontRendererObj.drawStringWithShadow(packTitle, (float)(x + slotHeight + padding), (float)(y + 1), 16777215);

        // draw both info lines
        List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(packDescription, maxTextWidth);
        for (int l = 0; l < 2 && l < list.size(); ++l)
        {
            this.mc.fontRendererObj.drawStringWithShadow(list.get(l), (float)(x + slotHeight + padding), (float)(y + 12 + 10 * l), 8421504);
        }

        // return but ci
        ci.cancel();
    }

    @Override
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int x, int y)
    {
        // todo: const all paddings and shit
        // todo: unobf all vars
        int outerPadding = 2;
        int rightMargin = 9;

        // todo: const
        int entryWidth = 210;
        int selectionRectWidth = entryWidth + (outerPadding * 2) ;

        if (x <= selectionRectWidth)
        {
            ResourcePackListEntry entry = (ResourcePackListEntry) (Object) ResourcePackEntryMixin.this;

            // selecting packs
            if (!isSelected())
            {
                resourcePacksGUI.markChanged();

                if (incompatiblePack() != 1)
                {
                    String incompatibleTitle = I18n.format("resourcePack.incompatible.confirm.title");
                    String incompatibleConfirm = I18n.format("resourcePack.incompatible.confirm." + (incompatiblePack() > 1 ? "new" : "old"));

                    mc.displayGuiScreen(new GuiYesNo((result, id) -> {
                        List<ResourcePackListEntry> availablePackList = resourcePacksGUI.getListContaining(entry);
                        mc.displayGuiScreen(resourcePacksGUI);

                        if (result)
                        {
                            // remove the pack from available packs
                            availablePackList.remove(entry);
                            // add the pack to the top
                            resourcePacksGUI.getSelectedResourcePacks().add(0, entry);
                        }
                    }, incompatibleTitle, incompatibleConfirm, 0));
                }
                else
                {
                    // remove the pack from available packs
                    resourcePacksGUI.getListContaining(entry).remove(entry);
                    // add the pack to the top
                    resourcePacksGUI.getSelectedResourcePacks().add(0, entry);
                }

                return true;
            }

            // unselecting packs
            if (x > ICON_SIZE && isSelected()) {
                // unselect the pack
                resourcePacksGUI.getListContaining(entry).remove(entry);
                // add the pack to the available packs list
                resourcePacksGUI.getAvailableResourcePacks().add(0, entry);
                // reload
                resourcePacksGUI.markChanged();
                return true;
            }

            // check if the mouse was clicked in the icon rectangle
            if (x <= ICON_SIZE && y <= ICON_SIZE) {
                List<ResourcePackListEntry> selectedPacksList = resourcePacksGUI.getListContaining(entry);

                // move pack up
                if (x < (ICON_SIZE / 2) && canMovePackUp())
                {
                    // get the current index of the pack that wants to move up
                    int currentPackIndex = selectedPacksList.indexOf(entry);
                    // remove it from the list
                    selectedPacksList.remove(entry);
                    // add it back, at the index it was before, minus one to move upwards
                    selectedPacksList.add(currentPackIndex - 1, entry);
                    // reload
                    resourcePacksGUI.markChanged();
                    return true;
                }

                // move pack down
                if (x > (ICON_SIZE / 2) && canMovePackDown()) {
                    // get the current index of the pack that wants to move down
                    int currentPackIndex = selectedPacksList.indexOf(entry);
                    // remove it from the list
                    selectedPacksList.remove(entry);
                    // add it back, at the index it was before, plus one to move downwards
                    selectedPacksList.add(currentPackIndex + 1, entry);
                    // reload
                    resourcePacksGUI.markChanged();
                    return true;
                }
            }
        }

        return false;
    }

    @Shadow protected abstract int func_183019_a();

    public int incompatiblePack() {
        // todo: enum or const
        // 1 = compatible
        // < 1 = incompatible because old
        // > 1 = incompatible because new
        return func_183019_a();
    }

    @Shadow protected abstract String func_148311_a();

    public String getPackDescription() {
        return func_148311_a();
    }

    @Shadow protected abstract String func_148312_b();

    public String getPackTitle() {
        return func_148312_b();
    }

    @Shadow protected abstract void func_148313_c();

    public void loadPackIcon() {
        func_148313_c();
    }

    @Shadow protected abstract boolean func_148308_f();

    public boolean isSelected() {
        // return this.resourcePacksGUI.hasResourcePackEntry(this);
        // return this.selectedResourcePacks.contains(p_146961_1_);
        return func_148308_f();
    }

    @Shadow protected abstract boolean func_148307_h();

    public boolean canMovePackDown() {
        return func_148307_h();
    }

    @Shadow protected abstract boolean func_148314_g();

    public boolean canMovePackUp() {
        return func_148314_g();
    }
}*/
