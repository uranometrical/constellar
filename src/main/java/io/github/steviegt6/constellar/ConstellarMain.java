package io.github.steviegt6.constellar;

import io.github.steviegt6.constellar.modules.IModuleRepository;
import io.github.steviegt6.constellar.modules.impl.StandardModuleRepository;

public class ConstellarMain {
    public static final String ClientNameReadable = "Constellar";
    public static final String ClientNameTechnical = "constellar";
    public static final String ClientVersion = "0.1.0-alpha";
    public static IModuleRepository Modules = new StandardModuleRepository();
}
