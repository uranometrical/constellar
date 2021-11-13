package dev.tomat.constellar.modules.impl;

import dev.tomat.constellar.modules.IModule;
import dev.tomat.constellar.modules.ModuleStatus;
import net.minecraft.util.ResourceLocation;

public abstract class StandardModule implements IModule {
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

    @Override
    public void toggle() {
        if (Status == ModuleStatus.FORCE_DISABLED)
            return;

        if (Status == ModuleStatus.DISABLED)
            Status = ModuleStatus.ENABLED;
        else
            Status = ModuleStatus.DISABLED;
    }
}
