package dev.tomat.constellar.core.modules;

import java.util.Map;

public interface IModuleRepository {
    void addModule(ModuleType moduleType, IModule module);

    // Replace with Identifier in modern verisons.
    IModule getModule(ModuleType moduleType) throws ModuleNotFoundException;

    Map<ModuleType, IModule> getModules();
}
