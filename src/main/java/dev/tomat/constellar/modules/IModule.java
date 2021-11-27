package dev.tomat.constellar.modules;

import net.minecraft.util.ResourceLocation;

public interface IModule {
    ResourceLocation getIdentifier();

    ResourceLocation getImageLocation();

    String getKey();

    String getDescription();

    ModuleStatus getModuleStatus();

    void setModuleStatus(ModuleStatus status);

    boolean isDisabled();

    void toggle();
}
