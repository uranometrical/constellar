package io.github.steviegt6.constellar.modules;

import net.minecraft.util.ResourceLocation;

import java.util.List;

public interface IModuleRepository {
    void AddModule(IModule module);

    // Replace with Identifier in modern verisons.
    IModule getModule(ResourceLocation identifier) throws ModuleNotFoundException;

    List<IModule> getModules();
}
