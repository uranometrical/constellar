package dev.tomat.constellar.modules;

import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public interface IModuleRepository {
    void addModule(ModuleType moduleType, IModule module);

    // Replace with Identifier in modern verisons.
    IModule getModule(ModuleType moduleType) throws ModuleNotFoundException;

    Map<ModuleType, IModule> getModules();
}
