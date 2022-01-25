package dev.tomat.common.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendedLogger {
    public Logger LoggerInstance;

    public ExtendedLogger(String name) {
        LoggerInstance = LogManager.getLogger(name);
    }

    public void debug(String content) {
        LoggerInstance.debug(content);
    }

    public void debug(String category, String content) {
        LoggerInstance.debug(toLoggableMessage(category, content));
    }

    public void info(String content) {
        LoggerInstance.info(content);
    }

    public void info(String category, String content) {
        LoggerInstance.info(toLoggableMessage(category, content));
    }

    public void warn(String content) {
        LoggerInstance.warn(content);
    }

    public void warn(String category, String content) {
        LoggerInstance.warn(toLoggableMessage(category, content));
    }

    public void error(String content) {
        LoggerInstance.error(content);
    }

    public void error(String category, String content) {
        LoggerInstance.error(toLoggableMessage(category, content));
    }

    public void fatal(String content) {
        LoggerInstance.fatal(content);
    }

    public void fatal(String category, String content) {
        LoggerInstance.fatal(toLoggableMessage(category, content));
    }

    public String toLoggableMessage(String category, String content) {
        return '[' + category + "] " + content;
    }
}
