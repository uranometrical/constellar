package io.github.steviegt6.constellar.launch.srg;

public enum MappingType {
    Package("PK"),
    Class("CL"),
    Field("FD"),
    Method("MD");

    public String Qualifier;

    MappingType(String qualifier) {
        Qualifier = qualifier;
    }

    public static MappingType getType(String qualifier) {
        switch (qualifier) {
            case "PK:":
                return Package;

            case "CL:":
                return Class;

            case "FD:":
                return Field;

            case "MD:":
                return Method;
        }

        return null;
    }
}
