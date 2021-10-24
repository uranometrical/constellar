package io.github.steviegt6.constellar.modules.impl;

import io.github.steviegt6.constellar.modules.IModule;
import io.github.steviegt6.constellar.modules.ModuleStatus;
import net.minecraft.util.ResourceLocation;

public class StandardModule implements IModule {
    private final String Key;
    private final String Description;
    private ModuleStatus Status = ModuleStatus.DISABLED;
    private final ResourceLocation Identifier;

    public StandardModule(String key, String description, ResourceLocation identifier) {
        Key = key;
        Description = description;
        Identifier = identifier;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return Identifier;
    }

    @Override
    public String getKey() {
        return Key;
    }

    @Override
    public String getDescription() {
        return Description;
    }

    @Override
    public ModuleStatus getModuleStatus() {
        return Status;
    }

    @Override
    public void setModuleStatus(ModuleStatus status) {
        Status = status;
    }

    @Override
    public boolean isDisabled() {
        // true for disabled and force-disabled
        return Status != ModuleStatus.ENABLED;
    }
}
