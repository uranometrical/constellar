package dev.tomat.constellar.core.modules;

public class ModuleNotFoundException extends Exception {
    public ModuleNotFoundException(String key) {
        super("Module not found: " + key);
    }
}
