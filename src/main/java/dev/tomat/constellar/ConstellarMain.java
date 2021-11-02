package dev.tomat.constellar;

import dev.tomat.constellar.modules.IModuleRepository;
import dev.tomat.constellar.modules.impl.StandardModuleRepository;

public class ConstellarMain {
    public static final String ClientNameReadable = "Constellar";
    public static final String ClientNameTechnical = "constellar";
    public static final String ClientVersion = "0.1.0-alpha";
    public static IModuleRepository Modules = new StandardModuleRepository();

    // public ConstellarMain() {
    //     System.out.println("Instantiated ConstellarMain. Was this a mistake?");
    // }
}
