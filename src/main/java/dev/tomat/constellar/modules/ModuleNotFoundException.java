package dev.tomat.constellar.modules;

public class ModuleNotFoundException extends Exception {
    public ModuleNotFoundException(String key) {
        super("Module not found: " + key);
    }
}
