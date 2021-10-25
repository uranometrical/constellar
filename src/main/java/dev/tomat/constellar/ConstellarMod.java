package dev.tomat.constellar;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.Display;

@Mod(name = ConstellarConstants.NAME, modid = ConstellarConstants.ID, version = ConstellarConstants.Version)
public class ConstellarMod {
    public ConstellarMod() {
        // Incredibly arrogant of us, but I don't really care.
        Display.setTitle(ConstellarConstants.NAME + " v" + ConstellarConstants.Version + " - " + Minecraft.getMinecraft().getVersion());
    }
}
