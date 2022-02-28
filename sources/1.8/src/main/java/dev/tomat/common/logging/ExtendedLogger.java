package dev.tomat.common.logging;

public class ExtendedLogger {
    public IFunctionalLogger logger;

    public ExtendedLogger(IFunctionalLogger logger) {
        this.logger = logger;
    }

    public void debug(String content) {
        logger.debug(content);
    }

    public void debug(String category, String content) {
        logger.debug(toLoggableMessage(category, content));
    }

    public void info(String content) {
        logger.info(content);
    }

    public void info(String category, String content) {
        logger.info(toLoggableMessage(category, content));
    }

    public void warn(String content) {
        logger.warn(content);
    }

    public void warn(String category, String content) {
        logger.warn(toLoggableMessage(category, content));
    }

    public void error(String content) {
        logger.error(content);
    }

    public void error(String category, String content) {
        logger.error(toLoggableMessage(category, content));
    }

    public void fatal(String content) {
        logger.fatal(content);
    }

    public void fatal(String category, String content) {
        logger.fatal(toLoggableMessage(category, content));
    }

    public String toLoggableMessage(String category, String content) {
        return '[' + category + "] " + content;
    }

    public interface IFunctionalLogger {
        void debug(String message);

        void info(String message);

        void warn(String message);

        void error(String message);

        void fatal(String message);
    }
}
