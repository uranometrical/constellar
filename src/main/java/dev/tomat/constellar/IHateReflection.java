package dev.tomat.constellar;

import java.lang.reflect.Field;

public final class IHateReflection {
    public static Field getMappedField(Class<?> clazz, String mappedName, String obfName) {
        for (Field field : clazz.getFields()) {
            if (field.getName().equals(mappedName))
                return field;
            else if (field.getName().equals(obfName))
                return field;
        }

        return null;
    }
}
