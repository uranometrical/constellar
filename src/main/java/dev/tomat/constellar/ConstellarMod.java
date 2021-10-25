package dev.tomat.constellar;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.Display;
/*
    replace("@MOD_ID@": project.modId,
            "@MOD_NAME@": project.modName,
            "@VERSION@": project.version,
            "@MOD_ACCEPTED@": project.acceptedMinecraftVersions)
 */
@Mod(modid = "@MOD_ID@", name = "@MOD_NAME@", version = "@VERSION@", acceptedMinecraftVersions = "@MOD_ACCEPTED@", useMetadata = true)
public class ConstellarMod {
    public ConstellarMod() {
        // Incredibly arrogant of us, but I don't really care.
        Display.setTitle("@MOD_NAME@" + " v" + "@VERSION@" + " - " + Minecraft.getMinecraft().getVersion());
    }
}
