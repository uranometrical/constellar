package dev.tomat.constellar.modules;

import java.util.List;

public class ModuleProfile {
    public String Name;
    public String Description;
    public String Author;
    public List<IModule> Modules;
    public String BasePath;

    public ModuleProfile() {
        this("", "", "");
    }

    public ModuleProfile(String name, String description, String author) {
        Name = name;
        Description = description;
        Author = author;
    }
}
