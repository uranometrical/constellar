package dev.tomat.constellar.content.resources;

import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConstellarPackListEntry extends ResourcePackListEntry {
    private static final Logger logger = LogManager.getLogger();
    private final IResourcePack packInstance = new ConstellarPack();
    private final ResourceLocation resourcePackIcon;

    public ConstellarPackListEntry(GuiScreenResourcePacks uiInstance) {
        super(uiInstance);
        this.resourcePackIcon = new ResourceLocation("constellar", "pack.png");
    }

    protected int func_183019_a() {
        return 1;
    }


    protected String func_148311_a() {
        return EnumChatFormatting.DARK_GRAY + "Default resources used by " + EnumChatFormatting.LIGHT_PURPLE + "Constellar" + EnumChatFormatting.DARK_GRAY + ".";
    }

    protected boolean func_148309_e() {
        return false;
    }

    protected boolean func_148308_f() {
        return false;
    }

    protected boolean func_148314_g() {
        return false;
    }

    protected boolean func_148307_h() {
        return false;
    }

    protected String func_148312_b() {
        return packInstance.getPackName();
    }

    protected void func_148313_c() {
        this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
    }

    protected boolean func_148310_d() {
        return false;
    }
}
