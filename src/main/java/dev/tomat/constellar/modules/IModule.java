package dev.tomat.constellar.modules;

import net.minecraft.util.ResourceLocation;

public interface IModule {
    // Replace with Identifiers in modern versions
    ResourceLocation getIdentifier();

    String getKey();

    String getDescription();

    ModuleStatus getModuleStatus();

    void setModuleStatus(ModuleStatus status);

    boolean isDisabled();

    void toggle();
}
