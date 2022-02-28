package dev.tomat.constellar.util;

import dev.tomat.common.logging.ExtendedLogger;
import org.apache.logging.log4j.Logger;

public class Log4jLogger implements ExtendedLogger.IFunctionalLogger {
    private final Logger logger;

    public Log4jLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void fatal(String message) {
        logger.fatal(message);
    }
}
