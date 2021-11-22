package dev.tomat.constellar;

import dev.tomat.common.Static;
import dev.tomat.common.logging.ExtendedLogger;
import dev.tomat.common.reflection.Reflector;
import dev.tomat.constellar.modules.IModuleRepository;
import dev.tomat.constellar.modules.impl.StandardModuleRepository;
import dev.tomat.constellar.modules.impl.ui.KeystrokesModule;

public final class Constellar extends Static {
    public static final Reflector REFLECTOR;
    public static final ExtendedLogger LOGGER = new ExtendedLogger("Constellar");
    public static final String ClientNameReadable = "Constellar";
    public static final String ClientNameTechnical = "constellar";
    public static final String ClientVersion = "0.1.0-alpha";
    public static IModuleRepository Modules = new StandardModuleRepository();

    static {
        REFLECTOR = new Reflector();
        REFLECTOR.registerClassLoader(Constellar.class.getClassLoader());
        Modules.addModule(new KeystrokesModule());
    }

    public Constellar() throws Exception {
    }
}
