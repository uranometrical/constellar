package dev.tomat.common;

/**
 * Prevents instantiation of extending classes.
 */
public abstract class Static {
    public Static() throws Exception {
        throw new Exception("Static classes can not be instantiated!");
    }
}
