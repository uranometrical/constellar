package dev.tomat.constellar.modules;

import net.minecraft.util.ResourceLocation;

import java.util.List;

public interface IModuleRepository {
    void addModule(IModule module);

    // Replace with Identifier in modern verisons.
    IModule getModule(ResourceLocation identifier) throws ModuleNotFoundException;

    List<IModule> getModules();
}
