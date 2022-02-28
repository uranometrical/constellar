package dev.tomat.constellar;

import dev.tomat.common.logging.ExtendedLogger;
import dev.tomat.common.reflection.Reflector;
import dev.tomat.constellar.content.modules.world.FullBrightModule;
import dev.tomat.constellar.core.keybinds.Keybinds;
import dev.tomat.constellar.core.modules.IModuleRepository;
import dev.tomat.constellar.core.modules.ModuleType;
import dev.tomat.constellar.content.modules.StandardModuleRepository;
import dev.tomat.constellar.content.modules.player.ToggleSprintModule;
import dev.tomat.constellar.content.modules.ui.KeystrokesModule;
import dev.tomat.constellar.content.modules.world.BlockOutlineModule;
import dev.tomat.constellar.util.Log4jLogger;
import org.apache.logging.log4j.LogManager;

public final class Constellar {
    public static final String CLIENT_NAME = "Constellar";
    public static final String CLIENT_VERSION = "0.1.0-alpha";
    public static final Reflector REFLECTOR = new Reflector();
    public static final ExtendedLogger LOGGER = new ExtendedLogger(new Log4jLogger(LogManager.getLogger(CLIENT_NAME)));
    public static IModuleRepository MODULES = new StandardModuleRepository();

    public static Keybinds Keybindings = new Keybinds();

    static {
        REFLECTOR.registerClassLoader(Constellar.class.getClassLoader());
    }

    public Constellar() throws Exception {
    }

    public static void registerModules() {
        MODULES.addModule(ModuleType.Keystrokes, new KeystrokesModule());
        MODULES.addModule(ModuleType.BlockOutline, new BlockOutlineModule());
        MODULES.addModule(ModuleType.ToggleSprint, new ToggleSprintModule());
        MODULES.addModule(ModuleType.FullBright, new FullBrightModule());
    }
}
