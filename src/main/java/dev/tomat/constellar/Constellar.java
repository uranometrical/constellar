package dev.tomat.constellar;

import dev.tomat.common.Static;
import dev.tomat.common.logging.ExtendedLogger;
import dev.tomat.common.reflection.Reflector;
import dev.tomat.constellar.core.keybinds.Keybinds;
import dev.tomat.constellar.core.modules.IModuleRepository;
import dev.tomat.constellar.core.modules.ModuleType;
import dev.tomat.constellar.content.modules.StandardModuleRepository;
import dev.tomat.constellar.content.modules.player.ToggleSprintModule;
import dev.tomat.constellar.content.modules.ui.KeystrokesModule;
import dev.tomat.constellar.content.modules.world.BlockOutlineModule;

public final class Constellar extends Static {
    public static final Reflector REFLECTOR;
    public static final ExtendedLogger LOGGER = new ExtendedLogger("Constellar");

    public static final String ClientNameReadable = "Constellar";
    public static final String ClientNameTechnical = "constellar";
    public static final String ClientVersion = "0.1.0-alpha";

    public static IModuleRepository Modules = new StandardModuleRepository();

    public static Keybinds Keybindings = new Keybinds();

    static {
        REFLECTOR = new Reflector();
        REFLECTOR.registerClassLoader(Constellar.class.getClassLoader());
    }

    public Constellar() throws Exception {
    }

    public static void registerModules() {
        Modules.addModule(ModuleType.Keystrokes, new KeystrokesModule());
        Modules.addModule(ModuleType.BlockOutline, new BlockOutlineModule());
        Modules.addModule(ModuleType.ToggleSprint, new ToggleSprintModule());
    }
}
