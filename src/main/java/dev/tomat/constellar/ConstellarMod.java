package dev.tomat.constellar;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.Display;

@Mod(
        modid = ConstellarConstants.ID,
        name = ConstellarConstants.NAME,
        version = ConstellarConstants.VERSION,
        acceptedMinecraftVersions = "[" + ConstellarConstants.MCVERSION + "]",
        useMetadata = true
)
public class ConstellarMod {
    public ConstellarMod() {
        // Incredibly arrogant of us, but I don't really care.
        Display.setTitle(ConstellarConstants.NAME + " v" + ConstellarConstants.VERSION + " - " + Minecraft.getMinecraft().getVersion());
    }
}
