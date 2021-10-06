package io.github.steviegt6.constellar.modules;

import java.util.List;

public interface IModuleRepository {
    void AddModule(IModule module);

    IModule getModule(String key) throws ModuleNotFoundException;

    List<IModule> getModules();
}
