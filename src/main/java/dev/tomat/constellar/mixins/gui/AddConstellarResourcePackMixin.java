package dev.tomat.constellar.mixins.gui;

import com.google.common.collect.Lists;
import dev.tomat.constellar.resources.ConstellarPackListEntry;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(GuiScreenResourcePacks.class)
public class AddConstellarResourcePackMixin extends GuiScreen {
    @Shadow
    private GuiResourcePackAvailable availableResourcePacksList;

    @Shadow
    private List<ResourcePackListEntry> availableResourcePacks;

    @Shadow
    private GuiResourcePackSelected selectedResourcePacksList;

    @Shadow
    private List<ResourcePackListEntry> selectedResourcePacks;

    @Shadow
    private boolean changed;

    @Inject(method = "initGui", at = @At("HEAD"), cancellable = true)
    public void initGui(CallbackInfo ci) {
        buttonList.add(new GuiOptionButton(2, width / 2 - 154, height - 48, I18n.format("resourcePack.openFolder")));
        buttonList.add(new GuiOptionButton(1, width / 2 + 4, height - 48, I18n.format("gui.done")));
        if (!changed) {
            availableResourcePacks = Lists.newArrayList();
            selectedResourcePacks = Lists.newArrayList();
            ResourcePackRepository lvt_1_1_ = mc.getResourcePackRepository();
            lvt_1_1_.updateRepositoryEntriesAll();
            List<ResourcePackRepository.Entry> lvt_2_1_ = Lists.newArrayList(lvt_1_1_.getRepositoryEntriesAll());
            lvt_2_1_.removeAll(lvt_1_1_.getRepositoryEntries());
            Iterator<ResourcePackRepository.Entry> lvt_3_2_ = lvt_2_1_.iterator();

            ResourcePackRepository.Entry lvt_4_2_;
            while (lvt_3_2_.hasNext()) {
                lvt_4_2_ = lvt_3_2_.next();
                availableResourcePacks.add(new ResourcePackListEntryFound((GuiScreenResourcePacks) (Object) this, lvt_4_2_));
            }

            lvt_3_2_ = Lists.reverse(lvt_1_1_.getRepositoryEntries()).iterator();

            while (lvt_3_2_.hasNext()) {
                lvt_4_2_ = lvt_3_2_.next();
                selectedResourcePacks.add(new ResourcePackListEntryFound((GuiScreenResourcePacks) (Object) this, lvt_4_2_));
            }

            selectedResourcePacks.add(new ConstellarPackListEntry((GuiScreenResourcePacks) (Object) this));
            selectedResourcePacks.add(new ResourcePackListEntryDefault((GuiScreenResourcePacks) (Object) this));
        }

        availableResourcePacksList = new GuiResourcePackAvailable(mc, 200, height, availableResourcePacks);
        availableResourcePacksList.setSlotXBoundsFromLeft(width / 2 - 4 - 200);
        availableResourcePacksList.registerScrollButtons(7, 8);
        selectedResourcePacksList = new GuiResourcePackSelected(mc, 200, height, selectedResourcePacks);
        selectedResourcePacksList.setSlotXBoundsFromLeft(width / 2 + 4);
        selectedResourcePacksList.registerScrollButtons(7, 8);

        ci.cancel();
    }
}
