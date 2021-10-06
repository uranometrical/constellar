package io.github.steviegt6.constellar.modules;

public interface IModule {
    String getKey();

    String getDescription();

    ModuleStatus getModuleStatus();

    void setModuleStatus(ModuleStatus status);

    boolean isDisabled();
}
