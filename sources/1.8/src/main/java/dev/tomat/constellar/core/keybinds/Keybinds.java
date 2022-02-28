package dev.tomat.constellar.core.keybinds;

import com.google.common.collect.Lists;
import dev.tomat.constellar.Constellar;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Keybinds {
    public List<KeyBinding> keybinds;

    public Keybinds() {
        keybinds = Lists.newArrayList();
        keybinds.add(new KeyBinding("key.modulesettings", Keyboard.KEY_RSHIFT, Constellar.CLIENT_NAME));
    }

    public void addKeybind(KeyBinding keybind) {
        keybinds.add(keybind);
    }
}
