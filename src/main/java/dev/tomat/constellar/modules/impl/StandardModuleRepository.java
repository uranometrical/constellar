package dev.tomat.constellar.modules.impl;

import dev.tomat.constellar.modules.IModule;
import dev.tomat.constellar.modules.IModuleRepository;
import dev.tomat.constellar.modules.ModuleNotFoundException;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StandardModuleRepository implements IModuleRepository {
    private final List<IModule> Modules = new ArrayList<>();

    @Override
    public void addModule(IModule module) {
        Modules.add(module);
    }

    @Override
    public IModule getModule(ResourceLocation identifier) throws ModuleNotFoundException {
        Optional<IModule> module = Modules.stream().filter(x -> Objects.equals(x.getIdentifier(), identifier)).findFirst();

        if (module.isPresent())
            return module.get();

        throw new ModuleNotFoundException(identifier.toString());
    }

    @Override
    public List<IModule> getModules() {
        return Modules;
    }
}
