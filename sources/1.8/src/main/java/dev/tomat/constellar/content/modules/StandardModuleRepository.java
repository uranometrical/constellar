package dev.tomat.constellar.content.modules;

import dev.tomat.constellar.core.modules.IModule;
import dev.tomat.constellar.core.modules.IModuleRepository;
import dev.tomat.constellar.core.modules.ModuleNotFoundException;
import dev.tomat.constellar.core.modules.ModuleType;

import java.util.*;

public class StandardModuleRepository implements IModuleRepository {
    private final Map<ModuleType, IModule> Modules = new HashMap<>();

    @Override
    public void addModule(ModuleType moduleType, IModule module) {
        Modules.put(moduleType, module);
    }

    @Override
    public IModule getModule(ModuleType moduleType) throws ModuleNotFoundException {
        //Optional<IModule> module = Modules.stream().filter(x -> Objects.equals(x.getIdentifier(), identifier)).findFirst();

        for (Map.Entry<ModuleType, IModule> moduleEntry : Modules.entrySet()) {
            if (moduleEntry.getKey() == moduleType) {
                return moduleEntry.getValue();
            }
        }

        throw new ModuleNotFoundException(moduleType.toString());
    }

    @Override
    public Map<ModuleType, IModule> getModules() {
        return Modules;
    }
}
