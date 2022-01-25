package dev.tomat.common.reflection;

import dev.tomat.constellar.Constellar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Useful class for handling excessive reflection.
 */
public class Reflector {
    public List<ClassLoader> ClassLoaders = new ArrayList<>();
    public HashMap<String, Class<?>> Classes = new HashMap<>();
    public HashMap<String, Method> Methods = new HashMap<>();

    public Reflector registerClassLoader(@Nonnull ClassLoader classLoader) {
        ClassLoaders.add(classLoader);

        Constellar.LOGGER.debug("Reflector", "Registered ClassLoader: " + classLoader.getClass().getName());

        return this;
    }

    public Reflector registerClass(@Nonnull Class<?> value) {
        if (Classes.containsKey(value.getName()))
            return this;

        Classes.put(value.getName(), value);

        Constellar.LOGGER.debug("Reflector", "Registered class: " + value.getName());

        return this;
    }

    public Reflector resolveClass(@Nonnull String name) {
        Class<?> clazz = getClass(name);

        if (clazz == null)
            return this;

        return registerClass(clazz);
    }

    public Reflector registerMethod(@Nonnull Method value) {
        String key = value.getClass().getName() + '.' + value.getName();

        if (Methods.containsKey(key))
            return this;

        value.setAccessible(true);
        Methods.put(key, value);

        Constellar.LOGGER.debug("Reflector", "Registered method: " + key);

        return this;
    }

    public Reflector resolveMethod(@Nonnull Class<?> clazz, @Nonnull String name, @Nullable Class<?>... args) {
        Method method = getMethod(clazz, name, args);

        if (method == null)
            return this;

        return registerMethod(method);
    }

    /**
     * Searches all provider ClassLoader instances for the class with the given name.
     * @param name The fully-qualified name of the class.
     * @return The class, if found in any ClassLoaders. Otherwise, null.
     */
    public @Nullable Class<?> getClass(@Nonnull String name) {
        if (Classes.containsKey(name))
            return Classes.get(name);

        Class<?> clazz = null;

        for (ClassLoader loader : ClassLoaders) {
            try {
                clazz = loader.loadClass(name);

                // Won't make it to here if a resolution fails. Safe to not check for null.
                registerClass(clazz);
            } catch (ClassNotFoundException ignored) {
            }
        }

        return clazz;
    }

    /**
     * Returns a method located within the given class instance, in accordance to the specified name and any arguments.
     * @param clazz The class to search for a method in.
     * @param name The name of the method.
     * @param args The arguments, if applicable.
     * @return The resolved method. If no arguments are specified and a method with no arguments does not exist, returns the first resolved method with parameters. If no methods are found, returns null. If no methods with the provided arguments are found, returns null.
     */
    public @Nullable Method getMethod(@Nonnull Class<?> clazz, @Nonnull String name, @Nullable Class<?>... args) {
        if (Methods.containsKey(clazz.getName() + '.' + name))
            return Methods.get(clazz.getName() + '.' + name);

        Method method = null;

        if (args == null || args.length == 0) {
            Optional<Method> streamResult = Arrays.stream(clazz.getMethods()).filter(x -> x.getName().equals(name)).findFirst();
            method = streamResult.orElse(null);
        }
        else {
            try {
                method = clazz.getMethod(name, args);
            } catch (NoSuchMethodException ignore) {
            }
        }

        if (method != null)
            registerMethod(method);

        return method;
    }

    /**
     * Returns a method located within the specified class, in accordance to the specified name and any arguments.
     * @param clazz The name of the class to search for the method in.
     * @param name The name of the method.
     * @param args The arguments, if applicable.
     * @return The resolved method. If the class could not be resolved, returns null. If no arguments are specified and a method with no arguments does not exist, returns the first resolved method with parameters. If no methods are found, returns null. If no methods with the provided arguments are found, returns null.
     */
    public @Nullable Method getMethod(@Nonnull String clazz, @Nonnull String name, @Nullable Class<?>... args) {
        Class<?> realClazz = getClass(clazz);

        if (realClazz == null)
            return null;

        return getMethod(realClazz, name, args);
    }

    public Object invokeMethod(@Nonnull Method method, @Nullable Object instance, @Nullable Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return new FailedInvocationObject(e);
        }
    }
}
