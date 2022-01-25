package dev.tomat.common.reflection;

/**
 * Returned as an instance if a method invocation resulted in failure.
 */
public final class FailedInvocationObject {
    public Exception Exception;

    public FailedInvocationObject(Exception e) {
        Exception = e;
    }
}
