package dev.tomat.constellar.content.modules.ui;

import dev.tomat.constellar.content.modules.UiModule;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class KeystrokesModule extends UiModule {
    private final List<Keystroke> keystrokes;

    public KeystrokesModule() {
        super(
                "modules.keystrokes.name",
                "modules.keystrokes.description",
                new ResourceLocation("constellar", "keystrokes"),
                0,0,
                60, 20
        );

        keystrokes = new ArrayList<>();
    }

    @Override
    public ResourceLocation getImageLocation() {
        return new ResourceLocation("constellar", "textures/gui/modules/keystrokes_icon.png");
    }

    @Override
    public void draw(int xPos, int yPos, int width, int height) {
        for (Keystroke keystroke : keystrokes) {
            keystroke.draw();
        }
    }

    public List<Keystroke> getKeystrokes() {
        return keystrokes;
    }

    public void triggerKeypress(Keyboard keypress, boolean pressed) {
        for (Keystroke keystroke : keystrokes) {
            if (keypress == keystroke.key) {
                keystroke.trigger(pressed);
            }
        }
    }

    public void addKeypress(Keyboard key) {
        keystrokes.add(new Keystroke(mc, key, 100, 100));
    }
}
